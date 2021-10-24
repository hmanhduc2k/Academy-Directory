package seedu.academydirectory.logic.commands;

import java.util.Optional;

import seedu.academydirectory.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String HELP_MESSAGE = "### Exiting the program : `exit`\n"
            + "\n"
            + "Exits the program.\n"
            + "\n"
            + "Format: `exit`";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Academy Directory as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, Optional.empty(), CommandResult.Type.EXIT);
    }

}
