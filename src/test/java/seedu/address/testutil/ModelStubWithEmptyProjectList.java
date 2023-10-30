package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

/**
 * A model stub with an empty project list.
 */
public class ModelStubWithEmptyProjectList extends ModelStub {
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        ObservableList<Project> filteredList = FXCollections.observableArrayList();
        return filteredList;
    }
    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return false;
    }
    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        // do nothing - no need to update the model stub with just one project
    }
    @Override
    public void setProject(Project project, Project editedProject) {
        requireAllNonNull(project, editedProject);
    }
    @Override
    public void addTask(Task task) {
        // do nothing
    }
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
        return filteredList;
    }
}
