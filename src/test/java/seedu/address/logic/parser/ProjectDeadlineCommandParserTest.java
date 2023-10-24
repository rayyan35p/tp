package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ProjectDeadlineCommand;
import seedu.address.model.project.Deadline;

public class ProjectDeadlineCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectDeadlineCommand.MESSAGE_USAGE);

    private ProjectDeadlineCommandParser parser = new ProjectDeadlineCommandParser();
    private final String validDeadline = "21/02/2023";


    @Test
    public void parse_validArgs_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DEADLINE + validDeadline;
        ProjectDeadlineCommand expectedCommand = new ProjectDeadlineCommand(INDEX_FIRST_PROJECT, new Deadline(validDeadline));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDeadlineDate_success() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "";
        ProjectDeadlineCommand expectedCommand = new ProjectDeadlineCommand(INDEX_FIRST_PROJECT, new Deadline(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingDeadlinePrefix_failure() {
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + validDeadline;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDeadline_failure() {
        // Invalid date format
        String userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "21-02-2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);

        // Invalid date value
        userInput = INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + "32/02/2023";
        assertParseFailure(parser, userInput, Deadline.MESSAGE_CONSTRAINTS);
    }
}
