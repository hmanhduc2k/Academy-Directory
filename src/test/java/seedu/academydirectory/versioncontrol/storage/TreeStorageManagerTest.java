package seedu.academydirectory.versioncontrol.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academydirectory.testutil.TypicalTrees.TREE1;
import static seedu.academydirectory.testutil.TypicalTrees.TREE2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.versioncontrol.objects.Tree;

public class TreeStorageManagerTest {
    private static final Path DATA_DIR = Paths.get("src", "test",
            "data", "VersionControlTest", "ParserTest");

    @Test
    public void read() {
        TreeStorageManager treeStorageManager = new TreeStorageManager(DATA_DIR);

        String filename = "TreeParserTest";
        Path filepath = DATA_DIR.resolve(Paths.get(filename));
        assertTrue(() -> filepath.toFile().exists());

        Tree actual = assertDoesNotThrow(() -> treeStorageManager.read(filename));
        assertEquals(filename, actual.getHash());
        String key = actual.getHashMap().keySet().toArray(String[]::new)[0];
        assertEquals("922d4ff703f9b003da1962b1a2228371718e504a", key);
        assertEquals("academydirectory.json", actual.getHashMap().get(key));
    }

    @Test
    public void getWriteableFormat() {
        TreeStorageManager treeStorageManager = new TreeStorageManager(DATA_DIR);

        // Null tree
        assertEquals(List.of("null null"), treeStorageManager.getWriteableFormat(Tree.NULL));

        assertEquals(List.of("world.hello hello.world"), treeStorageManager.getWriteableFormat(TREE1));
        assertEquals(List.of("world_hello Hello.png", "world_hello? Hello World.java"),
                treeStorageManager.getWriteableFormat(TREE2));
    }
}
