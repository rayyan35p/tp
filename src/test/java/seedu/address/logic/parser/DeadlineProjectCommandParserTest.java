package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineProjectCommand;
import seedu.address.model.project.Deadline;

public class DeadlineProjectCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineProjectCommand.MESSAGE_USAGE);

    private DeadlineProjectCommandParser parser = new DeadlineProjectCommandParser();
    private final String validDeadline = "21-02-2023";

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
    1. Strings following the format: "INDEX d/DEADLINE"
    where INDEX is a positive integer and DEADLINE is a valid date
        e.g. "1 d/21-02-2023"

    2. Strings following the format: "INDEX d/DEADLINE"
    where INDEX is a positive integer and DEADLINE is missing (indicating no deadline)
        e.g. "1 d/"

    3. Strings following the format: "INDEX d/DEADLINE"
    where INDEX is a positive integer and DEADLINE is an invalid date (february 29th does not exist)
        e.g. "1 d/29-02-2023"

    4. Strings following the format: "INDEX d/DEADLINE"
    where INDEX is a negative integer and DEADLINE is valid date
        e.g. "1 d/21/02/2023"
    */

    // EP used: EP 1
    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DEADLINE + validDeadline;
        DeadlineProjectCommand expectedCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(validDeadline));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // EP used: EP 2
    @Test
    public void parse_missingDeadlineDate_success() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "";
        DeadlineProjectCommand expectedCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDeadlinePrefix_failure() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + validDeadline;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDeadline_failure() {
        // Invalid date format
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "21/02/2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);

        // Invalid date value, EP used: EP 3
        userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "29-02-2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);
    }

    // EP used: EP 4
    @Test
    public void parse_invalidIndex_failure() {
        // Invalid index
        String userInput = "-1" + " " + PREFIX_DEADLINE + validDeadline;
        assertParseFailure(parser, userInput, String.format(ParserUtil.MESSAGE_INVALID_INDEX, "-1"));
    }
}
