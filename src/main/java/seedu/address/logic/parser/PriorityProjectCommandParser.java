package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectPriority;

/**
 * Parses input arguments and creates a new PriorityProjectCommand object.
 */
public class PriorityProjectCommandParser implements Parser<PriorityProjectCommand> {

    /**
     * Parses the given String of argumetns in the context of the PriorityProjectCommand and returns
     * a PriorityProjectCommand for execution.
     *
     * @throws ParseException if the user does not conform to the expected format.
     */
    public PriorityProjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY, PREFIX_PROJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRIORITY, PREFIX_PROJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityProjectCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRIORITY);
        ProjectPriority priority = ParserUtil.parseProjectPriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT);
        Index projectIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());

        return new PriorityProjectCommand(priority, projectIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
