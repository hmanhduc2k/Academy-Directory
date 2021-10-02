package seedu.academydirectory.logic.commands;

import static seedu.academydirectory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.academydirectory.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.academydirectory.commons.core.Messages;
import seedu.academydirectory.commons.core.index.Index;
import seedu.academydirectory.logic.commands.exceptions.CommandException;
import seedu.academydirectory.model.Model;
import seedu.academydirectory.model.person.Attendance;
import seedu.academydirectory.model.person.Person;

public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the attendance status of the student(s) identified "
            + "by the index number used in the last person listing as well "
            + "as the index of the Studio session. Existing attendance will "
            + "be overwritten by the input.\n"
            + "Parameters: INDEX(ES) (must be a positive integer(s))"
            + "ses/ STUDIO_SESSION_INDEX (must be a positive integer within range)"
            + "att/ ATTENDANCE_STATUS (0 or 1)\n"
            + "Example: " + COMMAND_WORD + " * s/ 7 a/ 1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Attendance command not implemented yet";

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
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indexArrayList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : indexArrayList) {
            Person personToEdit = lastShownList.get(index.getZeroBased());
            Attendance attendanceToEdit = personToEdit.getAttendance();
            attendanceToEdit = attendanceToEdit.update(studioSession, attendanceStatus);
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags());
            editedPerson.setAttendance(attendanceToEdit);
            model.setPerson(personToEdit, editedPerson);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("Attendance updated!");


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
