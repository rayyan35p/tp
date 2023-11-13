package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

public class FindProjectCommandParserTest {

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
    1. Strings that are empty or contain only whitespaces
    2. Strings containing any other character (i.e. complement of EP1)
    */

    private FindProjectCommandParser parser = new FindProjectCommandParser();

    // EP used: EP1
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProjectCommand.MESSAGE_USAGE));
    }

    // EP used: EP2
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindProjectCommand expectedFindProjectCommand =
                new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(Arrays.asList("Create", "Website")));
        assertParseSuccess(parser, "Create Website", expectedFindProjectCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Create \n \t Website  \t", expectedFindProjectCommand);
    }

}
