package seedu.academydirectory.storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import seedu.academydirectory.versioncontrol.objects.Commit;
import seedu.academydirectory.versioncontrol.objects.Label;
import seedu.academydirectory.versioncontrol.objects.Tree;
import seedu.academydirectory.versioncontrol.storage.CommitStorageManager;
import seedu.academydirectory.versioncontrol.storage.LabelStorageManager;
import seedu.academydirectory.versioncontrol.storage.TreeStorageManager;
import seedu.academydirectory.versioncontrol.utils.HashGenerator;
import seedu.academydirectory.versioncontrol.utils.HashMethod;

public class VersionControlReader {
    private final TreeStorageManager treeStorageManager;
    private final LabelStorageManager labelStorageManager;
    private final CommitStorageManager commitStorageManager;
    private final Path vcPath;
    private final HashGenerator hashGenerator;

    public VersionControlReader(HashMethod hashMethod, Path vcPath) {
        this.hashGenerator = new HashGenerator(hashMethod);
        this.vcPath = vcPath;
        this.treeStorageManager = new TreeStorageManager(vcPath);
        this.commitStorageManager = new CommitStorageManager(vcPath, this.treeStorageManager);
        this.labelStorageManager = new LabelStorageManager(vcPath, this.commitStorageManager);
    }

    /**
     * Constructs a new Commit object to be saved to disk.
     * @param message Commit message for a human-readable format of the commit
     * @param treeSupplier Supplier to the tree object that this Commit points to
     * @param parentCommitSupplier Supplier to the parent Commit that this Commit points to
     * @return a Commit object which represents the change. Returns a null commit if unable to write
     */
    public Commit createNewCommit(String message, Supplier<Tree> treeSupplier, Supplier<Commit> parentCommitSupplier) {
        requireNonNull(message);
        requireNonNull(treeSupplier);
        requireNonNull(parentCommitSupplier);

        String commitFileName = "temp";
        String author = System.getProperty("user.name");
        Date date = new Date();
        Commit temp = new Commit(commitFileName, author, date, message, parentCommitSupplier, treeSupplier);
        Path commitPath = vcPath.resolve(Paths.get(commitFileName));

        try {
            // Write a temporary commit to disk
            commitStorageManager.write(commitFileName, temp);

            // Delete temporarily created Commit
            String commitHash = hashGenerator.generateHashFromFile(commitPath);
            boolean deletedSuccessfully = commitPath.toFile().delete();

            // Return final commit
            return new Commit(commitHash, author, date, message, parentCommitSupplier, treeSupplier);
        } catch (IOException e) {
            return Commit.NULL;
        }
    }

    /**
     * Constructs a Label object to be saved to disk.
     * @param name Label name
     * @param commit Commit to be labelled
     * @return a Label object which represents a label of the given commit. Returns a null label if unable to write
     */
    public Label createNewLabel(String name, Commit commit) {
        requireNonNull(name);
        requireNonNull(commit);

        String labelFileName = "temp";
        Label temp = new Label(labelFileName, name, () -> commit);

        Path labelPath = this.vcPath.resolve(Path.of(labelFileName));
        try {
            labelStorageManager.write(labelFileName, temp);

            String labelHash = hashGenerator.generateHashFromFile(labelPath);
            boolean deletedSuccessfully = labelPath.toFile().delete();

            return new Label(labelHash, name, () -> commit);
        } catch (IOException e) {
            return Label.NULL;
        }
    }

    /**
     * Constructs a Tree object to be saved to disk
     * @param blobPaths Path to blobs to be duplicated
     * @return a Tree object which represents mapping between actual filename and version-controlled filename
     */
    public Tree createNewTree(List<Path> blobPaths) {
        // Make a blob snapshot
        ArrayList<Path> blobTargetPaths = new ArrayList<>();
        for (Path blobPath: blobPaths) {
            String blobHash;
            try {
                blobHash = hashGenerator.generateHashFromFile(blobPath);
            } catch (IOException e) {
                return Tree.NULL;
            }
            Path blobTargetPath = this.vcPath.resolve(Path.of(blobHash));

            try {
                Files.copy(blobPath, blobTargetPath, REPLACE_EXISTING);
            } catch (IOException e) {
                return Tree.NULL;
            }
            blobTargetPaths.add(blobTargetPath);
        }

        // Write a temporary tree to disk
        String treeFileName = "temp";
        Tree temp = new Tree(treeFileName,
                blobPaths.stream().map(String::valueOf).collect(Collectors.toList()),
                blobTargetPaths.stream().map(String::valueOf).collect(Collectors.toList()));
        Path treePath = this.vcPath.resolve(Path.of(treeFileName));
        try {
            treeStorageManager.write(treeFileName, temp);

            // Delete temporarily created Tree
            String treeHash = hashGenerator.generateHashFromFile(treePath);
            boolean deletedSuccessfully = treePath.toFile().delete();

            // Return final tree
            return new Tree(treeHash,
                    blobPaths.stream().map(String::valueOf).collect(Collectors.toList()),
                    blobTargetPaths.stream().map(String::valueOf).collect(Collectors.toList()));
        } catch (IOException e) {
            return Tree.NULL;
        }
    }

    public Tree createNewTree(Path path) {
        return createNewTree(List.of(path));
    }

    /**
     * Constructs a commit based on hash. Will find the commit object with the same hash in disk
     * @param hash Hash of the commit saved in disk
     * @return Commit object of the given hash
     */
    public Commit fetchCommitByHash(String hash) {
        // Allow for 5 digit hash to be used
        File f = vcPath.toFile();
        String finalHash = hash.trim();
        File[] matchingFiles = requireNonNull(f.listFiles((x, name) -> name.startsWith(finalHash)));
        if (matchingFiles.length == 0) {
            return Commit.NULL;
        }

        // Pick first match
        Path filePath = matchingFiles[0].toPath();
        return commitStorageManager.read(String.valueOf(filePath.getFileName()));
    }

    /**
     * Find a label on disk based on name.
     * @param labelName Hash of the commit saved in disk
     * @return Label object of the given hash
     */
    public Label fetchLabelByName(String labelName) {
        File f = new File(String.valueOf(vcPath));
        File[] matchingFiles = requireNonNull(f.listFiles((x, name) -> name.startsWith(labelName)));
        if (matchingFiles.length == 0) {
            return Label.NULL;
        }

        // Pick first match
        Path labelPath = matchingFiles[0].toPath();
        return labelStorageManager.read(String.valueOf(labelPath.getFileName()));
    }

    /**
     * Constructs a tree based on hash. Will find the tree object with the same hash in disk
     * @param hash Hash of the tree saved in disk
     * @return Commit object of the given hash
     * @throws IOException if no tree of the same hash can be found in disk
     */
    public Tree fetchTreeByHash(String hash) {
        // Allow for 5 digit hash to be used
        File f = new File(String.valueOf(vcPath));
        File[] matchingFiles = requireNonNull(f.listFiles((x, name) -> name.startsWith(hash)));
        if (matchingFiles.length == 0) {
            return Tree.NULL;
        }

        // Pick first match
        Path filePath = matchingFiles[0].toPath();
        return treeStorageManager.read(String.valueOf(filePath.getFileName()));
    }
}

