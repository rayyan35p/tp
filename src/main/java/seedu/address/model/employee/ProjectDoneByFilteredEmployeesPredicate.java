package seedu.address.model.employee;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Project} is done by any of the specified filtered employees.
 */
public class ProjectDoneByFilteredEmployeesPredicate implements Predicate<Project> {

    private final ObservableList<Employee> filteredEmployees;

    public ProjectDoneByFilteredEmployeesPredicate(ObservableList<Employee> filteredEmployees) {
        this.filteredEmployees = filteredEmployees;
    }

    @Override
    public boolean test(Project project) {
        return filteredEmployees.stream()
                .anyMatch(employee -> project.getEmployees().stream()
                        .anyMatch(otherEmployee -> otherEmployee.isSameEmployee(employee)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectDoneByFilteredEmployeesPredicate)) {
            return false;
        }

        ProjectDoneByFilteredEmployeesPredicate otherPredicate = (ProjectDoneByFilteredEmployeesPredicate) other;
        return filteredEmployees.equals(otherPredicate.filteredEmployees);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filtered employees", filteredEmployees).toString();
    }

}
