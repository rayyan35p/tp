package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Project;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddProjectCommandParser implements Parser<AddProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProjectCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_EMPLOYEE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT);
        Project project = ParserUtil.parseProject(argMultimap.getValue(PREFIX_PROJECT).get());
        List<Index> employeeIndexes = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_EMPLOYEE).isPresent()) {
            for (String index : argMultimap.getValue(PREFIX_EMPLOYEE).get().split(" ")) {
                employeeIndexes.add(ParserUtil.parseIndex(index));
            }
        }

        return new AddProjectCommand(project, employeeIndexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

