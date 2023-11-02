package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Deadline;

/**
 * Parses input arguments and creates a new DeadlineProjectCommand object
 */
public class DeadlineProjectCommandParser implements Parser<DeadlineProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeadlineProjectCommand
     * and returns an DeadlineProjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeadlineProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeadlineProjectCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DEADLINE);
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());;

        List<Index> projectIndexes;
        projectIndexes = ParserUtil.parseIndexes(argMultimap.getPreamble());

        return new DeadlineProjectCommand(projectIndexes, deadline);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
