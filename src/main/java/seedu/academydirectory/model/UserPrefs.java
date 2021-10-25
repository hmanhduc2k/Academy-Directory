package seedu.academydirectory.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.academydirectory.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path academyDirectoryFilePath = Paths.get("data" , "academydirectory.json");
    private Path versionControlPath = Paths.get("data", "vc");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAcademyDirectoryFilePath(newUserPrefs.getAcademyDirectoryFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAcademyDirectoryFilePath() {
        return academyDirectoryFilePath;
    }

    public void setAcademyDirectoryFilePath(Path academyDirectoryFilePath) {
        requireNonNull(academyDirectoryFilePath);
        this.academyDirectoryFilePath = academyDirectoryFilePath;
    }

    @Override
    public Path getVersionControlPath() {
        return versionControlPath;
    }

    public void setVersionControlPath(Path versionControlPath) {
        requireNonNull(versionControlPath);
        this.versionControlPath = versionControlPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && academyDirectoryFilePath.equals(o.academyDirectoryFilePath)
                && versionControlPath.equals(o.versionControlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, academyDirectoryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + academyDirectoryFilePath);
        sb.append("\nVersion Control location : " + versionControlPath);
        return sb.toString();
    }

}
