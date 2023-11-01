package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;
import seedu.address.testutil.ModelStubWithEmptyProjectList;
import seedu.address.testutil.ModelStubWithEmptyProjectListAndEmptyEmployeeList;
import seedu.address.testutil.ModelStubWithProjectAndEmployee;
import seedu.address.testutil.TaskBuilder;
public class AddTaskCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, null));
    }

    @Test
    public void execute_taskWithoutAssigneeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index index = ParserUtil.parseIndex("1");
        CommandResult commandResult = new AddTaskCommand(validTask,
                                                         index).execute(modelStub);
        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, index.getOneBased(),
                Messages.format(validTask)),
                commandResult.getFeedbackToUser());
    }
    @Test
    public void execute_taskWithAssigneeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index projectIndex = ParserUtil.parseIndex("1");
        Index employeeIndex = ParserUtil.parseIndex("1");
        CommandResult commandResult = new AddTaskCommand(validTask,
                                                         projectIndex,
                                                         employeeIndex).execute(modelStub);
        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, projectIndex.getOneBased(),
                                    Messages.format(validTask)),
                     commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_addTaskToInvalidProjectIndex_throwsCommandException() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index index = ParserUtil.parseIndex("2");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask,
                                                         index);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX, () ->
                addTaskCommand.execute(modelStub));
    }
    @Test
    public void execute_addTaskWithInvalidEmployeeIndex_throwsCommandException() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index projectIndex = ParserUtil.parseIndex("1");
        Index employeeIndex = ParserUtil.parseIndex("5");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask,
                                                           projectIndex,
                                                           employeeIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX, () ->
                addTaskCommand.execute(modelStub));
    }

    @Test
    public void execute_addTaskToEmptyProjectList_throwsCommandException() throws Exception {
        ModelStubWithEmptyProjectList modelStub = new ModelStubWithEmptyProjectList();
        Task validTask = new TaskBuilder().build();
        Index index = ParserUtil.parseIndex("1");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask,
                                                           index);
        assertThrows(CommandException.class, Messages.MESSAGE_NO_PROJECT_TO_ADD_TASK, () ->
                addTaskCommand.execute(modelStub));
    }
    @Test
    public void execute_addTaskToEmptyEmployeeList_throwsCommandException() throws Exception {
        ModelStubWithEmptyProjectListAndEmptyEmployeeList modelStub;
        modelStub = new ModelStubWithEmptyProjectListAndEmptyEmployeeList();
        Task validTask = new TaskBuilder().build();
        Index projectIndex = ParserUtil.parseIndex("1");
        Index employeeIndex = ParserUtil.parseIndex("1");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask,
                                                           projectIndex,
                                                           employeeIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_NO_EMPLOYEE_TO_ASSIGN, () ->
                addTaskCommand.execute(modelStub));
    }
    @Test
    public void equals() throws ParseException {
        AddTaskCommand addAlphaCommand = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        AddTaskCommand addBetaCommand = new AddTaskCommand(BETA_TASK, ParserUtil.parseIndex("1"));

        // same object -> returns true
        assertTrue(addAlphaCommand.equals(addAlphaCommand));

        // same values -> returns true
        AddTaskCommand addAlphaCommandCopy = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        assertTrue(addAlphaCommand.equals(addAlphaCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaCommand.equals(null));

        // different task names -> returns false
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    public void toStringMethod() throws ParseException {
        AddTaskCommand addTaskCommand = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        String expected = AddTaskCommand.class.getCanonicalName() + "{toAdd=" + ALPHA_TASK + "}";
        assertEquals(expected, addTaskCommand.toString());
    }

}
