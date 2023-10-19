package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignEmployeeCommand;

public class AssignEmployeeCommandParserTest {
    private AssignEmployeeCommandParser parser = new AssignEmployeeCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = " " + PREFIX_PROJECT + targetIndex.getOneBased() + " "
                            + PREFIX_EMPLOYEE + targetIndex.getOneBased();
        AssignEmployeeCommand command = new AssignEmployeeCommand(targetIndex,
                                        new ArrayList<>(Arrays.asList(targetIndex)));
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEmployeeCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AssignEmployeeCommand.COMMAND_WORD, expectedMessage);

        // no employee index
        assertParseFailure(parser, AssignEmployeeCommand.COMMAND_WORD + " " + PREFIX_PROJECT
                                        + INDEX_FIRST_EMPLOYEE.toString(), expectedMessage);
    }
}
