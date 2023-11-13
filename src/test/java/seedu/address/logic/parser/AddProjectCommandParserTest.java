package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectBuilder;

public class AddProjectCommandParserTest {

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
        1. Strings following the format: " n/PROJECT_NAME [em/EMPLOYEE_INDEXES]"
        where PROJECT_NAME is valid and all INDEXES are positive integers e.g. "n/Alpha em/1 2 3"

        2. Strings following the format: " n/PROJECT_NAME [em/EMPLOYEE_INDEXES]"
        where the PROJECT_NAME is valid, but not EMPLOYEE_INDEXES e.g. "n/Alpha em/0 3 5"

        3. Strings following the format: " n/PROJECT_NAME [em/EMPLOYEE_INDEXES]"
        with no EMPLOYEE_INDEXES e.g. "n/Alpha"

        4. Strings following the format: " n/PROJECT_NAME em/[EMPLOYEE_INDEXES]"
        where EMPLOYEE_INDEXES are valid but PROJECT_NAME is not e.g. "n/Alpha! em/1 2"

        5. Strings not following the format: " n/PROJECT_NAME em/[EMPLOYEE_INDEXES]" e.g. "somerandomtextstring"
    */
    private AddProjectCommandParser parser = new AddProjectCommandParser();

    //EP: 1
    @Test
    public void parse_allFieldsPresent_success() {
        Project project = new ProjectBuilder().withName(VALID_PROJECT_AMY).withEmployees().build();
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = PROJECT_DESC_AMY + " " + PREFIX_EMPLOYEE + INDEX_FIRST_EMPLOYEE.getOneBased();
        assertParseSuccess(parser, userInput,
                new AddProjectCommand(project,
                        new ArrayList<>(Arrays.asList(targetIndex))));
    }

    //EP: 2
    @Test
    public void parse_invalidEmployeeIndex_failure() {
        String input = " n/Alpha em/0 2";
        assertParseFailure(parser, input, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    //EP: 3
    @Test
    public void parse_nameFieldPresentOnly_success() {
        Project expectedProject = new ProjectBuilder().withName(VALID_PROJECT_AMY).withEmployees().build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_DESC_AMY,
                           new AddProjectCommand(expectedProject, new ArrayList<>()));
    }

    //EP: 4
    // " n/Alpha! em/1 2"
    @Test
    public void parse_invalidProjectName_failure() {
        String input = " " + PREFIX_NAME
                       + "ALPHA!" + " "
                       + PREFIX_EMPLOYEE + INDEX_FIRST_EMPLOYEE.toString()
                       + " " + INDEX_SECOND_EMPLOYEE.toString();
        assertParseFailure(parser, input, seedu.address.model.project.Name.MESSAGE_CONSTRAINTS);
    }

    //EP: 5
    @Test
    public void parse_noPrefixes_failure() {
        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE));
    }
}
