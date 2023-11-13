package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddProjectCommand.MESSAGE_DUPLICATE_PROJECT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalProjects.alphaFactory;
import static seedu.address.testutil.TypicalProjects.betaFactory;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.TaskHub;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ProjectBuilder;

public class AddProjectCommandTest {

    // Heuristic applied: No More Than One Invalid Input In A Test Case

    // Invalid input: null Project
    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null, null));
    }

    //Invalid input: none (successful test)
    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProjectAdded modelStub = new ModelStubAcceptingProjectAdded();
        Project validProject = new ProjectBuilder().withEmployees().build();
        ArrayList<Index> validIndex = new ArrayList<>();
        validIndex.add(Index.fromOneBased(1));
        CommandResult commandResult = new AddProjectCommand(validProject, validIndex).execute(modelStub);

        assertEquals(String.format(AddProjectCommand.MESSAGE_SUCCESS, Messages.format(validProject)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
    }

    // Invalid input: Project name exists in TaskHub
    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project validProject = new ProjectBuilder().build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(validProject, new ArrayList<>());
        ModelStub modelStub = new ModelStubWithProject(validProject);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_PROJECT, () ->
                addProjectCommand.execute(modelStub));
    }

    // Invalid input: Employee index does not exist in TaskHub
    @Test
    public void execute_addNonExistentEmployee_throwsCommandException() {
        Project project = new ProjectBuilder().build();
        ArrayList<Index> invalidIndex = new ArrayList<>();
        ModelStub modelStub = new ModelStubWithProject(project);
        invalidIndex.add(Index.fromZeroBased(modelStub.getFilteredEmployeeList().size()));
        AddProjectCommand addProjectCommand = new AddProjectCommand(project, invalidIndex);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX, () ->
                addProjectCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddProjectCommand addAlphaCommand = new AddProjectCommand(alphaFactory(), new ArrayList<>());
        AddProjectCommand addBetaCommand = new AddProjectCommand(betaFactory(), new ArrayList<>());

        // same object -> returns true
        assertTrue(addAlphaCommand.equals(addAlphaCommand));

        // same values -> returns true
        AddProjectCommand addAlphaCommandCopy = new AddProjectCommand(alphaFactory(), new ArrayList<>());
        assertTrue(addAlphaCommand.equals(addAlphaCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    public void toStringMethod() {
        AddProjectCommand addProjectCommand = new AddProjectCommand(alphaFactory(), new ArrayList<>());
        String expected = AddProjectCommand.class.getCanonicalName() + "{toAdd=" + alphaFactory() + "}";
        assertEquals(expected, addProjectCommand.toString());
    }

    /**
     * A Model stub that contains a single project.
     */
    private class ModelStubWithProject extends ModelStub {
        private final Project project;

        ModelStubWithProject(Project project) {
            requireNonNull(project);
            this.project = project;
        }

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return this.project.isSameProject(project);
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            ObservableList<Employee> list = FXCollections.observableArrayList();
            list.add(ALICE);
            return list;
        }
    }

    /**
     * A Model stub that always accept the project being added.
     */
    private class ModelStubAcceptingProjectAdded extends ModelStub {
        final ArrayList<Project> projectsAdded = new ArrayList<>();

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return projectsAdded.stream().anyMatch(project :: isSameProject);
        }

        @Override
        public void addProject(Project project) {
            requireNonNull(project);
            projectsAdded.add(project);
        }

        @Override
        public ReadOnlyTaskHub getTaskHub() {
            return new TaskHub();
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            ObservableList<Employee> list = FXCollections.observableArrayList();
            list.add(ALICE);
            return list;
        }
    }

}






