package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

public class AssignTaskCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignTaskCommand(
                null,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE));
    }

    @Test
    public void constructor_nullTaskIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                null,
                INDEX_FIRST_EMPLOYEE));
    }

    @Test
    public void constructor_nullEmployeeIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignTaskCommand(INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                null));
    }

    @Test
    public void execute_validIndexes_success() {
        Project projectToAssignTasks = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE);

        Employee employeeToAssign = projectToAssignTasks.getEmployees().asUnmodifiableObservableList().get(0);
        Task taskToAssign = projectToAssignTasks.getTasks().getTask(INDEX_FIRST_TASK);

        Task taskAfterAssigning = new Task(
                taskToAssign.getName(),
                taskToAssign.getDeadline(),
                taskToAssign.isDone(),
                employeeToAssign);

        TaskList editedTaskList = new TaskList();
        editedTaskList.setTasks(projectToAssignTasks.getTasks().asUnmodifiableObservableList());
        editedTaskList.setTask(INDEX_FIRST_TASK, taskAfterAssigning);

        Project projectWithAssignedTask = new Project(projectToAssignTasks.getName(),
                projectToAssignTasks.getEmployees(),
                editedTaskList,
                projectToAssignTasks.getProjectPriority(),
                projectToAssignTasks.getDeadline(),
                projectToAssignTasks.getCompletionStatus());

        String expectedMessage = String.format(AssignTaskCommand.MESSAGE_SUCCESS,
                taskToAssign.getName(), employeeToAssign.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToAssignTasks, projectWithAssignedTask);

        assertCommandSuccess(assignTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equalsTest() {
        AssignTaskCommand assignTaskFirstCommand = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE);
        AssignTaskCommand assignTaskSecondCommand = new AssignTaskCommand(
                INDEX_SECOND_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE);
        AssignTaskCommand assignTaskThirdCommand = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_SECOND_TASK,
                INDEX_FIRST_EMPLOYEE);
        AssignTaskCommand assignTaskFourthCommand = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(assignTaskFirstCommand.equals(assignTaskFirstCommand));

        // same values -> returns true
        AssignTaskCommand assignTaskFirstCommandCopy = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE);
        assertTrue(assignTaskFirstCommand.equals(assignTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(assignTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(assignTaskFirstCommand.equals(null));

        // different project index -> returns false
        assertFalse(assignTaskFirstCommand.equals(assignTaskSecondCommand));

        // different task index -> returns false
        assertFalse(assignTaskFirstCommand.equals(assignTaskThirdCommand));

        // different employee index -> returns false
        assertFalse(assignTaskFirstCommand.equals(assignTaskFourthCommand));
    }

    @Test
    public void toStringMethod() {
        AssignTaskCommand assignTaskCommand = new AssignTaskCommand(
                INDEX_FIRST_PROJECT,
                INDEX_FIRST_TASK,
                INDEX_FIRST_EMPLOYEE);
        String expected = AssignTaskCommand.class.getCanonicalName()
            + "{projectIndex=" + INDEX_FIRST_PROJECT + ", "
            + "taskIndex=" + INDEX_FIRST_TASK + ", "
            + "employeeIndex=" + INDEX_FIRST_EMPLOYEE + "}";;
        assertEquals(expected, assignTaskCommand.toString());
    }
}
