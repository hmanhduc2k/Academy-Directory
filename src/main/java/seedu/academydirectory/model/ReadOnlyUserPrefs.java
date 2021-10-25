package seedu.academydirectory.model;

import java.nio.file.Path;

import seedu.academydirectory.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAcademyDirectoryFilePath();

    Path getVersionControlPath();

}
