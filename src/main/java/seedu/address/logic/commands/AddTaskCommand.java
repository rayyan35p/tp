package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.task.Task;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Changes the remark of an existing person in the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addT";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Deadline: %2$s";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    private final Task task;
    public AddTaskCommand(Task task) {
        this.task= task;
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TaskHub.\n"
            + "Parameters: "
            + PREFIX_NAME + "TASK_NAME\n"
            + PREFIX_DEADLINE + "TASK_DEADLINE{yyyy-MM-dd HHmm}\n\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "Implement addT "
            + PREFIX_DEADLINE + "2023-11-31 2359";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // TODO modifying employee ? do we need to do this? employees should know all the tasks that they have?
//        List<Employee> lastShownList = model.getFilteredEmployeeList();
//        for (Index targetIndex : employeeIndexes) {
//            if (targetIndex.getZeroBased() >= lastShownList.size()) {
//                throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
//            }
//            //changes project of employee
//            Employee employeeToAdd = lastShownList.get(targetIndex.getZeroBased());
//            EditCommand.EditEmployeeDescriptor editEmployeeDescriptor = new EditCommand.EditEmployeeDescriptor();
//            editEmployeeDescriptor.setProject(toAdd);
//            editEmployeeDescriptor.setName(employeeToAdd.getName());
//            editEmployeeDescriptor.setAddress(employeeToAdd.getAddress());
//            editEmployeeDescriptor.setEmail(employeeToAdd.getEmail());
//            editEmployeeDescriptor.setPhone(employeeToAdd.getPhone());
//            editEmployeeDescriptor.setTags(employeeToAdd.getTags());
//            new EditCommand(targetIndex, editEmployeeDescriptor).execute(model);
//
//            //removes employee from previous project
//            toAdd.addEmployee(employeeToAdd);
//        }
          //TODO: we should allow duplicate tasks if multiple need to do the same thing in a project?
//        if (model.hasProject(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
//        }
        model.addTask(this.task);
//        return new CommandResult(String.format(MESSAGE_ARGUMENTS, task.getName(), task.getDeadline()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(this.task)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (other instanceof AddTaskCommand) {
            AddTaskCommand e = (AddTaskCommand) other;
            return this.task.equals(e.task);
        }
        return false;
    }
}