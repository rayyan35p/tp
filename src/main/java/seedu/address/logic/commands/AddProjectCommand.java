package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Adds a project to the TaskHub.
 */
public class AddProjectCommand extends Command {
    public static final String COMMAND_WORD = "addP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the TaskHub. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Rebuild Taskhub";


    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the TaskHub";

    private final Project toAdd;
    private final List<Index> employeeIndexes;

    /**
     * Creates an AddProjectCommand to add the specified {@code Project}
     */
    public AddProjectCommand(Project project, List<Index> employeeIndexes) {
        requireNonNull(project);
        toAdd = project;
        this.employeeIndexes = employeeIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();
        for (Index targetIndex : employeeIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
            }
            Employee employeeToAdd = lastShownList.get(targetIndex.getZeroBased());
            toAdd.addEmployee(employeeToAdd);
        }

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProjectCommand)) {
            return false;
        }

        AddProjectCommand otherAddProjectCommand = (AddProjectCommand) other;
        return toAdd.equals(otherAddProjectCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
