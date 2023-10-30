package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignTaskCommand object.
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {
    @Override
    public AssignTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_TASK, PREFIX_EMPLOYEE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT, PREFIX_TASK, PREFIX_EMPLOYEE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignTaskCommand.MESSAGE_USAGE));
        }

        Index projectIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());
        Index taskIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK).get());
        Index employeeIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMPLOYEE).get());

        return new AssignTaskCommand(projectIndex, taskIndex, employeeIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
