package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new MarkTaskCommand object
 */
public class MarkTaskCommandParser implements Parser<MarkTaskCommand> {

    @Override
    public MarkTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert args != null;

        // check for duplicates
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_TASK);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT, PREFIX_TASK);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT, PREFIX_TASK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
        }

        // get project and task indexes
        Index projectIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());
        List<Index> taskIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_TASK).get());
        assert taskIndexes.size() > 0;

        return new MarkTaskCommand(projectIndex, taskIndexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
