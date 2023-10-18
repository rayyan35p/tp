package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AssignEmployeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Project;

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
                PREFIX_PROJECT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignEmployeeCommand.MESSAGE_USAGE), ive);
        }

        String project = argMultimap.getValue(PREFIX_PROJECT).orElse("");

        return new AssignEmployeeCommand(index, new Project(project));
    }
}
