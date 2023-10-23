package seedu.address.model.employee;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Employee} is under any of the specified filtered projects.
 */
public class EmployeeUnderFilteredProjectsPredicate implements Predicate<Employee> {

    private final ObservableList<Project> filteredProjects;

    public EmployeeUnderFilteredProjectsPredicate(ObservableList<Project> filteredProjects) {
        this.filteredProjects = filteredProjects;
    }

    @Override
    public boolean test(Employee employee) {
        return filteredProjects.stream()
                .anyMatch(project -> project.getEmployees().stream()
                        .anyMatch(otherEmployee -> otherEmployee.isSameEmployee(employee)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmployeeUnderFilteredProjectsPredicate)) {
            return false;
        }

        EmployeeUnderFilteredProjectsPredicate otherPredicate = (EmployeeUnderFilteredProjectsPredicate) other;
        return filteredProjects.equals(otherPredicate.filteredProjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filtered projects", filteredProjects).toString();
    }

}
