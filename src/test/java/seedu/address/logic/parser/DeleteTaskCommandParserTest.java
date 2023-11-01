package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;



public class DeleteTaskCommandParserTest {

    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validProjectAndTaskIndexes_success() throws ParseException {
        Index projectIndex = Index.fromOneBased(2);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(2));
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.sort((a, b) -> b.getZeroBased() - a.getZeroBased());
        DeleteTaskCommand expectedCommand = new DeleteTaskCommand(projectIndex, taskIndexes);
        String userInput = " pr/2 t/1 2";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validProjectAndInvalidTaskIndexes_failure() {
        String userInput = " pr/1 t/0 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    @Test
    public void parse_invalidProjectAndValidTaskIndexes_failure() {
        String userInput = " pr/0 t/1 2";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    @Test
    public void parse_invalidInputFormat_failure() {
        String userInput = " somerandomtext$#";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTaskCommand.MESSAGE_USAGE));
    }
}
