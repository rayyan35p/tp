package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;

/**
 * Sets a priority for a project in TaskHub.
 */
public class PriorityProjectCommand extends Command {
    public static final String COMMAND_WORD = "priorityP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the priority for the project(s) identified "
            + "by the index number used in the last project listing. "
            + "Existing priority will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDEXES] "
            + PREFIX_PRIORITY + "PRIORITY_LEVEL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "high";


    public static final String MESSAGE_SUCCESS = "Set priority level: %1$s to %2$s project(s)";

    private static final Logger logger = LogsCenter.getLogger(PriorityProjectCommand.class);

    private final Priority toSet;
    private final List<Index> projectIndexes;

    /**
     * Creates a PriorityProjectCommand to set the priority of the specified Project.
     */
    public PriorityProjectCommand(Priority priority, List<Index> projectIndexes) {
        requireNonNull(priority);
        requireNonNull(projectIndexes);
        this.toSet = priority;
        this.projectIndexes = projectIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        // Check if all indexes are valid first
        for (Index projectIndex : projectIndexes) {
            if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
                logger.warning("Invalid project index: " + projectIndex.getOneBased());
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
        }

        // Set priority for each project
        for (Index projectIndex : projectIndexes) {
            Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());
            Project editedProject = createProjectWithNewPriority(projectToEdit, toSet);
            model.setProject(projectToEdit, editedProject);
        }

        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSet, projectIndexes.size()));
    }

    private static Project createProjectWithNewPriority(Project projectToSetPriority, Priority priority) {
        assert projectToSetPriority != null;
        return new Project(projectToSetPriority.getName(),
                projectToSetPriority.getEmployees(),
                projectToSetPriority.getTasks(), priority,
                projectToSetPriority.getDeadline(),
                projectToSetPriority.getCompletionStatus());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityProjectCommand)) {
            return false;
        }

        PriorityProjectCommand otherPriorityProjectCommand = (PriorityProjectCommand) other;
        return toSet.equals(otherPriorityProjectCommand.toSet)
                && projectIndexes.equals(otherPriorityProjectCommand.projectIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toSet", toSet)
                .add("targetIndex", projectIndexes)
                .toString();
    }
}
