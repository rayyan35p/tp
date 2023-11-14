package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkProjectCommand;
import seedu.address.logic.commands.UnmarkProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkProjectCommand object
 */
public class UnmarkProjectCommandParser implements Parser<UnmarkProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkProjectCommand
     * and returns an UnmarkProjectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnmarkProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert args != null;

        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkProjectCommand.MESSAGE_USAGE));
        }

        List<Index> indexes = ParserUtil.parseIndexes(args);
        assert indexes.size() > 0;

        return new UnmarkProjectCommand(indexes);
    }
}
