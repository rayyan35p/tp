package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_BOB;
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
import seedu.address.model.TaskHub;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AssignEmployeeCommand.
 */
public class ProjectCommandTest {

    private static final String PROJECT_STUB = "Some project";

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void execute_addProjectUnfilteredList_success() {
        Employee firstEmployee = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        Employee editedEmployee = new EmployeeBuilder(firstEmployee).withProject(PROJECT_STUB).build();

        AssignEmployeeCommand assignProjectCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(editedEmployee.getProject().name));

        String expectedMessage = String.format(AssignEmployeeCommand.MESSAGE_ADD_PROJECT_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setEmployee(firstEmployee, editedEmployee);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteProjectUnfilteredList_success() {
        Employee firstEmployee = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        Employee editedEmployee = new EmployeeBuilder(firstEmployee).withProject("").build();

        AssignEmployeeCommand assignProjectCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(editedEmployee.getProject().toString()));

        String expectedMessage = String.format(AssignEmployeeCommand.MESSAGE_DELETE_PROJECT_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setEmployee(firstEmployee, editedEmployee);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Employee firstEmployee = model.getFilteredEmployeeList()
                .get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        Employee editedEmployee = new EmployeeBuilder(model.getFilteredEmployeeList()
                .get(INDEX_FIRST_EMPLOYEE.getZeroBased()))
                .withProject(PROJECT_STUB).build();

        AssignEmployeeCommand assignProjectCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(editedEmployee.getProject().name));

        String expectedMessage = String.format(AssignEmployeeCommand.MESSAGE_ADD_PROJECT_SUCCESS, editedEmployee);

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setEmployee(firstEmployee, editedEmployee);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEmployeeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        AssignEmployeeCommand assignProjectCommand = new AssignEmployeeCommand(outOfBoundIndex,
                                                                                new Project(VALID_PROJECT_BOB));

        assertCommandFailure(assignProjectCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of TaskHub
     */
    @Test
    public void execute_invalidEmployeeIndexFilteredList_failure() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);
        Index outOfBoundIndex = INDEX_SECOND_EMPLOYEE;
        // ensures that outOfBoundIndex is still in bounds of TaskHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskHub().getEmployeeList().size());

        AssignEmployeeCommand assignProjectCommand = new AssignEmployeeCommand(outOfBoundIndex,
                                                                                new Project(VALID_PROJECT_BOB));

        assertCommandFailure(assignProjectCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AssignEmployeeCommand standardCommand = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(VALID_PROJECT_AMY));

        // same values -> returns true
        AssignEmployeeCommand commandWithSameValues = new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(VALID_PROJECT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AssignEmployeeCommand(INDEX_SECOND_EMPLOYEE,
                new Project(VALID_PROJECT_AMY))));

        // different project -> returns false
        assertFalse(standardCommand.equals(new AssignEmployeeCommand(INDEX_FIRST_EMPLOYEE,
                new Project(VALID_PROJECT_BOB))));
    }
}
