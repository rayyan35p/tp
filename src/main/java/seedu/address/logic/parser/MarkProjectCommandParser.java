package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkProjectCommand object
 */
public class MarkProjectCommandParser implements Parser<MarkProjectCommand> {

    @Override
    public MarkProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert args != null;

        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkProjectCommand.MESSAGE_USAGE));
        }

        List<Index> indexes = ParserUtil.parseIndexes(args);
        assert indexes.size() > 0;

        return new MarkProjectCommand(indexes);
    }
}
