package seedu.academydirectory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.academydirectory.commons.exceptions.DataConversionException;
import seedu.academydirectory.model.ReadOnlyAcademyDirectory;
import seedu.academydirectory.model.ReadOnlyUserPrefs;
import seedu.academydirectory.model.UserPrefs;
import seedu.academydirectory.versioncontrol.objects.StageArea;

/**
 * API of the StorageManager component
 */
public interface Storage extends AcademyDirectoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAcademyDirectoryFilePath();

    @Override
    Optional<ReadOnlyAcademyDirectory> readAcademyDirectory() throws DataConversionException, IOException;

    @Override
    void saveAcademyDirectory(ReadOnlyAcademyDirectory academyDirectory) throws IOException;

    void saveStageArea(StageArea stageArea) throws IOException;
}
