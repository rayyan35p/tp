package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Assigns a task in a project to employee(s) in that project.
 */
public class AssignTaskCommand extends Command {
    public static final String COMMAND_WORD = "assignT";
    public static final String MESSAGE_SUCCESS = "Assigned task: %1$s to employee, %2$s";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Assigns an employee in a project to a task in the project..\n"
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT_INDEX "
            + PREFIX_TASK + "TASK_INDEX "
            + PREFIX_EMPLOYEE + "EMPLOYEE_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROJECT + "1 " + PREFIX_TASK + "2 " + PREFIX_EMPLOYEE + "3 ";

    private final Index projectIndex;
    private final Index taskIndex;
    private final Index employeeIndex;

    /**
     * Constructs an AssignTaskCommand
     * @param projectIndex The index of the project containing the employees and tasks to be assigned.
     * @param taskIndex The index of the task in the project's list of tasks.
     * @param employeeIndex The index of the employee in the project's list of employees.
     */
    public AssignTaskCommand(Index projectIndex, Index taskIndex, Index employeeIndex) {
        requireAllNonNull(projectIndex, taskIndex, employeeIndex);
        this.projectIndex = projectIndex;
        this.taskIndex = taskIndex;
        this.employeeIndex = employeeIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Project> lastShownProjectList = model.getFilteredProjectList();

        if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
        UniqueEmployeeList projectEmployeeList = projectToEdit.getEmployees();
        TaskList projectTaskListToEdit = projectToEdit.getTasks();

        if (employeeIndex.getZeroBased() >= projectEmployeeList.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        List<Employee> employeeList = projectEmployeeList.asUnmodifiableObservableList();
        Employee employeeToAdd = employeeList.get(employeeIndex.getZeroBased());

        if (taskIndex.getZeroBased() >= projectTaskListToEdit.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = projectTaskListToEdit.getTask(taskIndex);
        Task editedTask = new Task(taskToEdit.getName(), taskToEdit.getDeadline(), taskToEdit.isDone(), employeeToAdd);
        TaskList editedTaskList = new TaskList();
        editedTaskList.setTasks(projectTaskListToEdit.asUnmodifiableObservableList());
        editedTaskList.setTask(taskIndex, editedTask);
        Project editedProject = new Project(projectToEdit.getName(),
                projectToEdit.getEmployees(),
                editedTaskList,
                projectToEdit.getProjectPriority(),
                projectToEdit.getDeadline(),
                projectToEdit.getCompletionStatus());
        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                taskToEdit.getName(), employeeToAdd.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignTaskCommand)) {
            return false;
        }

        AssignTaskCommand otherAssignTaskCommand = (AssignTaskCommand) other;
        return projectIndex.equals(otherAssignTaskCommand.projectIndex)
                && taskIndex.equals(otherAssignTaskCommand.taskIndex)
                && employeeIndex.equals(otherAssignTaskCommand.employeeIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectIndex", projectIndex)
                .add("taskIndex", taskIndex)
                .add("employeeIndex", employeeIndex)
                .toString();
    }
}
