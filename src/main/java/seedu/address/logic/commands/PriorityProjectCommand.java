package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * Sets a priority for a project in TaskHub.
 */
public class PriorityProjectCommand extends Command {
    public static final String COMMAND_WORD = "priorityP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a priority for a project in TaskHub."
            + "Parameters: "
            + PREFIX_PRIORITY + "PRIORITY_LEVEL"
            + PREFIX_PROJECT + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PRIORITY + "high "
            + PREFIX_PROJECT + "1";


    public static final String MESSAGE_SUCCESS = "Set priority level: %1$s";

    private final ProjectPriority toSet;
    private final Index targetIndex;

    /**
     * Creates a PriorityProjectCommand to set the priority of the specified Project.
     */
    public PriorityProjectCommand(ProjectPriority priority, Index targetIndex) {
        requireNonNull(priority);
        requireNonNull(targetIndex);
        this.toSet = priority;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToSetPriority = lastShownList.get(targetIndex.getZeroBased());
        Project projectWithNewPriority = createProjectWithNewPriority(projectToSetPriority, toSet);
        model.setProject(projectToSetPriority, projectWithNewPriority);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(projectWithNewPriority)));
    }

    private static Project createProjectWithNewPriority(Project projectToSetPriority, ProjectPriority priority) {
        assert projectToSetPriority != null;
        return new Project(projectToSetPriority.name, projectToSetPriority.employeeList, priority,
                projectToSetPriority.deadline, projectToSetPriority.getCompletionStatus());
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
                && targetIndex.equals(otherPriorityProjectCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toSet", toSet)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
