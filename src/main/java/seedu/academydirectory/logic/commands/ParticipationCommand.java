package seedu.academydirectory.logic.commands;

import static seedu.academydirectory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.academydirectory.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.academydirectory.commons.core.Messages;
import seedu.academydirectory.commons.core.index.Index;
import seedu.academydirectory.logic.commands.exceptions.CommandException;
import seedu.academydirectory.model.VersionedModel;
import seedu.academydirectory.model.student.Attendance;
import seedu.academydirectory.model.student.Participation;
import seedu.academydirectory.model.student.Student;

public class ParticipationCommand extends Command {

    public static final String COMMAND_WORD = "part";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the participation count of the student(s) identified "
            + "by the index number used in the last person listing as well "
            + "as the index of the Studio session. Existing participation will "
            + "be overwritten by the input or incremented/decremented depending on the flag.\n"
            + "Parameters: INDEX(ES) (must be a positive integer(s))"
            + "ses/ STUDIO_SESSION_INDEX (must be a positive integer within range)"
            + "add/ PARTICIPATION_TO_ADD"
            + "Example: " + COMMAND_WORD + " 1 ses/ 7 add/ 1";

    public static final String MESSAGE_UPDATE_PARTICIPATION_SUCCESS = "Participation updated!";

    private final ArrayList<Index> indexArrayList;
    private final Integer studioSession;
    private final Integer participationUpdate;

    /**
     * @param participationUpdate The new updated participation count
     * @param studioSession The studio session number
     * @param indexArrayList The ArrayList of students that are involved in the ParticipationCommand
     */
    public ParticipationCommand(Integer participationUpdate, Integer studioSession,
                                ArrayList<Index> indexArrayList) {
        requireAllNonNull(indexArrayList, participationUpdate, studioSession);
        this.indexArrayList = indexArrayList;
        this.participationUpdate = participationUpdate;
        this.studioSession = studioSession;
    }

    @Override
    public CommandResult execute(VersionedModel model) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        for (Index index : indexArrayList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
        }

        if (participationUpdate > 0) {
            int sessionCount = model.getFilteredStudentList().get(0).getParticipation().getSessionCount();
            for (Index index : indexArrayList) {
                Student studentToEdit = lastShownList.get(index.getZeroBased());
                boolean[] newAttArr = studentToEdit.getAttendance().getAttendanceArray();
                newAttArr[studioSession - 1] = true;
                Attendance newAttendance = new Attendance(sessionCount);
                newAttendance.setAttendance(newAttArr);
                studentToEdit.setAttendance(newAttendance);
            }
        }

        for (Index index : indexArrayList) {
            Student studentToEdit = lastShownList.get(index.getZeroBased());
            Participation participationToEdit = studentToEdit.getParticipation();
            participationToEdit = participationToEdit.add(studioSession, participationUpdate);

            Student editedStudent = new Student(
                    studentToEdit.getName(), studentToEdit.getPhone(), studentToEdit.getEmail(),
                    studentToEdit.getTelegram(), studentToEdit.getStudioRecord(),
                    studentToEdit.getAssessment(), studentToEdit.getTags());
            editedStudent.setParticipation(participationToEdit);
            model.setStudent(studentToEdit, editedStudent);
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_UPDATE_PARTICIPATION_SUCCESS,
                Optional.of(MESSAGE_UPDATE_PARTICIPATION_SUCCESS));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ParticipationCommand)) {
            return false;
        }
        ParticipationCommand e = (ParticipationCommand) other;
        return indexArrayList.equals(e.indexArrayList)
                && participationUpdate == e.participationUpdate
                && studioSession.equals(e.studioSession);
    }



}
