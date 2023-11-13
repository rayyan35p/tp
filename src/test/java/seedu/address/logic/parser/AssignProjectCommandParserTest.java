package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignProjectCommand;

public class AssignProjectCommandParserTest {

    /*
        Test case design used: Equivalence Partition

        EPs for userInput:
        1. Strings following the format: " pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]"
            where both PROJECT_INDEX and EMPLOYEE_INDEX are positive integers e.g." pr/1 em/1 2 3"

        2. Strings following the format :" pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]"
            where PROJECT_INDEX is a positive integer but EMPLOYEE_INDEX is not e.g. " pr/1 em/0 2 3"\

        3. Strings following the format :" pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]"
            where EMPLOYEE_INDEX is a positive integer but PROJECT_INDEX is not e.g. " pr/0 em/1 2 3"

        4. Strings not following the format :" pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES]"
            e.g. "aString"

    */
    private AssignProjectCommandParser parser = new AssignProjectCommandParser();

    //EP: 1
    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = " " + PREFIX_PROJECT + targetIndex.getOneBased() + " "
                            + PREFIX_EMPLOYEE + targetIndex.getOneBased();
        AssignProjectCommand command = new AssignProjectCommand(targetIndex,
                                        new ArrayList<>(Arrays.asList(targetIndex)));
        assertParseSuccess(parser, userInput, command);
    }

    //EP: 2
    @Test
    public void parse_employeeIndexInvalid_failure() {
        String input = " " + PREFIX_PROJECT + INDEX_FIRST_PROJECT.getOneBased()
                       + " " + PREFIX_EMPLOYEE + "0" + " " + INDEX_SECOND_EMPLOYEE.getOneBased();
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    //EP: 3
    @Test
    public void parse_projectIndexInvalid_failure() {
        String input = " " + PREFIX_PROJECT + "0"
                + " " + PREFIX_EMPLOYEE + INDEX_FIRST_EMPLOYEE.getOneBased()
                + " " + INDEX_SECOND_EMPLOYEE.getOneBased();
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    //EP: 4
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AssignProjectCommand.COMMAND_WORD, expectedMessage);

        // no employee index
        assertParseFailure(parser, " " + PREFIX_PROJECT + INDEX_FIRST_EMPLOYEE.getOneBased(), expectedMessage);

        // no project index
        assertParseFailure(parser, " " + PREFIX_EMPLOYEE + INDEX_FIRST_EMPLOYEE.getOneBased(), expectedMessage);
    }
}
