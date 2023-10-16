package seedu.address.ui;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2324s1-cs2103t-t08-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Hyperlink helpMessage;

    /**
     * Initializes the HelpWindow FXML.
     */
    @FXML
    public void initialize() {
        Map<String, String> generalCommands = new HashMap<>();
        Map<String, String> employeeCommands = new HashMap<>();
        Map<String, String> projectCommands = new HashMap<>();

        generalCommands.put("help",
                "- Get help pop-up to display.");
        generalCommands.put("clear",
                "- Clears all entries from TaskHub.");
        generalCommands.put("exit",
                "- Exits the program.");

        employeeCommands.put("addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B",
                "- Adds an employee to the employees list.");
        employeeCommands.put("listE",
                "- Shows a list of all employees in TaskHub.");
        employeeCommands.put("editE INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\u200B",
                "- Edits an existing employee in TaskHub.");
        employeeCommands.put("findE KEYWORD [MORE_KEYWORDS]",
                "- Finds employees whose names contain any of the given keywords.");
        employeeCommands.put("deleteE INDEX",
                "- Deletes the specified employee from the employees list.");
        employeeCommands.put("assignE pr/PROJECT_NAME e/EMPLOYEE_NAME [e/MORE_EMPLOYEE_NAMES]...",
                "- Assigns employee(s) to a project in TaskHub.");

        projectCommands.put("listP",
                "- Shows a list of all projects in TaskHub.");
        projectCommands.put("addP pr/PROJECT_NAME [e/EMPLOYEE_INDEX]...",
                "- Adds a new project with the employees assigned to the project.");
        projectCommands.put("deleteP INDEX",
                "- Deletes the specified project from TaskHub.");

        addToVBox("General Commands", generalCommands);
        addToVBox("Employee Commands", employeeCommands);
        addToVBox("Project Commands", projectCommands);
    }

    private void addToVBox(String header, Map<String, String> commands) {
        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: white;");
        vBox.getChildren().add(headerLabel);
        vBox.getChildren().add(new Separator());

        for (String command : commands.keySet()) {
            String description = commands.get(command);
            Text commandText = new Text(command);
            commandText.setStyle("-fx-font-family: 'Monospaced'; -fx-fill: white;");
            Text descriptionText = new Text(description);
            descriptionText.setStyle("-fx-fill: white;");
            vBox.getChildren().addAll(commandText, descriptionText);
            Separator separator = new Separator();
            vBox.getChildren().add(separator);
        }
    }

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        // this is done to make sure that we start at the top of the scrollPane.
        getRoot().setOnShown(event -> {
            scrollPane.applyCss();
            scrollPane.layout();
            scrollPane.setVvalue(0.5);
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();

        Platform.runLater(() -> {
            scrollPane.applyCss();
            scrollPane.layout();
            scrollPane.setVvalue(0.0);
        });
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


    @FXML
    private void openUserGuide() {
        // Open the user guide in the default web browser
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }
    }
}
