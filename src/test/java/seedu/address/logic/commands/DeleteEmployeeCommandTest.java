package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEmployeeCommand}.
 */
public class DeleteEmployeeCommandTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteEmployeeCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(employeeToDelete));

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);

        assertCommandSuccess(deleteEmployeeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteEmployeeCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteEmployeeCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS,
                Messages.format(employeeToDelete));

        Model expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);
        showNoEmployee(expectedModel);

        assertCommandSuccess(deleteEmployeeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Index outOfBoundIndex = INDEX_SECOND_EMPLOYEE;
        // ensures that outOfBoundIndex is still in bounds of TaskHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskHub().getEmployeeList().size());

        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteEmployeeCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEmployeeCommand deleteFirstCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        DeleteEmployeeCommand deleteSecondCommand = new DeleteEmployeeCommand(INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEmployeeCommand deleteFirstCommandCopy = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(targetIndex);
        String expected = DeleteEmployeeCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteEmployeeCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEmployee(Model model) {
        model.updateFilteredEmployeeList(p -> false);

        assertTrue(model.getFilteredEmployeeList().isEmpty());
    }
}
