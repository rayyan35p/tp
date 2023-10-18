package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AssignEmployeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class AssignEmployeeCommandParser implements Parser<AssignEmployeeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AssignEmployeeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROJECT,PREFIX_EMPLOYEE);

        Index index;
        List<Index> employeeIndexes = new ArrayList<>();
        try {
            if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT, PREFIX_EMPLOYEE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEmployeeCommand.MESSAGE_USAGE));
            }
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());
            for (String employeeIndex : argMultimap.getValue(PREFIX_EMPLOYEE).get().split(" ")) {
                employeeIndexes.add(ParserUtil.parseIndex(employeeIndex));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignEmployeeCommand.MESSAGE_USAGE), ive);
        }

        String project = argMultimap.getValue(PREFIX_PROJECT).orElse("");

        return new AssignEmployeeCommand(index, employeeIndexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
