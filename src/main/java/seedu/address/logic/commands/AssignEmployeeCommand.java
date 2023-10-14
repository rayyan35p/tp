package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

/**
 * Changes the remark of an existing employee in the TaskHub.
 */
public class AssignEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "assignP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the project of the employee identified "
            + "by the index number used in the last employee listing. "
            + "Existing project will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [PROJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Project: %2$s";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Added project to Employee: %1$s";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Removed project from Employee: %1$s";


    private final Index index;
    private final Project project;

    /**
     * @param index of the employee in the filtered employee list to edit the remark
     * @param project of the employee to be updated to
     */
    public AssignEmployeeCommand(Index index, Project project) {
        requireAllNonNull(index, project);

        this.index = index;
        this.project = project;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToEdit = lastShownList.get(index.getZeroBased());
        Employee editedEmployee = new Employee(
                employeeToEdit.getName(), employeeToEdit.getPhone(), employeeToEdit.getEmail(),
                employeeToEdit.getAddress(), project, employeeToEdit.getTags());

        model.setEmployee(employeeToEdit, editedEmployee);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);

        return new CommandResult(generateSuccessMessage(editedEmployee));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code employeeToEdit}.
     */
    private String generateSuccessMessage(Employee employeeToEdit) {
        String message = !project.value.isEmpty() ? MESSAGE_ADD_PROJECT_SUCCESS : MESSAGE_DELETE_PROJECT_SUCCESS;
        return String.format(message, employeeToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignEmployeeCommand)) {
            return false;
        }

        AssignEmployeeCommand e = (AssignEmployeeCommand) other;
        return index.equals(e.index)
                && project.equals(e.project);
    }
}
