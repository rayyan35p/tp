package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import java.util.ArrayList;
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
//Todo: revamp class from remarkCommand to assignEmployee command
public class AssignEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "assignE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the project of the employee identified "
            + "by the index number used in the last employee listing. "
            + "Existing project will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [PROJECT]\n"
            + "Example: " + COMMAND_WORD + PREFIX_PROJECT +"1 "
            + PREFIX_EMPLOYEE + "2 3 1";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Updated project : %1$s";


    private final Index projectIndex;
    private final List<Index> employeeIndexes;

    /**
     * @param index of the employee in the filtered employee list to edit the remark
     * @param project of the employee to be updated to
     */
    public AssignEmployeeCommand(Index projectIndex, List<Index> employeeIndexes) {
        requireAllNonNull(projectIndex, employeeIndexes);

        this.projectIndex = projectIndex;
        this.employeeIndexes = employeeIndexes;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Employee> lastShownEmployeeList = model.getFilteredEmployeeList();
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
        Project editedProject = new Project(projectToEdit.name, projectToEdit.employeeList);
        for (Index employeeIndex : employeeIndexes) {
            editedProject.employeeList.add(lastShownEmployeeList.get(employeeIndex.getZeroBased()));
        }

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);

        return new CommandResult(generateSuccessMessage(editedProject));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code projectToEdit}.
     */
    private String generateSuccessMessage(Project projectToEdit) {
        return String.format(MESSAGE_ADD_PROJECT_SUCCESS, projectToEdit);
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
        return projectIndex.equals(e.projectIndex)
                && employeeIndexes.equals(e.employeeIndexes);
    }
}
