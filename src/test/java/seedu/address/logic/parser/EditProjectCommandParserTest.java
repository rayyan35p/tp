package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.EditProjectCommand.EditProjectDescriptor;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Name;
import seedu.address.model.project.ProjectPriority;
import seedu.address.testutil.EditProjectDescriptorBuilder;

public class EditProjectCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE);

    private EditProjectCommandParser parser = new EditProjectCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROJECT_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditProjectCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROJECT_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROJECT_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_PROJECT_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, "1" + INVALID_PROJECT_PRIORITY_DESC, ProjectPriority.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, "1" + INVALID_PROJECT_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_PROJECT_PRIORITY_DESC + VALID_PROJECT_AMY
                        + VALID_DEADLINE_PROJECT_AMY, ProjectPriority.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_AMY + PROJECT_PRIORITY_DESC_AMY
                + PROJECT_DEADLINE_DESC_AMY;

        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_PROJECT_AMY)
                .withPriority(VALID_PRIORITY_AMY).withDeadline(VALID_DEADLINE_PROJECT_AMY).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_BOB + PROJECT_PRIORITY_DESC_AMY;

        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withPriority(VALID_PRIORITY_AMY)
                .withName(VALID_PROJECT_BOB).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_AMY;
        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_PROJECT_AMY).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + PROJECT_PRIORITY_DESC_AMY;
        descriptor = new EditProjectDescriptorBuilder().withPriority(VALID_PRIORITY_AMY).build();
        expectedCommand = new EditProjectCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + PROJECT_DEADLINE_DESC_AMY;
        descriptor = new EditProjectDescriptorBuilder().withDeadline(VALID_DEADLINE_PROJECT_AMY).build();
        expectedCommand = new EditProjectCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_AMY + PROJECT_PRIORITY_DESC_AMY
                + PROJECT_DEADLINE_DESC_AMY + PROJECT_DEADLINE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEADLINE));
    }
}
