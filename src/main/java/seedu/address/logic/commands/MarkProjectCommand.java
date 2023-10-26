package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Project;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class MarkProjectCommand extends Command {

    public static final String COMMAND_WORD = "markP";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a project as completed in TaskHub.\n"
            + "Parameters: INDEXES (must be one or more positive integers, separated by spaces)\n"
            + "Example: " + COMMAND_WORD + " 1 2 4";

    public static final String MESSAGE_PROJECTS_MARKED_SUCCESSFULLY = "%1$d projects marked as completed.";

    private final List<Index> projectIndexes;

    public MarkProjectCommand(List<Index> projectIndexes) {
        requireAllNonNull(projectIndexes);

        this.projectIndexes = projectIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        // Check if all indexes are valid
        for (Index projectIndex : projectIndexes) {
            if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
        }

        // Mark all projects as completed
        for (Index projectIndex : projectIndexes) {
            Project projectToMark = lastShownProjectList.get(projectIndex.getZeroBased());
            Project markedProject = new Project(projectToMark.name, projectToMark.employeeList,
                    projectToMark.getProjectPriority(), projectToMark.deadline, new CompletionStatus(true));
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
