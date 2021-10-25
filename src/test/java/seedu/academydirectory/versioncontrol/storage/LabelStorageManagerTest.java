package seedu.academydirectory.versioncontrol.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.versioncontrol.objects.Commit;
import seedu.academydirectory.versioncontrol.objects.Label;

public class LabelStorageManagerTest {
    private static final Path TESTING_DIR = Paths.get("src", "test", "data", "VersionControlTest", "ParserTest");

    @Test
    public void parse_fileExist_success() {
        TreeStorageManager treeStorageManager = new TreeStorageManager(TESTING_DIR);
        CommitStorageManager commitStorageManager = new CommitStorageManager(TESTING_DIR, treeStorageManager);
        LabelStorageManager labelStorageManager = new LabelStorageManager(TESTING_DIR, commitStorageManager);

        String filename = "HeadParserTest_NoRef";
        Path filepath = TESTING_DIR.resolve(Paths.get(filename));
        Path finalFilepath = filepath;
        assertTrue(() -> finalFilepath.toFile().exists());

        Label label = labelStorageManager.read(filename);
        assertEquals(Commit.NULL, label.getCommitSupplier().get());

        filename = "HeadParserTest_WithRef";
        filepath = TESTING_DIR.resolve(Paths.get(filename));
        Path finalFilepath1 = filepath;
        assertTrue(() -> finalFilepath1.toFile().exists());

        label = labelStorageManager.read(filename);
        assertEquals("CommitParserTest", label.getCommitSupplier().get().getHash());
    }

    @Test
    public void parse_fileAbsent_nullParse() {
        TreeStorageManager treeStorageManager = new TreeStorageManager(TESTING_DIR);
        CommitStorageManager commitStorageManager = new CommitStorageManager(TESTING_DIR, treeStorageManager);
        LabelStorageManager labelStorageManager = new LabelStorageManager(TESTING_DIR, commitStorageManager);

        String filename = "NOSUCHFILE";
        Path filepath = Paths.get("src", "test", "data", "VersionControlTest", "ParserTest", filename);
        assertFalse(() -> filepath.toFile().exists());
        assertEquals(Label.NULL, labelStorageManager.read(filename));
    }

    @Test
    public void parse_fileCorrupt_nullParse() {
        TreeStorageManager treeStorageManager = new TreeStorageManager(TESTING_DIR);
        CommitStorageManager commitStorageManager = new CommitStorageManager(TESTING_DIR, treeStorageManager);
        LabelStorageManager labelStorageManager = new LabelStorageManager(TESTING_DIR, commitStorageManager);

        String filename = "CommitParserTest";
        Path filepath = Paths.get("src", "test", "data", "VersionControlTest", "ParserTest", filename);
        assertTrue(() -> filepath.toFile().exists());
        assertEquals(Label.NULL, labelStorageManager.read(filename));
    }
}
