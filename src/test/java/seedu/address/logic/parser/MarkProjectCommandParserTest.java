package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkProjectCommand;

public class MarkProjectCommandParserTest {

    private MarkProjectCommandParser parser = new MarkProjectCommandParser();

    /*
    Test case design used: Equivalence Partition

    EPs for userInput:
    1. Strings following the format: "INDEX [MORE_INDEXES]"
    where there are multiple positive integers
        e.g. "1 2"

    2. Strings following the format: "INDEX [MORE_INDEXES]"
    where there is a non-integer
        e.g. "a"
    */

    // EP used: EP 1
    @Test
    public void parse_validIndexes_success() {
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        indexes.add(Index.fromOneBased(2));
        String userInput = "1 2";
        MarkProjectCommand expectedCommand = new MarkProjectCommand(indexes);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // EP used: EP 2
    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "a";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_INDEX, userInput));
    }
}
