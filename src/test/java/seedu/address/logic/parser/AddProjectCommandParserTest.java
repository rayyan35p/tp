package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.model.employee.Project;
import seedu.address.testutil.ProjectBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

public class AddProjectCommandParserTest {
    private AddProjectCommandParser parser = new AddProjectCommandParser();

    @Test
    public void parse_NameFieldPresentOnly_success() {
        Project expectedProject = new ProjectBuilder().withName(VALID_PROJECT_AMY).withEmployees().build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_DESC_AMY,
                           new AddProjectCommand(expectedProject, new ArrayList<>()));
    }

    @Test
    public void parse_AllFieldsPresent_success() {
        Project project = new ProjectBuilder().withName(VALID_PROJECT_AMY).withEmployees().build();
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = PROJECT_DESC_AMY+ " em/" + INDEX_FIRST_EMPLOYEE.getOneBased();
        assertParseSuccess(parser,userInput,
                            new AddProjectCommand(project,
                                    new ArrayList<>(Arrays.asList(targetIndex))));
    }
}
