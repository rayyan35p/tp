package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;

public class PriorityProjectCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullPriority_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PriorityProjectCommand(null,
                List.of(INDEX_FIRST_PROJECT)));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PriorityProjectCommand(null, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project projectToSetPriority = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        PriorityProjectCommand projectPriorityCommand = new PriorityProjectCommand(new Priority("high"),
                List.of(INDEX_FIRST_PROJECT));
        Project projectWithNewPriority = new Project(projectToSetPriority.getName(),
                projectToSetPriority.getEmployees(),
                projectToSetPriority.getTasks(),
                new Priority("high"),
                projectToSetPriority.getDeadline(),
                projectToSetPriority.getCompletionStatus());

        String expectedMessage = String.format(PriorityProjectCommand.MESSAGE_SUCCESS,
                "high", "1");

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToSetPriority, projectWithNewPriority);

        assertCommandSuccess(projectPriorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIndexUnfilteredList_success() {
        Project projectToSetPriority = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectToSetPriority2 = model.getFilteredProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        PriorityProjectCommand projectPriorityCommand = new PriorityProjectCommand(new Priority("high"),
                List.of(INDEX_FIRST_PROJECT, INDEX_SECOND_PROJECT));
        Project projectWithNewPriority = new Project(projectToSetPriority.getName(),
                projectToSetPriority.getEmployees(),
                projectToSetPriority.getTasks(),
                new Priority("high"),
                projectToSetPriority.getDeadline(),
                projectToSetPriority.getCompletionStatus());
        Project projectWithNewPriority2 = new Project(projectToSetPriority2.getName(),
                projectToSetPriority2.getEmployees(),
                projectToSetPriority2.getTasks(),
                new Priority("high"),
                projectToSetPriority2.getDeadline(),
                projectToSetPriority2.getCompletionStatus());

        String expectedMessage = String.format(PriorityProjectCommand.MESSAGE_SUCCESS,
                "high", "2");

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToSetPriority, projectWithNewPriority);
        expectedModel.setProject(projectToSetPriority2, projectWithNewPriority2);

        assertCommandSuccess(projectPriorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        PriorityProjectCommand projectPriorityCommand = new PriorityProjectCommand(
                new Priority("high"), List.of(outOfBoundIndex));

        assertCommandFailure(projectPriorityCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equalsTest() {
        PriorityProjectCommand priorityProjectFirstCommand = new PriorityProjectCommand(
                new Priority("high"),
                List.of(INDEX_FIRST_PROJECT));
        PriorityProjectCommand priorityProjectSecondCommand = new PriorityProjectCommand(
                new Priority("low"),
                List.of(INDEX_SECOND_PROJECT));

        // same object -> returns true
        assertTrue(priorityProjectFirstCommand.equals(priorityProjectFirstCommand));

        // same values -> returns true
        PriorityProjectCommand priorityProjectFirstCommandCopy = new PriorityProjectCommand(
                new Priority("high"),
                List.of(INDEX_FIRST_PROJECT));
        assertTrue(priorityProjectFirstCommand.equals(priorityProjectFirstCommandCopy));

        // different types -> returns false
        assertFalse(priorityProjectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(priorityProjectFirstCommand.equals(null));

        // different priority -> returns false
        assertFalse(priorityProjectFirstCommand.equals(priorityProjectSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PriorityProjectCommand projectPriorityEmployeeCommand = new PriorityProjectCommand(
                new Priority("high"),
                List.of(targetIndex));
        String expected = PriorityProjectCommand.class.getCanonicalName() + "{toSet=high, targetIndex="
                + List.of(targetIndex) + "}";
        assertEquals(expected, projectPriorityEmployeeCommand.toString());
    }
}
