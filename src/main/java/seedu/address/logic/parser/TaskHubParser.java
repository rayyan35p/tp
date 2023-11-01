package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AssignProjectCommand;
import seedu.address.logic.commands.AssignTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditEmployeeCommand;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListEmployeeAndProjectCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.commands.MarkProjectCommand;
import seedu.address.logic.commands.MarkTaskCommand;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.logic.commands.ProjectDeadlineCommand;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.commands.UnassignProjectCommand;
import seedu.address.logic.commands.UnassignTaskCommand;
import seedu.address.logic.commands.UnmarkProjectCommand;
import seedu.address.logic.commands.UnmarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class TaskHubParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(TaskHubParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddEmployeeCommand.COMMAND_WORD:
            return new AddEmployeeCommandParser().parse(arguments);

        case AddProjectCommand.COMMAND_WORD:
            return new AddProjectCommandParser().parse(arguments);

        case EditEmployeeCommand.COMMAND_WORD:
            return new EditEmployeeCommandParser().parse(arguments);

        case EditProjectCommand.COMMAND_WORD:
            return new EditProjectCommandParser().parse(arguments);

        case AssignProjectCommand.COMMAND_WORD:
            return new AssignProjectCommandParser().parse(arguments);

        case UnassignProjectCommand.COMMAND_WORD:
            return new UnassignProjectCommandParser().parse(arguments);

        case DeleteEmployeeCommand.COMMAND_WORD:
            return new DeleteEmployeeCommandParser().parse(arguments);

        case DeleteProjectCommand.COMMAND_WORD:
            return new DeleteProjectCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindEmployeeCommand.COMMAND_WORD:
            return new FindEmployeeCommandParser().parse(arguments);

        case FindProjectCommand.COMMAND_WORD:
            return new FindProjectCommandParser().parse(arguments);

        case ListEmployeeCommand.COMMAND_WORD:
            return new ListEmployeeCommand();

        case ListProjectCommand.COMMAND_WORD:
            return new ListProjectCommand();

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case AssignTaskCommand.COMMAND_WORD:
            return new AssignTaskCommandParser().parse(arguments);

        case UnassignTaskCommand.COMMAND_WORD:
            return new UnassignTaskCommandParser().parse(arguments);

        case ListEmployeeAndProjectCommand.COMMAND_WORD:
            return new ListEmployeeAndProjectCommand();

        case PriorityProjectCommand.COMMAND_WORD:
            return new PriorityProjectCommandParser().parse(arguments);

        case ProjectDeadlineCommand.COMMAND_WORD:
            return new ProjectDeadlineCommandParser().parse(arguments);

        case MarkProjectCommand.COMMAND_WORD:
            return new MarkProjectCommandParser().parse(arguments);

        case UnmarkProjectCommand.COMMAND_WORD:
            return new UnmarkProjectCommandParser().parse(arguments);

        case MarkTaskCommand.COMMAND_WORD:
            return new MarkTaskCommandParser().parse(arguments);

        case UnmarkTaskCommand.COMMAND_WORD:
            return new UnmarkTaskCommandParser().parse(arguments);

        case SortTaskCommand.COMMAND_WORD:
            return new SortTaskCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
