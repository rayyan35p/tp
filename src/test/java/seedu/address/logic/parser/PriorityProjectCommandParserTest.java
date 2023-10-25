package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.model.project.ProjectPriority;

public class PriorityProjectCommandParserTest {
    private PriorityProjectCommandParser parser = new PriorityProjectCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        ProjectPriority priority = new ProjectPriority("high");
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = PROJECT_PRIORITY_AMY + " " + PREFIX_PROJECT + INDEX_FIRST_PROJECT.getOneBased();
        assertParseSuccess(parser, userInput,
                new PriorityProjectCommand(priority, targetIndex));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityProjectCommand.MESSAGE_USAGE);

        // missing priority prefix
        assertParseFailure(parser, VALID_PRIORITY_AMY + " "
                + PREFIX_PROJECT + INDEX_FIRST_PROJECT.getOneBased(), expectedMessage);

        // missing project prefix
        assertParseFailure(parser, PROJECT_PRIORITY_AMY + " "
                + INDEX_FIRST_PROJECT.getOneBased(), expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PRIORITY_AMY + " "
                + INDEX_FIRST_PROJECT.getOneBased(), expectedMessage);
    }
}
