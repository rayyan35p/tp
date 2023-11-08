package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalProjects.ALPHA_FACTORY;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.ModelStubWithEmptyProjectList;
import seedu.address.testutil.ModelStubWithProjectAndEmployee;



public class DeleteTaskCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTaskCommand(null, null));
    }

    @Test
    public void execute_deleteTaskOne_successful() throws Exception {
        // ALPHA contains ALPHA_TASK
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA_FACTORY(), ALICE);
        Index projectIndex = ParserUtil.parseIndex("1");
        Index taskIndex = ParserUtil.parseIndex("1");
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(taskIndex);
        CommandResult commandResult = new DeleteTaskCommand(projectIndex, taskIndexes).execute(modelStub);
        assertEquals(String.format(DeleteTaskCommand.MESSAGE_TASKS_DELETED_SUCCESSFULLY,
                                   taskIndexes.size(), ALPHA_FACTORY().getName()),
                    commandResult.getFeedbackToUser());
    }
    @Test
    public void execute_deleteTaskNoExistingProject_throwsCommandException() throws Exception {
        ModelStubWithEmptyProjectList modelStub = new ModelStubWithEmptyProjectList();
        Index projectIndex = ParserUtil.parseIndex("1");
        Index taskIndex = ParserUtil.parseIndex("1");
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(taskIndex);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(projectIndex,
                taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_NO_PROJECT_TO_DELETE_TASK, () ->
                deleteTaskCommand.execute(modelStub));
    }


    @Test
    public void execute_deleteTaskInvalidProjectIndex_throwsCommandException() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA_FACTORY(), ALICE);
        Index projectIndex = ParserUtil.parseIndex("50");
        Index taskIndex = ParserUtil.parseIndex("1");
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(taskIndex);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(projectIndex,
                                                                    taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX, () ->
                deleteTaskCommand.execute(modelStub));
    }

    @Test
    public void execute_deleteTaskInvalidTaskIndex_throwsCommandException() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA_FACTORY(), ALICE);
        Index projectIndex = ParserUtil.parseIndex("1");
        Index taskIndex = ParserUtil.parseIndex("50");
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(taskIndex);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(projectIndex,
                                                                    taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () ->
                deleteTaskCommand.execute(modelStub));
    }
    @Test
    public void equals() throws ParseException {
        Index projectIndex = ParserUtil.parseIndex("1");
        Index projectIndexTwo = ParserUtil.parseIndex("2");

        Index taskIndex = ParserUtil.parseIndex("1");
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(taskIndex);
        DeleteTaskCommand deleteCommandV1 = new DeleteTaskCommand(projectIndex, taskIndexes);
        DeleteTaskCommand deleteCommandV1Copy = new DeleteTaskCommand(projectIndex, taskIndexes);
        DeleteTaskCommand deleteCommandV2 = new DeleteTaskCommand(projectIndexTwo, taskIndexes);

        // same exact command -> returns true
        assertTrue(deleteCommandV1.equals(deleteCommandV1));
        // same indexes for both projects and tasks -> returns true
        assertTrue(deleteCommandV1.equals(deleteCommandV1Copy));

        // different types -> returns false
        assertFalse(deleteCommandV1.equals(1));

        // different projectIndexes -> returns false
        assertFalse(deleteCommandV1.equals(1));

        // null -> returns false
        assertFalse(deleteCommandV1.equals(null));
    }
}
