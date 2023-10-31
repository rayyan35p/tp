package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Represents a Project in TaskHub.
 * Guarantees: immutable; is always valid
 */
public class Project {

    public static final String MESSAGE_CONSTRAINTS = "Projects can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String name;
    public final UniqueEmployeeList employeeList;
    private final TaskList tasks;
    private final Deadline deadline;
    private final ProjectPriority projectPriority;
    private final CompletionStatus completionStatus;


    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     */
    public Project(String project) {
        requireNonNull(project);
        this.name = project;
        this.projectPriority = new ProjectPriority("normal");
        this.deadline = new Deadline("");
        this.employeeList = new UniqueEmployeeList();
        this.completionStatus = new CompletionStatus(false);
        this.tasks = new TaskList();
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     * @param employees A list of Employees that are in the project
     * @param tasks A list of Tasks that are in the project
     * @param priority A valid ProjectPriority for the project.
     * @param deadline A valid Deadline for the project.
     * @param completionStatus A valid CompletionStatus for the project.
     */
    public Project(String project,
                   UniqueEmployeeList employees,
                   TaskList tasks,
                   ProjectPriority priority,
                   Deadline deadline,
                   CompletionStatus completionStatus) {
        requireNonNull(project);
        this.name = project;
        this.projectPriority = priority;
        this.employeeList = employees;
        this.tasks = tasks;
        this.deadline = deadline;
        this.completionStatus = completionStatus;
    }

    public String getNameString() {
        return this.name;
    }

    public UniqueEmployeeList getEmployees() {
        return employeeList;
    }

    public ProjectPriority getProjectPriority() {
        return projectPriority;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    /**
     * Returns true if a given string is a valid project name.
     */
    public static boolean isValidProject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adds an employee to the Project.
     */
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    /**
     * Removes the employee from the Project.
     */
    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    /**
     * Returns true if both projects have the same name.
     * This defines a weaker notion of equality between two projects.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.name.equals(this.name);
    }

    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the project
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the project
     * @param task The task to be removed.
     */

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public String getListOfEmployeeNames() {
        StringBuilder employeeListString = new StringBuilder();
        int index = 1;
        for (Employee employee : employeeList) {
            employeeListString.append(index + ". " + employee.getName() + "\n");
            index++;
        }
        return employeeListString.toString();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Project // instanceof handles nulls
                && name.equals(((Project) other).name)
                && (Objects.equals(employeeList, ((Project) other).employeeList)));
        // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
