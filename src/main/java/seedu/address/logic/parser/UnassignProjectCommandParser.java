package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnassignProjectCommandParser implements Parser<UnassignProjectCommand> {

    public UnassignProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROJECT, PREFIX_EMPLOYEE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT, PREFIX_EMPLOYEE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                     UnassignProjectCommand.MESSAGE_USAGE));
        }

        Index projectIndex;
        List<Index> employeeIndexes;

        projectIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT).get());
        employeeIndexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_EMPLOYEE).get());

        return new UnassignProjectCommand(projectIndex, employeeIndexes);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
