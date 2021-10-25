package seedu.academydirectory.model;

import java.io.IOException;

import seedu.academydirectory.versioncontrol.objects.Commit;
import seedu.academydirectory.versioncontrol.objects.StageArea;

public interface VersionedModel extends Model {
    /**
     * Commits a change and tie the change with a given message
     * @param message Message attached to the Commit for a readable explanation
     */
    void commit(String message);

    Commit revert(String fiveCharHash) throws IOException;

    StageArea getStageArea();

    Commit getHeadCommit();

    Commit fetchCommitByLabel(String labelName);
}
