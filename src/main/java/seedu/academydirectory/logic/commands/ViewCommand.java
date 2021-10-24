package seedu.academydirectory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.academydirectory.commons.core.Messages;
import seedu.academydirectory.commons.core.index.Index;
import seedu.academydirectory.logic.commands.exceptions.CommandException;
import seedu.academydirectory.model.Model;
import seedu.academydirectory.model.student.Student;

public class ViewCommand extends Command {

    private Index index;

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View full information about the student.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "View student %1$s";

    public ViewCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> studentList = model.getFilteredStudentList();
        if (index.getZeroBased() >= studentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student studentToView = studentList.get(index.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToView));
    }
}
