package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;

public class FindEmployeeCommandParserTest {

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
    1. Strings that are empty or contain only whitespaces
    2. Strings containing any other character (i.e. complement of EP1)
    */

    private FindEmployeeCommandParser parser = new FindEmployeeCommandParser();

    // EP used: EP1
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEmployeeCommand.MESSAGE_USAGE));
    }

    // EP used: EP2
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEmployeeCommand expectedFindEmployeeCommand =
                new FindEmployeeCommand(new EmployeeNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindEmployeeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindEmployeeCommand);
    }

}
