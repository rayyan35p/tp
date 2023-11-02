package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

/**
 * Adds a deadline to an existing project in TaskHub.
 */
public class DeadlineProjectCommand extends Command {

    public static final String COMMAND_WORD = "dlP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deadline of the project(s) identified "
            + "by the index number used in the last project listing. "
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDEXES] "
            + PREFIX_DEADLINE + "DEADLINE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "21-02-2021";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline: %1$s to %2$s project(s)";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline for %1$s project(s)";

    private static final Logger logger = LogsCenter.getLogger(DeadlineProjectCommand.class);

    private final List<Index> projectIndexes;
    private final Deadline deadline;

    /**
     * @param projectIndexes of the project in the filtered project list to edit the deadline
     * @param deadline to be updated to
     */
    public DeadlineProjectCommand(List<Index> projectIndexes, Deadline deadline) {
        requireAllNonNull(projectIndexes, deadline);

        this.projectIndexes = projectIndexes;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        // Check if all indexes are valid first
        for (Index projectIndex : projectIndexes) {
            if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
                logger.warning("Invalid project index: " + projectIndex.getOneBased());
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
        }

        // Update the deadline for each project
        for (Index projectIndex : projectIndexes) {
            Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
            Project editedProject = new Project(projectToEdit.getName(), projectToEdit.getEmployees(),
                    projectToEdit.getTasks(),
                    projectToEdit.getPriority(), deadline, projectToEdit.getCompletionStatus());
            model.setProject(projectToEdit, editedProject);
        }

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message based on whether the deadline is added to or removed from
     * {@code projectToEdit}.
     */
    private String generateSuccessMessage() {
        String message = !deadline.value.isEmpty()
                ? String.format(MESSAGE_ADD_DEADLINE_SUCCESS, this.deadline.toString(), projectIndexes.size())
                : String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, projectIndexes.size());
        return message;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeadlineProjectCommand)) {
            return false;
        }

        // state check
        DeadlineProjectCommand e = (DeadlineProjectCommand) other;
        return projectIndexes.equals(e.projectIndexes)
                && deadline.equals(e.deadline);
    }
}
