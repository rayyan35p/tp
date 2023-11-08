package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Name;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * A utility class to help with building Project objects.
 * Default {@code ProjectBuilder} has name "Alpha", typical employee Alice, typical task ALPHA, and normal priority.
 */
public class ProjectBuilder {
    public static final String DEFAULT_NAME = "Alpha";
    public static final String DEFAULT_PRIORITY = "normal";
    public static final String DEFAULT_DEADLINE = "";
    public static final boolean DEFAULT_COMPLETION_STATUS = false;

    private Name projectName;
    private UniqueEmployeeList employeeList;
    private TaskList taskList;
    private Deadline deadline;
    private Priority priority;
    private CompletionStatus completionStatus;

    /**
     * Instantiates a {@code Project} with the default details
     */
    public ProjectBuilder() {
        projectName = new Name(DEFAULT_NAME);
        employeeList = new UniqueEmployeeList();
        employeeList.add(ALICE);
        taskList = new TaskList();
        priority = new Priority(DEFAULT_PRIORITY);
        deadline = new Deadline(DEFAULT_DEADLINE);
        completionStatus = new CompletionStatus(DEFAULT_COMPLETION_STATUS);
    }

    /**
     * Initializes the ProjectBuilder with details of {@code toCopy}
     */
    public ProjectBuilder(Project toCopy) {
        projectName = toCopy.getName();
        employeeList = toCopy.getEmployees();
        taskList = toCopy.getTasks();
        priority = toCopy.getPriority();
        deadline = toCopy.getDeadline();
        completionStatus = toCopy.getCompletionStatus();
    }

    /**
     * Sets the name of the {@code Project} we are building.
     */
    public ProjectBuilder withName(String name) {
        this.projectName = new Name(name);
        return this;
    }

    /**
     * Sets the employees of the {@code Project} we are building.
     */
    public ProjectBuilder withEmployees(Employee... employees) {
        this.employeeList = new UniqueEmployeeList();
        for (Employee employee : employees) {
            employeeList.add(employee);
        }
        return this;
    }

    /**
     * Sets the tasks of the {@code Project} we are building.
     */
    public ProjectBuilder withTasks(Task... tasks) {
        this.taskList = new TaskList();
        for (Task task : tasks) {
            taskList.add(task);
        }
        return this;
    }

    /**
     * Sets the priority of the {@code Project} we are building.
     */
    public ProjectBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Project} we are building.
     */
    public ProjectBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code CompletionStatus} of the {@code Project} we are building.
     */
    public ProjectBuilder withCompletionStatus(boolean status) {
        this.completionStatus = new CompletionStatus(status);
        return this;
    }

    public Project build() {
        return new Project(projectName, employeeList, taskList, priority, deadline, completionStatus);
    }
}
