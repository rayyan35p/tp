package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalIndexes.INDEX_ASSIGNED_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_WITH_ASSIGNED_TASKS_IN_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

public class UnassignTaskCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignTaskCommand(
                null,
                INDEX_FIRST_TASK));
    }

    @Test
    public void constructor_nullTaskIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignTaskCommand(
                INDEX_FIRST_PROJECT,
                null));
    }

    @Test
    public void execute_validIndexes_success() {
        Project projectToEdit = model.getFilteredProjectList()
                .get(INDEX_WITH_ASSIGNED_TASKS_IN_PROJECT.getZeroBased());
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(
                INDEX_WITH_ASSIGNED_TASKS_IN_PROJECT,
                INDEX_ASSIGNED_TASK);

        Task taskToEdit = projectToEdit.getTasks().getTask(INDEX_ASSIGNED_TASK);
        Task taskAfterUnassigning = new Task(
                taskToEdit.getName(),
                taskToEdit.getDeadline(),
                taskToEdit.isDone());

        TaskList editedTaskList = new TaskList();
        editedTaskList.setTasks(projectToEdit.getTasks().asUnmodifiableObservableList());
        editedTaskList.setTask(INDEX_ASSIGNED_TASK, taskAfterUnassigning);

        Project projectWithAssignedTask = new Project(projectToEdit.getName(),
                projectToEdit.getEmployees(),
                editedTaskList,
                projectToEdit.getProjectPriority(),
                projectToEdit.getDeadline(),
                projectToEdit.getCompletionStatus());

        String expectedMessage = String.format(UnassignTaskCommand.MESSAGE_SUCCESS,
                taskToEdit.getName(), taskToEdit.getEmployee().get(0).getName());

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToEdit, projectWithAssignedTask);

        assertCommandSuccess(unassignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equalsTest() {
        UnassignTaskCommand unassignTaskFirstCommand = new UnassignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK);
        UnassignTaskCommand unassignTaskSecondCommand = new UnassignTaskCommand(
                INDEX_SECOND_PROJECT,
                INDEX_FIRST_TASK);
        UnassignTaskCommand unassignTaskThirdCommand = new UnassignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(unassignTaskFirstCommand.equals(unassignTaskFirstCommand));

        // same values -> returns true
        UnassignTaskCommand unassignTaskFirstCommandCopy = new UnassignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK);
        assertTrue(unassignTaskFirstCommand.equals(unassignTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(unassignTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unassignTaskFirstCommand.equals(null));

        // different project index -> returns false
        assertFalse(unassignTaskFirstCommand.equals(unassignTaskSecondCommand));

        // different task index -> returns false
        assertFalse(unassignTaskFirstCommand.equals(unassignTaskThirdCommand));
    }

    @Test
    public void toStringMethod() {
        UnassignTaskCommand unassignTaskCommand = new UnassignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK);
        String expected = UnassignTaskCommand.class.getCanonicalName()
                + "{projectIndex=" + INDEX_FIRST_PROJECT + ", "
                + "taskIndex=" + INDEX_FIRST_TASK + "}";;
        assertEquals(expected, unassignTaskCommand.toString());
    }
}
