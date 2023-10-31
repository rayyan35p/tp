package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_INDEX_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_THREE_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;



class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_TASK)
                                             .withDoneness(false)
                                             .build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_TASK_THREE_FIELDS,
                new AddTaskCommand(expectedTask, ParserUtil.parseIndex(VALID_PROJECT_INDEX_TASK)));
    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PREFIX_PROJECT + VALID_PROJECT_INDEX_TASK + " "
                                      + PREFIX_DEADLINE + VALID_DEADLINE_TASK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PROJECT_INDEX_TASK + VALID_NAME_TASK + VALID_DEADLINE_TASK,
                expectedMessage);
    }

}
