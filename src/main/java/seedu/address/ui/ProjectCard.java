package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.project.Project;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class ProjectCard extends UiPart<Region> {

    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label projects;
    @FXML
    private Label priority;
    @FXML
    private Label deadline;
    @FXML
    private StackPane taskListPlaceholder;
    @FXML
    private CheckBox completionStatus;
    @FXML
    private Label completionStatusText;


    /**
     * Creates a {@code ProjectCode} with the given {@code Project} and index to display.
     */
    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;

        // Set the index
        id.setText(displayedIndex + ". ");

        // Set the name
        name.setText(project.getNameString());

        // Set the list of employees
        String listOfEmployeesString =
                project.getEmployees().asUnmodifiableObservableList().size() == 0
                ? "No members yet."
                : "Members: " + project.getListOfEmployeeNames();
        projects.setText(listOfEmployeesString);

        // Set the priority
        String priorityString = project.getProjectPriority().toString();
        priority.setText("Priority: " + priorityString);
        if (priorityString.equals("low")) {
            priority.setStyle("-fx-text-fill: green;");
        }
        if (priorityString.equals("high")) {
            priority.setStyle("-fx-text-fill: red;");
        }

        TaskListPanel taskListPanel = new TaskListPanel(project.getTasks().asUnmodifiableObservableList());
        taskListPlaceholder.getChildren().add(taskListPanel.getRoot());

        // Set the deadline
        if (project.getDeadline().value.isEmpty()) {
            deadline.setText("No deadline set");
        } else {
            LocalDate currentDateTime = LocalDate.now(); // Get the current date
            LocalDate deadlineDate = project.getDeadline().getLocalDate(); // Get project deadline

            String formattedDeadline = deadlineDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            deadline.setText("Deadline: " + formattedDeadline);

            // Compare project deadline with current date
            if (deadlineDate.isBefore(currentDateTime)) {
                // Set the style to red if the deadline is in the past
                deadline.setStyle("-fx-text-fill: red;");
            } else {
                // Set the style to green if the deadline is in the future
                deadline.setStyle("-fx-text-fill: green;");
            }
        }

        // Set the completion status
        completionStatusText.setText("Completed?:");
        completionStatus.setSelected(project.getCompletionStatus().isCompleted);
        // Prevent user from interacting with the checkbox through GUI
        completionStatus.setDisable(true);
    }
}
