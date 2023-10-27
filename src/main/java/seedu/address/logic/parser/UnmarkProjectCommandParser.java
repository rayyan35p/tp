package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkProjectCommand object
 */
public class UnmarkProjectCommandParser implements Parser<UnmarkProjectCommand> {

    @Override
    public UnmarkProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert args != null;

        try {
            List<Index> indexes = ParserUtil.parseIndexes(args);
            assert indexes.size() > 0;

            return new UnmarkProjectCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkProjectCommand.MESSAGE_USAGE), pe);
        }
    }
}
