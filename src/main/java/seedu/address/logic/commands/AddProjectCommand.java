package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

public class AddProjectCommand extends Command{
    public static final String COMMAND_WORD = "addP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the TaskHub. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Rebuild Taskhub";


    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the TaskHub";

    private final Project toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Project}
     */
    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

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
        if (!(other instanceof AddCommand)) {
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
