package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignEmployeeCommand;
import seedu.address.model.employee.Project;

public class ProjectCommandParserTest {
    //Todo: redesign AssignEmployeeCommandParser
    private AssignEmployeeCommandParser parser = new AssignEmployeeCommandParser();
    private final String nonEmptyProject = "Some project.";

    @Test
    public void parse_indexSpecified_success() {
        // have project
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PROJECT + nonEmptyProject;
        AssignEmployeeCommand expectedCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                                                                            new Project(nonEmptyProject));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no project
        userInput = targetIndex.getOneBased() + " " + PREFIX_PROJECT;
        expectedCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE, new Project(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEmployeeCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AssignEmployeeCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AssignEmployeeCommand.COMMAND_WORD + " " + nonEmptyProject, expectedMessage);
    }
}
