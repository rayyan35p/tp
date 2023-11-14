package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteProjectCommand object
 */
public class DeleteProjectCommandParser implements Parser<DeleteProjectCommand> {
    @Override
    public DeleteProjectCommand parse(String userInput) throws ParseException {

        try {
            Integer.parseInt(userInput.replaceFirst("deleteP ", "").trim());
        } catch (NumberFormatException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectCommand.MESSAGE_USAGE), pe);
        }
        Index index = ParserUtil.parseIndex(userInput);
        return new DeleteProjectCommand(index);

    }
}
