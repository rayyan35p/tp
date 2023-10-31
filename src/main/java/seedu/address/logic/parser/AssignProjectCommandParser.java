package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AssignProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignProjectCommand object
 */
public class AssignProjectCommandParser implements Parser<AssignProjectCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AssignProjectCommand}
     * and returns a {@code AssignProjectCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AssignProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROJECT, PREFIX_EMPLOYEE);

        Index index;
        List<Index> employeeIndexes;
        try {
            if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT, PREFIX_EMPLOYEE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                         AssignProjectCommand.MESSAGE_USAGE));
            }
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());
            employeeIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_EMPLOYEE).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignProjectCommand.MESSAGE_USAGE), ive);
        }

        return new AssignProjectCommand(index, employeeIndexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
