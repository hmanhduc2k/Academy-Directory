package seedu.academydirectory.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academydirectory.testutil.TypicalCommits.COMMIT1;
import static seedu.academydirectory.testutil.TypicalTrees.TREE1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.versioncontrol.objects.Commit;
import seedu.academydirectory.versioncontrol.objects.Label;
import seedu.academydirectory.versioncontrol.objects.Tree;
import seedu.academydirectory.versioncontrol.utils.HashGenerator;
import seedu.academydirectory.versioncontrol.utils.HashMethod;

public class VersionControlReaderTest {
    private static final Path DATA_DIR = Paths.get("src", "test",
            "data", "VersionControlTest");
    private static final Path BLOB_DIR = DATA_DIR.resolve(Paths.get("BlobStorage"));
    private static final Path TESTING_DIR = Paths.get("src", "test", "temp");
    private static final HashMethod hashMethod = HashMethod.SHA1;
    private static final HashGenerator hashGenerator = new HashGenerator(hashMethod);

    @Test
    public void createNewCommit() {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());

        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, TESTING_DIR);

        // Null Tree and Null Commit
        String message = "Initial Commit";
        Supplier<Tree> nullTreeSupplier = () -> Tree.NULL;
        Supplier<Commit> nullCommitSupplier = () -> Commit.NULL;
        Commit currentCommit = assertDoesNotThrow(() -> versionControlReader.createNewCommit(message,
                nullTreeSupplier,
                nullCommitSupplier));

        assertEquals(System.getProperty("user.name"), currentCommit.getAuthor());
        assertEquals(currentCommit.getDate(), currentCommit.getDate());
        assertEquals(message, currentCommit.getMessage());
        assertEquals(nullCommitSupplier, currentCommit.getParentSupplier());
        assertEquals(nullTreeSupplier, currentCommit.getTreeSupplier());

        // Non-Null Tree and Non-Null Commit
        Supplier<Tree> treeSupplier = () -> new Tree("Test", "TEst", "TEST");
        Supplier<Commit> commitSupplier = () -> COMMIT1;
        currentCommit = assertDoesNotThrow(() -> versionControlReader.createNewCommit(message,
                treeSupplier,
                commitSupplier));

        assertEquals(System.getProperty("user.name"), currentCommit.getAuthor());
        assertEquals(currentCommit.getDate(), currentCommit.getDate());
        assertEquals(message, currentCommit.getMessage());
        assertEquals(commitSupplier, currentCommit.getParentSupplier());
        assertEquals(treeSupplier, currentCommit.getTreeSupplier());

        assertTrue(TESTING_DIR.toFile().delete());
    }

    @Test
    public void fetchCommitByHash() throws IOException {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());

        Path dataPath = DATA_DIR.resolve(Paths.get("CommitControllerTest"));
        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, dataPath);

        // Exact Hash used -> Commit fetched successfully
        String commitHash = "1d83638a25901e76c8e3882afca2347f8352cd06";
        Path filepath = dataPath.resolve(Paths.get(commitHash));
        assertTrue(filepath.toFile().exists()); // Check if file exists first

        Commit actualCommit = versionControlReader.fetchCommitByHash(commitHash);
        assertEquals(COMMIT1, actualCommit);

        // fiveCharHash used -> Commit fetched successfully
        commitHash = commitHash.substring(0, 5);
        actualCommit = versionControlReader.fetchCommitByHash(commitHash);
        assertEquals(COMMIT1, actualCommit);

        // given hash not present in disk -> Commit.NULL returned
        commitHash = "Testing123";
        actualCommit = versionControlReader.fetchCommitByHash(commitHash);
        assertEquals(Commit.NULL, actualCommit);

        // given corrupt commit file -> Commit.NULL returned
        commitHash = "corrupted";
        actualCommit = versionControlReader.fetchCommitByHash(commitHash);
        assertEquals(Commit.NULL, actualCommit);

        assertTrue(TESTING_DIR.toFile().delete());
    }

    @Test
    public void createNewTree_blobsPresent_correctlyDuplicated() throws IOException {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());

        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, TESTING_DIR);

        // Blobs present -> Correctly Duplicated
        String blobName = "HEAD";
        Path blobPath = BLOB_DIR.resolve(Paths.get(blobName));
        assertTrue(blobPath.toFile().exists()); // Check if blob exists first

        Tree actualTree = versionControlReader.createNewTree(blobPath);
        HashMap<String, String> hashMap = actualTree.getHashMap();
        String[] keySet = hashMap.keySet().toArray(new String[0]);
        for (String vcName : keySet) {
            String realName = hashMap.get(vcName);

            Path actualPath = Paths.get(realName);
            assertTrue(actualPath.toFile().exists());

            Path vcPath = Paths.get(vcName);
            assertTrue(vcPath.toFile().exists());

            String actualHash = assertDoesNotThrow(() ->
                    hashGenerator.generateHashFromFile(actualPath));
            String vcHash = assertDoesNotThrow(() ->
                    hashGenerator.generateHashFromFile(actualPath));
            assertEquals(actualHash, vcHash);
        }

        Files.walk(TESTING_DIR).sorted(Comparator.reverseOrder()).map(Path::toFile)
                .forEach(File::delete);

        assertFalse(Files.exists(TESTING_DIR), "Directory still exists");
    }

    @Test
    public void createNewTree_blobsAbsent_nullTree() throws IOException {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());

        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, TESTING_DIR);

        String blobName = "TAIL";
        Path blobPath = BLOB_DIR.resolve(Paths.get(blobName));
        assertFalse(blobPath.toFile().exists()); // Check if blob does not exist

        Tree actualTree = versionControlReader.createNewTree(blobPath);
        assertEquals(Tree.NULL, actualTree);

        Files.walk(TESTING_DIR).sorted(Comparator.reverseOrder()).map(Path::toFile)
                .forEach(File::delete);

        assertFalse(Files.exists(TESTING_DIR), "Directory still exists");
    }

    @Test
    public void fetchTreeByHash() throws IOException {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());

        Path dataPath = DATA_DIR.resolve(Paths.get("TreeControllerTest"));
        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, dataPath);

        // Exact Hash used -> Commit fetched successfully
        String treeHash = "9d34f3e9ada5ae7cc5c063b905a5d7893f792497";
        Path filepath = dataPath.resolve(Paths.get(treeHash));
        assertTrue(filepath.toFile().exists()); // Check if file exists first

        String hash = hashGenerator.generateHashFromFile(filepath);
        System.out.println(hash);

        Tree actualTree = versionControlReader.fetchTreeByHash(treeHash);
        assertEquals(TREE1.getHash(), actualTree.getHash());

        // fiveCharHash used -> Commit fetched successfully
        treeHash = treeHash.substring(0, 5);
        actualTree = versionControlReader.fetchTreeByHash(treeHash);
        assertEquals(TREE1.getHash(), actualTree.getHash());

        // given hash not present in disk -> Commit.NULL returned
        treeHash = "Testing123";
        actualTree = versionControlReader.fetchTreeByHash(treeHash);
        assertEquals(Tree.NULL, actualTree);

        // given corrupt commit file -> Commit.NULL returned
        treeHash = "corrupted";
        actualTree = versionControlReader.fetchTreeByHash(treeHash);
        assertEquals(Tree.NULL, actualTree);

        assertTrue(TESTING_DIR.toFile().delete());
    }

    @Test
    public void createLabel() {
        if (TESTING_DIR.toFile().exists()) {
            assertTrue(TESTING_DIR.toFile().delete());
        }
        assertTrue(TESTING_DIR.toFile().mkdir());
        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, TESTING_DIR);

        // null passed in -> exception thrown
        assertThrows(NullPointerException.class, () -> versionControlReader.createNewLabel(null, null));
        assertThrows(NullPointerException.class, () -> versionControlReader.createNewLabel("NULL", null));

        // Commit1 passed in -> Commit1 labelled
        Label labelledCommit = versionControlReader.createNewLabel("temp", COMMIT1);
        assertEquals(labelledCommit.getCommitSupplier().get(), COMMIT1);

        // Commit.NULL passed in -> Commit.NULL labelled
        Label labelledNullCommit = versionControlReader.createNewLabel("temp", Commit.NULL);
        assertEquals(labelledNullCommit.getCommitSupplier().get(), Commit.NULL);

        assertTrue(TESTING_DIR.toFile().delete());
    }

    @Test
    public void fetchLabelByName() {
        Path dataPath = DATA_DIR.resolve(Paths.get("LabelControllerTest"));
        VersionControlReader versionControlReader = new VersionControlReader(hashMethod, dataPath);

        String labelName = "HEAD";
        Path filepath = dataPath.resolve(Paths.get(labelName));
        assertTrue(filepath.toFile().exists()); // Check if file exists first

        // Correct Name given -> Label fetched successfully
        Label actualLabel = versionControlReader.fetchLabelByName(labelName);
        assertEquals(COMMIT1, actualLabel.getCommitSupplier().get());

        // given name not present in disk -> Label.NULL returned
        actualLabel = versionControlReader.fetchLabelByName("MISSING");
        assertEquals(Label.NULL, actualLabel);
    }
}
