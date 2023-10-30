package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.TaskList;



/**
 * Deletes a task from an existing project in TaskHub.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deleteT";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes (a) task(s) from a project in TaskHub.\n"
            + "Parameters: " + PREFIX_PROJECT + "PROJECT_INDEX " + PREFIX_TASK + "TASK_INDEXES\n"
            + "PROJECT_INDEX must be one positive integer and "
            + "TASK_INDEXES must be one or more positive integers, separated by a space between each INDEX\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROJECT + "2 " + PREFIX_TASK + "1 2 4";

    public static final String MESSAGE_TASKS_DELETED_SUCCESSFULLY =
            "%d task(s) deleted from the project: %s";
    private static final Logger logger = LogsCenter.getLogger(DeleteTaskCommand.class);
    private final Index projectIndex;
    private String projectName;
    private final List<Index> taskIndexes;

    /**
     * Creates a DeleteTaskCommand to delete the specified tasks from the specified project.
     * @param projectIndex
     * @param taskIndexes
     */
    public DeleteTaskCommand(Index projectIndex, List<Index> taskIndexes) {
        requireAllNonNull(taskIndexes);
        assert taskIndexes.size() > 0;
        this.projectIndex = projectIndex;
        this.taskIndexes = taskIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Project> lastShownProjectList = model.getFilteredProjectList();

        if (lastShownProjectList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_PROJECT_TO_DELETE_TASK);
        } else if (projectIndex.getZeroBased() >= lastShownProjectList.size()
            // Check if project index is valid first
            || projectIndex.getZeroBased() < 0) {
            logger.warning("Invalid project index: " + projectIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project targetProject = lastShownProjectList.get(projectIndex.getZeroBased());
        TaskList lastShownTaskList = targetProject.getTasks();

        // Delete all tasks from targetProject
        for (Index taskIndex : taskIndexes) {
            // Check if task index is valid first
            if (taskIndex.getZeroBased() >= lastShownTaskList.getSize()
                    || taskIndex.getZeroBased() < 0) {
                logger.warning("Invalid task index: " + taskIndex.getOneBased());
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            lastShownTaskList.remove(taskIndex);
        }

        // Create new project with updated tasks
        Project updatedProject = new Project(targetProject.getNameString(), targetProject.getEmployees(),
                lastShownTaskList, targetProject.getProjectPriority(),
                targetProject.getDeadline(), targetProject.getCompletionStatus());

        projectName = updatedProject.getNameString();

        model.setProject(targetProject, updatedProject);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message based on whether all the tasks were deleted.
     */
    private String generateSuccessMessage() {
        return String.format(MESSAGE_TASKS_DELETED_SUCCESSFULLY, taskIndexes.size(), projectName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherCommand = (DeleteTaskCommand) other;
        return projectIndex.equals(otherCommand.projectIndex) && taskIndexes.equals(otherCommand.taskIndexes);
    }
}
