package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

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
 * Deletes a employee identified using it's displayed index from the TaskHub.
 */
public class DeleteEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "deleteE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the employee identified by the index number used in the displayed employee list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Deleted Employee: %1$s";

    private final Index targetIndex;

    public DeleteEmployeeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEmployee(employeeToDelete);
        model.getTaskHub().getProjectList().stream().forEach(project -> {
            UniqueEmployeeList employeeList = project.getEmployees();
            if (employeeList.contains(employeeToDelete)) {
                employeeList.remove(employeeToDelete);
            }

            TaskList editedTaskList = editTaskList(project, employeeToDelete);
            model.setProject(project, new Project(project.getName(), employeeList, editedTaskList,
                    project.getPriority(), project.getDeadline(), project.getCompletionStatus()));

        });

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, Messages.format(employeeToDelete)));
    }

    /**
     * Creates and returns a TaskList with tasks updated with the employee to be deleted, removed from assigned tasks.
     */
    private TaskList editTaskList(Project projectToEdit, Employee employeeToDelete) {
        requireAllNonNull(projectToEdit, employeeToDelete);
        TaskList editedTaskList = new TaskList();
        editedTaskList.setTasks(projectToEdit.getTasks());
        int i = 0;
        for (Task task : editedTaskList) {
            if (task.getEmployee().isEmpty()) {
                i++;
                continue;
            }
            assert task.getEmployee() != null;
            Employee employeeAssigned = task.getEmployee().get(0);
            if (employeeAssigned.equals(employeeToDelete)) {
                Task editedTask = new Task(task.getName(), task.getDeadline(), task.isDone());
                editedTaskList.setTask(Index.fromZeroBased(i), editedTask);
            }
            i++;
        }
        return editedTaskList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEmployeeCommand)) {
            return false;
        }

        DeleteEmployeeCommand otherDeleteEmployeeCommand = (DeleteEmployeeCommand) other;
        return targetIndex.equals(otherDeleteEmployeeCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
