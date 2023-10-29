package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

/**
 *  Adds a Task to a project existing in TaskHub upon execution.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addT";

    public static final String MESSAGE_SUCCESS = "New task added to project %1$s, %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TaskHub.\n"
            + "Parameters: "
            + PREFIX_NAME + "TASK_NAME\n"
            + PREFIX_PROJECT + "PROJECT_INDEX\n"
            + PREFIX_DEADLINE + "TASK_DEADLINE{yyyy-MM-dd HHmm}\n\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "name of task"
            + PREFIX_PROJECT + "1 "
            + PREFIX_DEADLINE + "2023-11-31 2359";

    private final Task task;
    private final Index projectIndex;

    /**
     * Constructs an AddTaskCommand.
     * @param task The task to be added.
     * @param projectIndex The index of the project that should contain the task.
     */
    public AddTaskCommand(Task task, Index projectIndex) {
        requireAllNonNull(task, projectIndex);
        this.task = task;
        this.projectIndex = projectIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO modifying employee ? do we need to do this? employees should know all the tasks that they have?
        List<Project> lastShownProjectList = model.getFilteredProjectList();
        if (lastShownProjectList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_PROJECT_TO_ADD_TASK);
        } else if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());

        // Add a task to the project with the selected index.
        projectToEdit.addTask(this.task);

        Project editedProject = new Project(projectToEdit.getNameString(),
                projectToEdit.getEmployees(), projectToEdit.getTasks(),
                projectToEdit.getProjectPriority(), projectToEdit.getDeadline(), projectToEdit.getCompletionStatus());

        // update model and filtered list for Ui update.
        model.setProject(projectToEdit, editedProject);
        model.addTask(this.task);

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, projectIndex.getOneBased(),
                                               Messages.format(this.task)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }
        AddTaskCommand e = (AddTaskCommand) other;
        return this.task.equals(e.task) && this.projectIndex.equals(e.projectIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", task)
                .toString();
    }
}
