package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
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
            + "[" + PREFIX_EMPLOYEE + "EMPLOYEE_INDEX] (optional)\n"
            + PREFIX_DEADLINE + "TASK_DEADLINE{dd-MM-yyyy HHmm}\n\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "name of task "
            + PREFIX_PROJECT + "1 "
            + PREFIX_EMPLOYEE + "1 "
            + PREFIX_DEADLINE + "11-11-2023 2359";

    private final Task task;
    private final Index projectIndex;
    private final Optional<Index> employeeIndex;

    /**
     * Constructs an AddTaskCommand.
     * @param task The task to be added.
     * @param projectIndex The index of the project that should contain the task.
     */
    public AddTaskCommand(Task task, Index projectIndex) {
        requireAllNonNull(task, projectIndex);
        this.task = task;
        this.projectIndex = projectIndex;
        this.employeeIndex = Optional.empty();
    }

    /**
     * Constructs an AddTaskCommand.
     * @param task The task to be added.
     * @param projectIndex The index of the project that should contain the task.
     * @param employeeIndex The index of the employee that the task should be assigned to
     */
    public AddTaskCommand(Task task, Index projectIndex, Index employeeIndex) {
        requireAllNonNull(task, projectIndex);
        this.task = task;
        this.projectIndex = projectIndex;
        this.employeeIndex = Optional.of(employeeIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // if an employee was specified, add him to the task
        // we can only do this here in the execute method where we have access to the model.
        if (this.employeeIndex.isPresent()) {
            List<Employee> lastShownEmployeeList = model.getFilteredEmployeeList();
            // throw exceptions if employee list empty or invalid index
            if (lastShownEmployeeList.size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_EMPLOYEE_TO_ASSIGN);
            } else if (this.employeeIndex.get().getZeroBased() >= lastShownEmployeeList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
            }
            Employee assignee = model.getFilteredEmployeeList().get(employeeIndex.get().getZeroBased());
            this.task.setEmployee(assignee);
        }

        List<Project> lastShownProjectList = model.getFilteredProjectList();
        if (lastShownProjectList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_PROJECT_TO_ADD_TASK);
        } else if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());

        // Add a task to the project with the selected index.
        projectToEdit.addTask(this.task);

        Project editedProject = new Project(projectToEdit);

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
