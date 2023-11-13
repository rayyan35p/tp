package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteProjectCommand;

public class DeleteProjectCommandParserTest {

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
        1. Strings following the format: "deleteP INDEX"
            with a valid INDEX e.g. "deleteP 1"

        2. Strings following the format: "deleteP INDEX"
            but without a valid INDEX e.g. "deleteP 0"

        3. Strings not following the format: "deleteP INDEX" e.g. "aString"

     */
    private DeleteProjectCommandParser parser = new DeleteProjectCommandParser();

    //EP:1
    @Test
    public void parse_validIndex_returnsDeleteProjectCommand() {
        assertParseSuccess(parser, "1", new DeleteProjectCommand(INDEX_FIRST_EMPLOYEE));
    }

    //EP:2
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_INDEX, "0"));
    }

    //EP:3
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "aString",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectCommand.MESSAGE_USAGE));
    }
}
