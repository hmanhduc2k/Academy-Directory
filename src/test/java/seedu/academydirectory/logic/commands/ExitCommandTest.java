package seedu.academydirectory.logic.commands;

import static seedu.academydirectory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academydirectory.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.model.ModelManager;
import seedu.academydirectory.model.VersionedModel;

public class ExitCommandTest {
    private VersionedModel model = new ModelManager();
    private VersionedModel expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                Optional.empty(), CommandResult.Type.EXIT);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
