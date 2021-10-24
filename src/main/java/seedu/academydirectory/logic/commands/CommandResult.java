package seedu.academydirectory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** Type of command that needs special treatment from UI */
    public enum Type {
        DEFAULT, HELP, VIEW, EXIT, STATISTIC
    }

    /** Feedback to be sent to user on the result display*/
    private final String feedbackToUser;

    /** Additional Information to be displayed, to change to AdditionalInformation */
    private final Optional additionalInformation;

    /** Type of command */
    private final Type type;


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Optional additionalInformation, Type type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.additionalInformation = additionalInformation;
        this.type = type;
    }

    /**
     * Constructs a {@code CommandResult} that is default only concerning feedback to users
     * @param feedbackToUser
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, Optional.empty(), Type.DEFAULT);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }


    public boolean isShowHelp() {
        return (type.equals(Type.HELP));
    }

    public boolean isExit() {
        return (type.equals(Type.EXIT));
    }

    public boolean isView() {
        return (type.equals(Type.VIEW));
    }

    public Optional getAdditionalInformation() {
        return this.additionalInformation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && additionalInformation.equals(otherCommandResult.additionalInformation)
                && type.equals(otherCommandResult.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, additionalInformation, type);
    }
}
