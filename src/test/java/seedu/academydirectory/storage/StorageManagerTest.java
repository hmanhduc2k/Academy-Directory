package seedu.academydirectory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.academydirectory.testutil.TypicalStudents.getTypicalAcademyDirectory;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.academydirectory.commons.core.GuiSettings;
import seedu.academydirectory.model.AcademyDirectory;
import seedu.academydirectory.model.ReadOnlyAcademyDirectory;
import seedu.academydirectory.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAcademyDirectoryStorage academyDirectoryStorage = new JsonAcademyDirectoryStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(academyDirectoryStorage, userPrefsStorage, getTempFilePath("vc"));
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void academyDirectoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAcademyDirectoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAcademyDirectoryStorageTest} class.
         */
        AcademyDirectory original = getTypicalAcademyDirectory();
        storageManager.saveAcademyDirectory(original);
        ReadOnlyAcademyDirectory retrieved = storageManager.readAcademyDirectory().get();
        assertEquals(original, new AcademyDirectory(retrieved));
    }

    @Test
    public void getAcademyDirectoryFilePath() {
        assertNotNull(storageManager.getAcademyDirectoryFilePath());
    }

}
