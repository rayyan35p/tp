package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Unassigns a task in a project from the currently assigned employee.
 */
public class UnassignTaskCommand extends Command {
    public static final String COMMAND_WORD = "unassignT";
    public static final String MESSAGE_SUCCESS = "Unassigned task: %1$s from %2$s";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Unassigns an employee in a project from a task in the project.\n"
                    + "Parameters: "
                    + PREFIX_PROJECT + "PROJECT_INDEX "
                    + PREFIX_TASK + "TASK_INDEX\n"
                    + "Example: " + COMMAND_WORD + " " + PREFIX_PROJECT + "1 " + PREFIX_TASK + "2 ";

    private final Index projectIndex;
    private final Index taskIndex;

    /**
     * Constructs an unassignTaskCommand
     * @param projectIndex The index of the project containing the employees and tasks to be unassigned.
     * @param taskIndex The index of the task in the project's list of tasks.
     */
    public UnassignTaskCommand(Index projectIndex, Index taskIndex) {
        requireAllNonNull(projectIndex, taskIndex);
        this.projectIndex = projectIndex;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Project> lastShownProjectList = model.getFilteredProjectList();

        if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
        TaskList projectTaskListToEdit = projectToEdit.getTasks();

        if (taskIndex.getZeroBased() >= projectTaskListToEdit.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = projectTaskListToEdit.getTask(taskIndex);

        // check if there is an employee assigned to begin with
        if (taskToEdit.getEmployee().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_EMPLOYEE_TO_UNASSIGN);
        }
        Employee assignedEmployee = taskToEdit.getEmployee().get(0);

        Task editedTask = new Task(taskToEdit.getName(), taskToEdit.getDeadline(), taskToEdit.isDone());
        TaskList editedTaskList = new TaskList();
        editedTaskList.setTasks(projectTaskListToEdit.asUnmodifiableObservableList());
        editedTaskList.setTask(taskIndex, editedTask);
        Project editedProject = new Project(projectToEdit.getName(),
                projectToEdit.getEmployees(),
                editedTaskList,
                projectToEdit.getPriority(),
                projectToEdit.getDeadline(),
                projectToEdit.getCompletionStatus());
        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                taskToEdit.getName(), assignedEmployee.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignTaskCommand)) {
            return false;
        }

        UnassignTaskCommand otherUnassignTaskCommand = (UnassignTaskCommand) other;
        return projectIndex.equals(otherUnassignTaskCommand.projectIndex)
                && taskIndex.equals(otherUnassignTaskCommand.taskIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectIndex", projectIndex)
                .add("taskIndex", taskIndex)
                .toString();
    }
}
