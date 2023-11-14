package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Assigns an existing project to a number of existing employees in the TaskHub.
 */
public class AssignProjectCommand extends Command {

    public static final String COMMAND_WORD = "assignP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the project of the employees identified "
            + "by the index number used in the last employee listing. "
            + "\n"
            + "Parameters: PROJECT_INDEX, EMPLOYEE_INDEX (must be a positive integer) "
            + "pr/[PROJECT_INDEX] em/[EMPLOYEE_INDEX ...]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROJECT + "1 "
            + PREFIX_EMPLOYEE + "2 3 1";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Member(s) have been assigned!\n%1$s";

    private static final Logger logger = LogsCenter.getLogger(DeadlineProjectCommand.class);
    private final Index projectIndex;
    private final List<Index> employeeIndexes;

    /**
     * @param projectIndex index of the project in the filtered project list to update
     * @param employeeIndexes index of the employees in the filtered employee list to be added to the project
     */
    public AssignProjectCommand(Index projectIndex, List<Index> employeeIndexes) {
        requireAllNonNull(projectIndex, employeeIndexes);

        this.projectIndex = projectIndex;
        this.employeeIndexes = employeeIndexes;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Employee> lastShownEmployeeList = model.getFilteredEmployeeList();
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        projectIsInList(projectIndex.getZeroBased(), lastShownProjectList.size());

        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
        Project editedProject = new Project(projectToEdit);
        for (Index employeeIndex : employeeIndexes) {
            employeeIsInList(employeeIndex.getZeroBased(), lastShownEmployeeList.size());
            Employee employeeToAdd = lastShownEmployeeList.get(employeeIndex.getZeroBased());
            if (!editedProject.getEmployees().contains(employeeToAdd)) {
                editedProject.getEmployees().add(employeeToAdd);
            }
        }

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);

        return new CommandResult(generateSuccessMessage(editedProject));
    }

    private void employeeIsInList(int target, int size) throws CommandException {
        if (target >= size) {
            logger.warning("Invalid employee index: " + (target + 1));
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }
    }

    private void projectIsInList(int target, int size) throws CommandException {
        if (target >= size) {
            logger.warning("Invalid project index: " + (target + 1));
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
    }
    /**
     * Generates a command execution success message based on whether
     * the employees are added to a project.
     * {@code projectToEdit}.
     */
    private String generateSuccessMessage(Project projectToEdit) {
        return String.format(MESSAGE_ADD_PROJECT_SUCCESS, Messages.format(projectToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignProjectCommand)) {
            return false;
        }

        AssignProjectCommand e = (AssignProjectCommand) other;
        return projectIndex.equals(e.projectIndex)
                && employeeIndexes.equals(e.employeeIndexes);
    }
}
