package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
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
     * Initializes the HelpWindow FXML.
     */
    @FXML
    public void initialize() {
        Map<String, String> generalCommands = new HashMap<>();
        Map<String, String> employeeCommands = new HashMap<>();
        Map<String, String> projectCommands = new HashMap<>();
        Map<String, String> taskCommands = new HashMap<>();
        Map<String, String> assignCommands = new HashMap<>();

        assignCommands.put("assignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]…\u200b",
                "- Assigns employee(s) to a project in TaskHub");
        assignCommands.put("unassignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]…\u200b",
                "- Un-assigns employee(s) from a project in TaskHub");
        assignCommands.put("assignT pr/PROJECT_INDEX t/TASK_INDEX em/EMPLOYEE_INDEX",
                "- Assigns a specified employee in a specified project to a specified task in that project.");
        assignCommands.put("unassignT pr/PROJECT_INDEX t/TASK_INDEX",
                "- Un-assigns the currently assigned employee from the specified task in the specified project.");

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

        projectCommands.put("listP",
                "- Shows a list of all projects in TaskHub.");
        projectCommands.put("addP pr/PROJECT_NAME [em/EMPLOYEE_INDEX]…\u200B",
                "- Adds a new project with the employees assigned to the project.");
        projectCommands.put("findP KEYWORD [MORE_KEYWORDS]",
                "- Finds projects whose names contain any of the given keywords. \n" +
                "Additionally, it shows only the employees that are under these projects.");
        projectCommands.put("deleteP INDEX",
                "- Deletes the specified project from TaskHub.");
        projectCommands.put("dl INDEX d/DATE",
                "- Edit the deadline of a project in the projects list.");
        projectCommands.put("priorityP INDEX priority/PRIORITY",
                "- Sets a priority for a specified project in TaskHub.");
        projectCommands.put("markP INDEX [MORE_INDEXES]",
                "- Marks the specified project(s) as completed in TaskHub.");
        projectCommands.put("unmarkP INDEX [MORE_INDEXES]",
                "- Marks the specified project(s) as incomplete in TaskHub.");

        taskCommands.put("addT pr/PROJECT_INDEX [em/EMPLOYEE_INDEX] n/TASK_NAME d/DEADLINE(dd-MM-yyyy HHmm)",
                "- Adds a new task to the specified project in TaskHub and assigns it to the specified employee (" +
                        "if there is any).");
        taskCommands.put("deleteT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]",
                "- Deletes the specified task(s) from the specified project in TaskHub");
        taskCommands.put("markT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]",
                "- Marks the specified task(s) of a specified project as completed in TaskHub.");
        taskCommands.put("unmarkT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]",
                "- Marks the specified task(s) of a specified project as incomplete in TaskHub.");
        taskCommands.put("sortT",
                "- Sorts the tasks in each project by their deadline and completion status.");

        addToVBox("General Commands", generalCommands);
        addToVBox("Assign Commands", assignCommands);
        addToVBox("Employee Commands", employeeCommands);
        addToVBox("Project Commands", projectCommands);
        addToVBox("Task Commands", taskCommands);
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
