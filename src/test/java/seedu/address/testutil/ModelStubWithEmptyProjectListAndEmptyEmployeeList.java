package seedu.address.testutil;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * A model stub with an empty project and empty employee list.
 */
public class ModelStubWithEmptyProjectListAndEmptyEmployeeList extends ModelStub {
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        ObservableList<Project> filteredList = FXCollections.observableArrayList();
        return filteredList;
    }
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
        return filteredList;
    }
    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return false;
    }
    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return false;
    }
    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        // do nothing - no need to update the model stub
    }
    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        // do nothing - no need to update the model stub
    }
    @Override
    public void setProject(Project project, Project editedProject) {
        requireAllNonNull(project, editedProject);
    }

    @Override
    public void setEmployee(Employee employee, Employee editedEmployee) {
        requireAllNonNull(employee, editedEmployee);
    }
}
