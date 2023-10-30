package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.employee.Employee;

/**
 * Represents a task which belongs to a certain project.
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS = "You must enter a name for a task!\n"
            + "You must also enter a deadline, which should be in the format: dd-mm-yyyy HHmm\n "
            + "e.g., addT n/name pr/1 d/11-11-2023 2359";

    /**
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String name;
    private final LocalDateTime deadline;
    private boolean isDone;
    private List<Employee> employee;
    /**
     * Constructs a {@code Task}
     * @param taskName The name of the task.
     */
    public Task(String taskName, LocalDateTime deadline) {
        requireNonNull(taskName);
        requireNonNull(deadline);
        name = taskName;
        this.deadline = deadline;
        this.isDone = false;
        this.employee = new ArrayList<>();
    }

    /**
     * Constructs a {@code Task} with a pre-determined Done status - for loading of tasks from taskhub.json.
     * @param taskName The name of the task.
     */
    public Task(String taskName, LocalDateTime deadline, Boolean isDone) {
        requireNonNull(taskName);
        requireNonNull(deadline);
        name = taskName;
        this.deadline = deadline;
        this.isDone = isDone;
        this.employee = new ArrayList<>();
    }

    /**
     * Constructs a Task with an assigned employee.
     * - for loading of tasks from taskhub.json
     * @param taskName The name of the task.
     * @param employee The employee that was assigned to the task.
     */
    public Task(String taskName, LocalDateTime deadline, Employee employee) {
        requireNonNull(taskName);
        requireNonNull(deadline);
        requireNonNull(employee);
        name = taskName;
        this.deadline = deadline;
        this.isDone = false;
        this.employee = Collections.singletonList(employee);
    }

    /**
     * Constructs a Task with a pre-determined Done status and assigned employee
     * - for loading of tasks from taskhub.json.
     * @param taskName The name of the task.
     * @param employee The employee that was assigned to the task.
     */
    public Task(String taskName, LocalDateTime deadline, Boolean isDone, Employee employee) {
        requireNonNull(taskName);
        requireNonNull(deadline);
        requireNonNull(employee);
        name = taskName;
        this.deadline = deadline;
        this.isDone = isDone;
        this.employee = Collections.singletonList(employee);
    }

    /**
     * Constructs a Task with a pre-determined Done status and list of employees - for loading of tasks
     * from taskhub.json.
     * @param taskName The name of the task.
     * @param employeeList The employees assigned to the task.
     */
    public Task(String taskName, LocalDateTime deadline, Boolean isDone, List<Employee> employeeList) {
        requireNonNull(taskName);
        requireNonNull(deadline);
        name = taskName;
        this.deadline = deadline;
        this.isDone = isDone;
        this.employee = employeeList;
    }


    /**
     * Returns true if a given string is a valid project name.
     */
    public static boolean isValidTask(String testString) {
        return testString.matches(VALIDATION_REGEX);
    }

    /**
     * Tells us if a task is done.
     * @return A boolean value indicating if the task is done.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks a task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    public List<Employee> getEmployee() {
        return this.employee;
    }

    public String getName() {
        return this.name;
    }
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        String completionString = this.isDone() ? "[X]" : "[]";
        return completionString + " | "
                                    + this.name + " | "
                                        + this.employee + " | "
                                            + DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm").format(this.deadline);
    }

    /**
     * Determines if a string is of the format "dd-MM-yyyy HHmm" i.e., a valid LocalDateTime object.
     *
     * @param input The string to be examined.
     * @return A boolean value which tells us if the string represents a LocalDate.
     */
    public static boolean isValidDateTime(String input) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        try {
            LocalDateTime.parse(input, dtf);
            return true; // Parsing success: Valid date/time
        } catch (Exception e) {
            return false; // Parsing failed: Invalid date/time
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Task)) {
            return false;
        }
        Task castedTask = (Task) other;
        return this.name.equals(castedTask.name)
                    && this.deadline.equals(castedTask.deadline);
    }
}
