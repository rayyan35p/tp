package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index index = ParserUtil.parseIndex("1");
        CommandResult commandResult = new AddTaskCommand(validTask,
                                                         index).execute(modelStub);
        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, index.getOneBased(),
                Messages.format(validTask)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_addTaskToInvalidProjectIndex_throwsCommandException() throws Exception {
        ModelStubWithProjectAndEmployee modelStub = new ModelStubWithProjectAndEmployee(ALPHA, ALICE);
        Task validTask = new TaskBuilder().build();
        Index index = ParserUtil.parseIndex("2");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask,
                                                         index);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX, () ->
                addTaskCommand.execute(modelStub));
    }
    @Test
    public void equals() throws ParseException {
        AddTaskCommand addAlphaCommand = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        AddTaskCommand addBetaCommand = new AddTaskCommand(BETA_TASK, ParserUtil.parseIndex("1"));

        // same object -> returns true
        assertTrue(addAlphaCommand.equals(addAlphaCommand));

        // same values -> returns true
        AddTaskCommand addAlphaCommandCopy = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        assertTrue(addAlphaCommand.equals(addAlphaCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaCommand.equals(null));

        // different task names -> returns false
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    public void toStringMethod() throws ParseException {
        AddTaskCommand addTaskCommand = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        String expected = AddTaskCommand.class.getCanonicalName() + "{toAdd=" + ALPHA_TASK + "}";
        assertEquals(expected, addTaskCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTaskHubFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskHubFilePath(Path taskHubFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskHub(ReadOnlyTaskHub newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskHub getTaskHub() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEmployee(Employee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deleteTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEmployee(Employee target, Employee editedEmployee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project projectToEdit, Project editedProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithProjectAndEmployee extends ModelStub {
        private Project project;
        private Employee employee;

        ModelStubWithProjectAndEmployee(Project project, Employee employee) {
            requireAllNonNull(project, employee);
            this.employee = employee;
            this.project = project;
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            ObservableList<Project> filteredList = FXCollections.observableArrayList();
            filteredList.add(project); // Add the project to the list
            return filteredList;
        }
        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return this.project.isSameProject(project);
        }
        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            // do nothing - no need to update the model stub with just one project
        }
        @Override
        public void setProject(Project project, Project editedProject) {
            requireAllNonNull(project, editedProject);
            this.project = editedProject;

        }
        @Override
        public void addTask(Task task) {
            this.project.addTask(task);

        }


        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            ObservableList<Employee> filteredList = FXCollections.observableArrayList();
            filteredList.add(employee); // Add the employee to the list
            return filteredList;
        }
    }

}
