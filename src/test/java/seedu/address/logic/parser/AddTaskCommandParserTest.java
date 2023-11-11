package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DEADLINE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NO_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NO_NAME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEE_INDEX_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_INDEX_TASK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ALL_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEmployees.ALICE;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        List<Employee> employeeList = Collections.singletonList(ALICE);

        Task expectedTask = new TaskBuilder().withName(VALID_NAME_TASK)
                                             .withDoneness(false).withEmployee(employeeList)
                                             .build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_TASK_ALL_FIELDS,
                new AddTaskCommand(expectedTask,
                                   ParserUtil.parseIndex(VALID_PROJECT_INDEX_TASK),
                                   ParserUtil.parseIndex(VALID_EMPLOYEE_INDEX_TASK)));
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

    @Test
    public void parse_missingTaskName_failure() {
        String expectedMessage = Task.MESSAGE_NO_TASK;

        List<Employee> employeeList = Collections.singletonList(ALICE);

        // User command has no name
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TASK_NO_NAME, expectedMessage);
    }
    @Test
    public void parse_missingTaskDeadline_failure() {
        String expectedMessage = Task.MESSAGE_NO_DEADLINE;

        List<Employee> employeeList = Collections.singletonList(ALICE);

        // User command has no deadline
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TASK_NO_DEADLINE, expectedMessage);
    }
    @Test
    public void parse_invalidTaskDeadline_failure() {
        String expectedMessage = Task.MESSAGE_INVALID_DEADLINE;

        List<Employee> employeeList = Collections.singletonList(ALICE);

        // User command has no deadline
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TASK_DEADLINE_FORMAT, expectedMessage);
    }

}
