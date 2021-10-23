package seedu.academydirectory.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private StackPane placeHolder;

    private boolean isView;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        placeHolder.getChildren().clear();
        resultDisplay.setText(feedbackToUser);
        if (feedbackToUser.contains("Psyche")) {
            // TODO: If it's a view command, view everything.
            placeHolder.getChildren().add(new TextArea("Psyche"));
        } else {
            placeHolder.getChildren().add(resultDisplay);
        }
    }
}
