package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;


/**
 * A model stub that contains a single employee and project.
 */
public class ModelStubWithProjectAndEmployee extends ModelStub {
    private Project project;
    private Employee employee;

    /**
     * Constructs a model stub that contains a single employee and project.
     *
     * @param project Project to be added.
     * @param employee Employee to be added.
     */
    public ModelStubWithProjectAndEmployee(Project project, Employee employee) {
        requireAllNonNull(project, employee);
        this.project = project;
        this.employee = employee;
    }

    @Override
    public ObservableList<Project> getFilteredProjectList() {
        ObservableList<Project> filteredList = FXCollections.observableArrayList();
        filteredList.add(project);
        return filteredList;
    }

    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
        filteredList.add(employee);
        return filteredList;
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return this.project.isSameProject(project);
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        // do nothing
    }

    @Override
    public void setProject(Project project, Project editedProject) {
        requireAllNonNull(project, editedProject);
        this.project = editedProject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModelStubWithProjectAndEmployee)) {
            return false;
        }
        ModelStubWithProjectAndEmployee model = (ModelStubWithProjectAndEmployee) other;
        return this.project.equals(model.project) && this.employee.equals(model.employee);
    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        project.getTasks().forEach(x -> {
            res.append(x.toString() + ", ");
        });
        return res.toString();
    }
}
