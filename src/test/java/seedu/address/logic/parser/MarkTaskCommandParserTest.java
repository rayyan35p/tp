package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkTaskCommand;


public class MarkTaskCommandParserTest {

    private MarkTaskCommandParser parser = new MarkTaskCommandParser();

    @Test
    public void parse_validProjectAndTaskIndexes_success() {
        Index projectIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.add(Index.fromOneBased(2));
        MarkTaskCommand expectedCommand = new MarkTaskCommand(projectIndex, taskIndexes);

        String userInput = " pr/1 t/1 2";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validProjectAndInvalidTaskIndexes_failure() {
        String userInput = " pr/1 t/0 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidProjectAndValidTaskIndexes_failure() {
        String userInput = " pr/0 t/1 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidInputFormat_failure() {
        String userInput = " somerandomtext$#";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkTaskCommand.MESSAGE_USAGE));
    }
}
