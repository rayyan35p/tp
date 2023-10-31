package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.MarkTaskCommand.MESSAGE_TASKS_MARKED_SUCCESSFULLY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ModelStubWithEmptyProjectList;
import seedu.address.testutil.ModelStubWithProjectAndEmployee;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TaskBuilder;


public class MarkTaskCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkTaskCommand(null, null));
    }

    @Test
    public void execute_validProjectAndTaskIndexesUnfilteredList_success() throws Exception {
        Task taskToMark = new TaskBuilder().build();
        Employee employee = new EmployeeBuilder().build();
        Project targetProject = new ProjectBuilder().withName("Sample Project").withTasks(taskToMark).build();

        Index projectIndex = Index.fromOneBased(1);
        Index taskIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = List.of(taskIndex);

        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(targetProject, employee);

        CommandResult commandResult = new MarkTaskCommand(projectIndex, taskIndexes).execute(modelStub);
        assertEquals(String.format(MESSAGE_TASKS_MARKED_SUCCESSFULLY, taskIndexes.size(),
                        targetProject.getNameString()), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidProjectIndex_throwsCommandException() {
        Task taskToMark = new TaskBuilder().build();
        Employee employee = new EmployeeBuilder().build();
        Project targetProject = new ProjectBuilder().withName("Sample Project").withTasks(taskToMark).build();

        Index projectIndex = Index.fromOneBased(5);
        Index taskIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = List.of(taskIndex);

        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(targetProject, employee);

        MarkTaskCommand markTaskCommand = new MarkTaskCommand(projectIndex, taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX, () ->
                markTaskCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidTaskIndexes_throwsCommandException() {
        Task taskToMark = new TaskBuilder().build();
        Employee employee = new EmployeeBuilder().build();
        Project targetProject = new ProjectBuilder().withName("Sample Project").withTasks(taskToMark).build();

        Index projectIndex = Index.fromOneBased(1);
        Index taskIndex = Index.fromOneBased(5);
        List<Index> taskIndexes = List.of(taskIndex);

        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(targetProject, employee);

        MarkTaskCommand markTaskCommand = new MarkTaskCommand(projectIndex, taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () ->
                markTaskCommand.execute(modelStub));
    }

    @Test
    public void execute_emptyProjectList_throwsCommandException() {
        Index projectIndex = Index.fromOneBased(1);
        Index taskIndex = Index.fromOneBased(5);
        List<Index> taskIndexes = List.of(taskIndex);

        ModelStubWithEmptyProjectList modelStub = new ModelStubWithEmptyProjectList();

        MarkTaskCommand markTaskCommand = new MarkTaskCommand(projectIndex, taskIndexes);
        assertThrows(CommandException.class, Messages.MESSAGE_NO_PROJECT_TO_MARK_UNMARK_TASK, () ->
                markTaskCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Index firstProjectIndex = Index.fromOneBased(1);
        List<Index> firstTaskIndexes = new ArrayList<>();
        firstTaskIndexes.add(Index.fromOneBased(1));
        MarkTaskCommand firstMarkTaskCommand = new MarkTaskCommand(firstProjectIndex, firstTaskIndexes);

        Index secondProjectIndex = Index.fromOneBased(2);
        List<Index> secondTaskIndexes = new ArrayList<>();
        secondTaskIndexes.add(Index.fromOneBased(3));
        MarkTaskCommand secondMarkTaskCommand = new MarkTaskCommand(secondProjectIndex, secondTaskIndexes);

        // same object -> returns true
        assertTrue(firstMarkTaskCommand.equals(firstMarkTaskCommand));

        // same values -> returns true
        MarkTaskCommand firstMarkTaskCommandCopy = new MarkTaskCommand(firstProjectIndex, firstTaskIndexes);
        assertTrue(firstMarkTaskCommand.equals(firstMarkTaskCommandCopy));

        // different types -> returns false
        assertFalse(firstMarkTaskCommand.equals(1));

        // null -> returns false
        assertFalse(firstMarkTaskCommand.equals(null));

        // different projectIndex or taskIndexes -> returns false
        assertFalse(firstMarkTaskCommand.equals(secondMarkTaskCommand));
    }
}
