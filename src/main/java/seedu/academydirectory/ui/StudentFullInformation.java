package seedu.academydirectory.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.academydirectory.model.student.Student;

public class StudentFullInformation extends UiPart<Region> {

    private static final String FXML = "StudentFullInformation.fxml";

    private final Student student;
    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label telegram;

    /**
     * Information to be added later:
     * Student name on the top
     * Assessment, participation, and other things as a drop down
     * Personal information on the bottom, in form of a list
     * To be incorporated with view command
     * @param student Student to be viewed
     * @param displayedIndex  index of the student
     */
    public StudentFullInformation(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        email.setText(student.getEmail().value);
        telegram.setText(student.getTelegram().value);
    }
}
