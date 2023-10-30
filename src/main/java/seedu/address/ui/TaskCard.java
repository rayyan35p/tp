package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;


/**
 * A UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private CheckBox isDone;
    @FXML
    private Label name;
    @FXML
    private ImageView employeeIcon;

    @FXML
    private Label employee;
    @FXML
    private ImageView deadlineIcon;
    @FXML
    private Label deadline;

    @FXML
    private Label tasks;



    /**
     * Creates a {@code TaskCode} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;

        // Set the index
        id.setText(displayedIndex + ". ");

        // Set the done status
        isDone.setSelected(task.isDone());

        // Set the taskName
        name.setText(task.getName());

        Image eIcon = new Image(getClass().getResourceAsStream("/images/employee_icon.png"));
        employeeIcon.setImage(eIcon);

        // Set the employee
        // TODO: Make a new class for single-item list for the employee in a task
        if (!task.getEmployee().isEmpty()) {
            employee.setText(task.getEmployee().get(0).getName().toString());
        } else {
            employee.setText("No one assigned.");
        }

        // Set the deadline icon
        Image dIcon = new Image(getClass().getResourceAsStream("/images/calendar_white.png"));
        deadlineIcon.setImage(dIcon);
        ColorAdjust colorAdjust = new ColorAdjust();

        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date
        LocalDateTime deadlineDateTime = task.getDeadline(); // Get task deadline
        String formattedDeadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy\n hh:mma"));
        deadline.setText(formattedDeadline);

        // Compare Task deadline with current date
        if (deadlineDateTime.isBefore(currentDateTime)) {
            // Set the style to red if the deadline is in the past
            deadline.setStyle("-fx-text-fill: red; -fx-font-size: 10px; -fx-font-weight: bold");
        } else {
            // Set the style to green if the deadline is in the future
            deadline.setStyle("-fx-text-fill: green; -fx-font-size: 10px; -fx-font-weight: bold");
        }
    }
}
