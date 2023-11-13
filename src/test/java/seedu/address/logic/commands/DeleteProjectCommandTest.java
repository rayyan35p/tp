package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;

import java.util.ArrayList;

public class DeleteProjectCommandTest {

    // Test Heuristic used: No More Than One Invalid Input In A Test Case
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    // Invalid input: null Project Index
    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteProjectCommand(null));
    }

    // Invalid input: none (success)
    @Test
    public void execute_validInputs_success() {
        Project projectToDelete = model.getFilteredProjectList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(projectToDelete));

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.deleteProject(projectToDelete);

        assertCommandSuccess(deleteProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: Project index out of Bounds
    @Test
    public void execute_invalidIndex_exceptionThrown() {
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(Index.fromOneBased(99));

        assertCommandFailure(deleteProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteProjectCommand deleteFirstCommand = new DeleteProjectCommand(INDEX_FIRST_EMPLOYEE);
        DeleteProjectCommand deleteSecondCommand = new DeleteProjectCommand(INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteProjectCommand deleteFirstCommandCopy = new DeleteProjectCommand(INDEX_FIRST_EMPLOYEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
