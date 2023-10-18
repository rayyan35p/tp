package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

/**
 * Unmodifiable view of an TaskHub
 */
public interface ReadOnlyTaskHub {

    /**
     * Returns an unmodifiable view of the employees list.
     * This list will not contain any duplicate employees.
     */
    ObservableList<Employee> getEmployeeList();
    ObservableList<Project> getProjectList();

}
