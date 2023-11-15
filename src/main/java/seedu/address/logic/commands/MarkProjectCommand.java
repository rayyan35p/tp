package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Project;

/**
 * Marks a project as completed in TaskHub.
 */
public class MarkProjectCommand extends Command {

    public static final String COMMAND_WORD = "markP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a project as completed in TaskHub.\n"
            + "Parameters: INDEXES (must be one or more positive integers, separated by a space between each INDEX)\n"
            + "Example: " + COMMAND_WORD + " 1 2 4";

    public static final String MESSAGE_PROJECTS_MARKED_SUCCESSFULLY = "%1$d project(s) marked as completed.";

    private static final Logger logger = LogsCenter.getLogger(MarkProjectCommand.class);

    private final List<Index> projectIndexes;

    /**
     * Creates a MarkProjectCommand to mark the specified projects as completed.
     *
     * @param projectIndexes which are the indexes of the projects to mark as completed.
     */
    public MarkProjectCommand(List<Index> projectIndexes) {
        requireAllNonNull(projectIndexes);
        assert projectIndexes.size() > 0;

        this.projectIndexes = projectIndexes;
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

        // Mark all projects as completed
        for (Index projectIndex : projectIndexes) {
            assert projectIndex.getZeroBased() >= 0;
            assert projectIndex.getZeroBased() < lastShownProjectList.size();

            Project projectToMark = lastShownProjectList.get(projectIndex.getZeroBased());
            Project markedProject = new Project(projectToMark.getName(), projectToMark.getEmployees(),
                    projectToMark.getTasks(),
                    projectToMark.getPriority(), projectToMark.getDeadline(), new CompletionStatus(true));
            model.setProject(projectToMark, markedProject);
        }

        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message based on whether all the projects were marked as completed.
     */
    private String generateSuccessMessage() {
        return String.format(MESSAGE_PROJECTS_MARKED_SUCCESSFULLY, projectIndexes.size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkProjectCommand)) {
            return false;
        }

        MarkProjectCommand otherCommand = (MarkProjectCommand) other;
        return projectIndexes.equals(otherCommand.projectIndexes);
    }
}
