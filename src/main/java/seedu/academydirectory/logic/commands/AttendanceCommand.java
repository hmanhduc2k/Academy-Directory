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
import seedu.academydirectory.model.student.Student;
import seedu.academydirectory.model.student.StudioRecord;

public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String HELP_MESSAGE = "### Editing a student's Studio attendance: `attendance`\n"
            + "Avengers will be able to edit the attendance status of their students.\n"
            + "\n"
            + "Format: `attendance s/STUDIO_GROUP k/KEYWORD a/ATTENDANCE_STATUS [i/INDEX]`\n"
            + "\n"
            + "* Edits the attendance of a student or multiple students who have the matching "
            + "`KEYWORD` in their names and in Studio group as defined by `STUDIO_GROUP`.\n"
            + "* Only full words will be matched e.g. `Han` will not match `Hans`.\n"
            + "* The search is case-insensitive. e.g `hans` will match `Hans`.\n"
            + "* If `INDEX` is not supplied, the command will edit the attendance of the student is in the "
            + "last created Studio session. Otherwise, it will execute the edit in the specified Studio "
            + "session if `INDEX` is valid.\n"
            + "* The `ATTENDANCE_STATUS` field can only be a 1 or 0 to indicate whether the student "
            + "attended the session or not.\n"
            + "* Existing values will be updated to the input values.\n"
            + "\n"
            + "Examples:\n"
            + "\n"
            + "* `attendance s/1 k/Aaron a/0`\n"
            + "* `attendance s/33 k/Chan a/1 i/7`";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the attendance status of the student(s) identified "
            + "by the index number used in the last person listing as well "
            + "as the index of the Studio session. Existing attendance will "
            + "be overwritten by the input.\n"
            + "Parameters: INDEX(ES) (must be a positive integer(s))"
            + "ses/ STUDIO_SESSION_INDEX (must be a positive integer within range)"
            + "att/ ATTENDANCE_STATUS (0 or 1)\n"
            + "Example: " + COMMAND_WORD + " 1 ses/ 7 att/ 1";

    public static final String MESSAGE_UPDATE_ATTENDANCE_SUCCESS = "Attendance updated!";

    private final ArrayList<Index> indexArrayList;
    private final boolean attendanceStatus;
    private final Integer studioSession;

    /**
     * @param attendanceStatus true if attended, false otherwise
     * @param studioSession The studio session number
     * @param indexArrayList The ArrayList of students that are involved in the AttendanceCommand
     */
    public AttendanceCommand(boolean attendanceStatus, Integer studioSession, ArrayList<Index> indexArrayList) {
        requireAllNonNull(indexArrayList, attendanceStatus, studioSession);
        this.indexArrayList = indexArrayList;
        this.attendanceStatus = attendanceStatus;
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

        for (Index index : indexArrayList) {
            Student studentToEdit = lastShownList.get(index.getZeroBased());
            Attendance attendanceToEdit = studentToEdit.getAttendance();
            attendanceToEdit = attendanceToEdit.update(studioSession, attendanceStatus);
            StudioRecord newStudioRecord = new StudioRecord(attendanceToEdit, studentToEdit.getParticipation());
            Student editedStudent = new Student(studentToEdit.getName(), studentToEdit.getPhone(),
                    studentToEdit.getEmail(), studentToEdit.getTelegram(), newStudioRecord,
                    studentToEdit.getAssessment(), studentToEdit.getTags());
            model.setStudent(studentToEdit, editedStudent);
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_UPDATE_ATTENDANCE_SUCCESS, Optional.of(MESSAGE_UPDATE_ATTENDANCE_SUCCESS));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceCommand)) {
            return false;
        }

        AttendanceCommand e = (AttendanceCommand) other;
        return indexArrayList.equals(e.indexArrayList)
                && attendanceStatus == e.attendanceStatus
                && studioSession.equals(e.studioSession);
    }



}
