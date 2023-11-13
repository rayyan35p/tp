package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkTaskCommand;


public class UnmarkTaskCommandParserTest {

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
    1. Strings following the format: " pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]"
    where all INDEXES are positive integers
        e.g. " pr/1 t/1 2 3"

    2. Strings following the format: " pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]"
    where the PROJECT_INDEX is a positive integer, but not TASK_INDEXES
        e.g. " pr/1 t/0 3 5"

    3. Strings following the format: " pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]"
    where the PROJECT_INDEX is not a positive integer
        e.g. " pr/0 t/1 3 5"

    4. Strings not following the format: " pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]"
        e.g. "somerandomtextstring"
    */

    private UnmarkTaskCommandParser parser = new UnmarkTaskCommandParser();

    // EP used: EP 1
    @Test
    public void parse_validProjectAndTaskIndexes_success() {
        Index projectIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.add(Index.fromOneBased(2));
        UnmarkTaskCommand expectedCommand = new UnmarkTaskCommand(projectIndex, taskIndexes);

        String userInput = " pr/1 t/1 2";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // EP used: EP 2
    @Test
    public void parse_validProjectAndInvalidTaskIndexes_failure() {
        String userInput = " pr/1 t/0 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    // EP used: EP 3
    @Test
    public void parse_invalidProjectIndex_failure() {
        String userInput = " pr/0 t/1 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    // EP used: EP 4
    @Test
    public void parse_invalidInputFormat_failure() {
        String userInput = "somerandomtext$#";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkTaskCommand.MESSAGE_USAGE));
    }
}
