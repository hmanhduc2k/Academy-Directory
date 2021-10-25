package seedu.academydirectory.ui;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class VisualizeDisplay extends UiPart<Region> {
    private static final String FXML = "VisualizeDisplay.fxml";

    @FXML
    private StackPane placeHolder;

    public VisualizeDisplay() {
        super(FXML);
    }

    public void setVisualizer(Node node) {
        placeHolder.getChildren().clear();
        placeHolder.getChildren().add(node);
    }
}
