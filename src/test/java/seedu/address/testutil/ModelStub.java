package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * A default model stub that have all the methods failing.
 */
public class ModelStub implements Model {

    // Note: this class is adapted from the private ModelStub class of AddCommandTest.java in AB3.

    //@@author Chandan8186-reused
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

    //@@author aslam341-reused
    @Override
    public void addEmployee(Employee employee) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author antonTan96-reused
    @Override
    public void addProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chandan8186-reused
    @Override
    public void setTaskHub(ReadOnlyTaskHub newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTaskHub getTaskHub() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author aslam341-reused
    @Override
    public boolean hasEmployee(Employee employee) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author antonTan96-reused
    @Override
    public boolean hasProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author aslam341-reused
    @Override
    public void deleteEmployee(Employee target) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author antonTan96-reused
    @Override
    public void deleteProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author aslam341-reused
    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        throw new AssertionError("This method should not be called.");
    }

    //@@author antonTan96-reused
    @Override
    public ObservableList<Project> getFilteredProjectList() {

        throw new AssertionError("This method should not be called.");
    }

    //@@author aslam341-reused
    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author antonTan96-reused
    @Override
    public void setProject(Project projectToEdit, Project editedProject) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    //@@author Chandan8186
    @Override
    public void sortTasksByDeadlineAndCompletion() {
        throw new AssertionError("This method should not be called.");
    }
}
