package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.model.employee.Employee;

/**
 * Represents a task which belongs to a certain project.
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS = "Tasks must be bound to a project first"
        + " and later assigned to an Employee";

    /**
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String name;
    private boolean isDone;
    private Employee employee;
    private final LocalDateTime deadline;
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
    }

    /**
     * Returns true if a given string is a valid project name.
     */
    public static boolean isValidTask(String testString) {
        return testString.matches(VALIDATION_REGEX);
    }

    /**
     * Assigns an employee to the task.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public Employee getEmployee() {
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
                                            + DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").format(this.deadline);
    }

    /**
     * Determines if a string is of the format "yyyy-MM-dd HHmm" i.e., a valid LocalDateTime object.
     *
     * @param input The string to be examined.
     * @return A boolean value which tells us if the string represents a LocalDate.
     */
    public static boolean isValidDateTime(String input) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{4}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Task) {
            Task castedTask = (Task) other;
            return this.name.equals(castedTask.name)
                        && this.deadline.equals(castedTask.deadline);
        }
        return false;
    }
}
