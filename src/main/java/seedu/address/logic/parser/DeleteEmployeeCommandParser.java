package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEmployeeCommand object
 */
public class DeleteEmployeeCommandParser implements Parser<DeleteEmployeeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEmployeeCommand
     * and returns a DeleteEmployeeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEmployeeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEmployeeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEmployeeCommand.MESSAGE_USAGE), pe);
        }
    }

}
