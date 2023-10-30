package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EMPLOYEE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnassignProjectCommand;


public class UnassignProjectCommandParserTest {

    private UnassignProjectCommandParser parser = new UnassignProjectCommandParser();

    @Test
    public void parse_validArgs_success() {
        String args = " " + PREFIX_PROJECT + "1 " + PREFIX_EMPLOYEE + "1 3";
        UnassignProjectCommand expectedCommand = new UnassignProjectCommand(
                INDEX_FIRST_PROJECT,
                List.of(INDEX_FIRST_EMPLOYEE, INDEX_THIRD_EMPLOYEE));
        assertParseSuccess(parser, args, expectedCommand);
    }

    @Test
    public void parse_missingProjectPrefix_failure() {
        String args = " 1 " + PREFIX_EMPLOYEE + "2 3 4";
        assertParseFailure(parser, args, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingEmployeePrefix_failure() {
        String args = " " + PREFIX_PROJECT + "1 2 3 4";
        assertParseFailure(parser, args, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexes_failure() {
        String args = " " + PREFIX_PROJECT + "abc " + PREFIX_EMPLOYEE + "2 3 4";
        assertParseFailure(parser, args, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_noIndexes_failure() {
        String args = " " + PREFIX_PROJECT + "1 " + PREFIX_EMPLOYEE;
        assertParseFailure(parser, args, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_repeatedIndexes_failure() {
        String args = " " + PREFIX_PROJECT + "1 " + PREFIX_EMPLOYEE + "1 1";
        assertParseFailure(parser, args, String.format(ParserUtil.MESSAGE_DUPLICATE_INDEX, "1"));
    }
}
