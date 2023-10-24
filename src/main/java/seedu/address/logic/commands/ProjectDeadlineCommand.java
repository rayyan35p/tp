package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

/**
 * Adds a deadline to an existing project in TaskHub.
 */
public class ProjectDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "dl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deadline of a project identified "
            + "by the index number used in the last project listing. "
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "[DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "21/02/2021";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to project: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline from project: %1$s";

    private final Index index;
    private final Deadline deadline;

    /**
     * @param index of the project in the filtered project list to edit the remark
     * @param deadline of the person to be updated to
     */
    public ProjectDeadlineCommand(Index index, Deadline deadline) {
        requireAllNonNull(index, deadline);

        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        Project editedProject = new Project(projectToEdit.getNameString(), projectToEdit.getEmployees(),
                deadline);

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

        return new CommandResult(generateSuccessMessage(editedProject));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Project projectToEdit) {
        String message = !deadline.value.isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, projectToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectDeadlineCommand)) {
            return false;
        }

        // state check
        ProjectDeadlineCommand e = (ProjectDeadlineCommand) other;
        return index.equals(e.index)
                && deadline.equals(e.deadline);
    }
}
