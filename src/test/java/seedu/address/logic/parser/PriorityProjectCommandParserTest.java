package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.model.project.Priority;

public class PriorityProjectCommandParserTest {
    private PriorityProjectCommandParser parser = new PriorityProjectCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Index singleTargetIndex = INDEX_FIRST_PROJECT;
        List<Index> targetIndexList = List.of(INDEX_FIRST_PROJECT, INDEX_SECOND_PROJECT, INDEX_THIRD_PROJECT);

        // EP: low priority
        Priority lowPriority = new Priority("low");
        String lowPriorityUserInput = INDEX_FIRST_PROJECT.getOneBased() + " p/low";
        assertParseSuccess(parser, lowPriorityUserInput,
                new PriorityProjectCommand(lowPriority, List.of(singleTargetIndex)));

        // EP: normal priority
        Priority normalPriority = new Priority("normal");
        String normalPriorityUserInput = INDEX_FIRST_PROJECT.getOneBased() + " p/normal";
        assertParseSuccess(parser, normalPriorityUserInput,
                new PriorityProjectCommand(normalPriority, List.of(singleTargetIndex)));

        // EP: high priority
        Priority highPriority = new Priority("high");
        String highPriorityUserInput = INDEX_FIRST_PROJECT.getOneBased() + " p/high";
        assertParseSuccess(parser, highPriorityUserInput,
                new PriorityProjectCommand(highPriority, List.of(singleTargetIndex)));

        // EP: multiple indexes
        String highPriorityMultipleIndexUserInput =
                INDEX_FIRST_PROJECT.getOneBased() + " "
                        + INDEX_SECOND_PROJECT.getOneBased() + " "
                        + INDEX_THIRD_PROJECT.getOneBased() + " "
                        + " p/high";
        assertParseSuccess(parser, highPriorityMultipleIndexUserInput,
                new PriorityProjectCommand(highPriority, targetIndexList));
    }

    @Test
    public void parse_priorityFieldMissing_failure() {
        String expectedMessage = String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PriorityProjectCommand.MESSAGE_USAGE));

        // EP: missing priority prefix
        assertParseFailure(parser, INDEX_FIRST_PROJECT.getOneBased() + " "
                + VALID_PRIORITY_AMY + " ", expectedMessage);
    }
}
