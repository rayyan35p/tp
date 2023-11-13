package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Deletes a project using its identified index from the TaskHub.
 */
public class DeleteProjectCommand extends Command {

    public static final String COMMAND_WORD = "deleteP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";
    private static final Logger logger = LogsCenter.getLogger(DeadlineProjectCommand.class);

    private final Index targetIndex;
    public DeleteProjectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid project index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
        Project projectToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteProject(projectToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, Messages.format(projectToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProjectCommand)) {
            return false;
        }

        DeleteProjectCommand otherDeleteProjectCommand = (DeleteProjectCommand) other;
        return targetIndex.equals(otherDeleteProjectCommand.targetIndex);
    }
}
