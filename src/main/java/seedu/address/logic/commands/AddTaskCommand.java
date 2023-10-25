package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.task.Task;

import java.time.LocalDateTime;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Changes the remark of an existing person in the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addT";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Deadline: %2$s";
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
        return new CommandResult(String.format(MESSAGE_ARGUMENTS, task.getName(), task.getDeadline()));
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