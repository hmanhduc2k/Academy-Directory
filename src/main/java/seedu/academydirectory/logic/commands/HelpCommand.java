package seedu.academydirectory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.academydirectory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.academydirectory.commons.core.Messages;
import seedu.academydirectory.logic.commands.exceptions.CommandException;
import seedu.academydirectory.model.VersionedModel;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String DEFAULT_MESSAGE = Messages.GENERAL_HELP_MESSAGE;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows program usage instructions for the command in query\n"
            + "Example: " + COMMAND_WORD + "add";
    public static final String MESSAGE_HELP_SUCCESS_GENERAL = "Showing summary help.";
    public static final String MESSAGE_HELP_SUCCESS_SPECIFIC = "Show help for command: %1$s.";

    private final String commandWord;
    private final String helpMessage;
    private final boolean isGeneralHelp;

    /**
     * Default constructor of help, resulting in a summary table on the help window.
     */
    public HelpCommand() {
        this.commandWord = COMMAND_WORD;
        this.helpMessage = DEFAULT_MESSAGE;
        this.isGeneralHelp = true;
    }

    /**
     * Constructor for the Help command
     * @param helpMessage of the command needed to be clarified by the users
     */
    public HelpCommand(String commandWord, String helpMessage) {
        requireAllNonNull(helpMessage, commandWord);
        this.commandWord = commandWord;
        this.helpMessage = helpMessage;
        this.isGeneralHelp = false;
    }

    @Override
    public CommandResult execute(VersionedModel model) throws CommandException {
        requireNonNull(model);
        if (this.commandWord == null || this.helpMessage == null) {
            throw new CommandException(Messages.MESSAGE_HELP_NOT_EXIST);
        }
        if (isGeneralHelp) {
            return new CommandResult(MESSAGE_HELP_SUCCESS_GENERAL,
                    Optional.of(helpMessage), CommandResult.Type.HELP);
        } else {
            return new CommandResult(String.format(MESSAGE_HELP_SUCCESS_SPECIFIC, this.commandWord),
                    Optional.of(helpMessage), CommandResult.Type.HELP);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HelpCommand)) {
            return false;
        }
        HelpCommand curr = (HelpCommand) obj;
        return curr.helpMessage.equals(this.helpMessage) && curr.commandWord.equals(this.commandWord);
    }
}
