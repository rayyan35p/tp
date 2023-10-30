package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.testutil.TypicalProjects.getTypicalTaskHub;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectBuilder;

public class UnassignProjectCommandTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        List<Index> validEmployeeIndexes = Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE);
        assertThrows(NullPointerException.class, () ->
                new UnassignProjectCommand(null, validEmployeeIndexes));
    }

    @Test
    public void constructor_nullEmployeeIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new UnassignProjectCommand(INDEX_FIRST_PROJECT, null));
    }

    @Test
    public void execute_validInputs_success() {
        Project projectToUnassign = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        ProjectBuilder projectBuilder = new ProjectBuilder(projectToUnassign);

        // Assign Alice and Benson employees to project
        model.setProject(projectToUnassign, projectBuilder.withEmployees(ALICE, BENSON).build());

        List<Index> employeeIndexes = Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE);

        // Command to unassign Alice and Benson employees from project
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                employeeIndexes);

        Project editedProject = projectBuilder.withEmployees().build();
        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToUnassign, editedProject);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);

        // Verify that the employees have been removed from the project in the model
        Project projectInModel = expectedModel.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        assertTrue(projectInModel.employeeList.isEmpty());
    }

    @Test
    public void execute_repeatCommand_failure() {
        Project projectToUnassign = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        ProjectBuilder projectBuilder = new ProjectBuilder(projectToUnassign);

        // Assign Alice and Benson employees to project
        model.setProject(projectToUnassign, projectBuilder.withEmployees(ALICE, BENSON).build());

        List<Index> employeeIndexes = Arrays.asList(INDEX_FIRST_EMPLOYEE);

        // Command to unassign Alice from project
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                employeeIndexes);

        // Expected project after command execution should only have Benson assigned
        Project expectedProject = projectBuilder.withEmployees(BENSON).build();
        String expectedSuccessMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.format(expectedProject));

        Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        expectedModel.setProject(projectToUnassign, expectedProject);

        // First time unassigning Alice from project
        assertCommandSuccess(unassignProjectCommand, model, expectedSuccessMessage, expectedModel);

        String expectedFailureMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_FAILURE,
                ALICE.getName(), INDEX_FIRST_EMPLOYEE.getOneBased(),
                projectToUnassign.getNameString(), INDEX_FIRST_PROJECT.getOneBased());

        // Alice is already unassigned from project, so command should fail
        assertCommandFailure(unassignProjectCommand, model, expectedFailureMessage);

        // Verify that Alice has been removed and Benson is still in the project
        Project projectInModel = expectedModel.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        assertTrue(projectInModel.employeeList.contains(BENSON));
    }

    @Test
    public void execute_invalidProjectIndex_throwsCommandException() {
        List<Index> employeeIndexes = Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE);
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(Index.fromOneBased(999),
                employeeIndexes);
        assertCommandFailure(unassignProjectCommand, model, MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEmployeeIndex_throwsCommandException() {
        Project projectToUnassign = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        ProjectBuilder projectBuilder = new ProjectBuilder(projectToUnassign);

        model.setProject(projectToUnassign, projectBuilder.withEmployees(ALICE, BENSON).build());

        List<Index> employeeIndexes = Arrays.asList(INDEX_FIRST_EMPLOYEE, Index.fromOneBased(999));
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                employeeIndexes);

        assertCommandFailure(unassignProjectCommand, model, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UnassignProjectCommand standardCommand = new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE));

        // same values -> returns true
        UnassignProjectCommand commandWithSameValues = new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different project index -> returns false
        assertFalse(standardCommand.equals(new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                Arrays.asList(INDEX_FIRST_EMPLOYEE, Index.fromOneBased(3)))));

        // different employee indexes -> returns false
        assertFalse(standardCommand.equals(new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE, Index.fromOneBased(3)))));
    }
}
