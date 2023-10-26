package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class MarkProjectCommandParser implements Parser<MarkProjectCommand> {

    @Override
    public MarkProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            List<Index> indexes = ParserUtil.parseIndexes(args);
            return new MarkProjectCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkProjectCommand.MESSAGE_USAGE), pe);
        }
    }
}
