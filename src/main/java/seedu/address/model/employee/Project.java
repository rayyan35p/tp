
package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Project {

    public static final String MESSAGE_CONSTRAINTS = "Projects can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String name;
    public final List<Employee> employeeList;

    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     */
    public Project(String project) {
        requireNonNull(project);
        name = project;
        employeeList = new ArrayList<>();
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     * @param employees A list of Employees that are in the project
     */
    public Project(String project, List<Employee> employees) {
        requireNonNull(project);
        name = project;
        employeeList = employees;
    }

    /**
     * Returns true if a given string is a valid project name.
     */
    public static boolean isValidProject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adds an employee to the Project.
     */
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    /**
     * Removes the employee from the Project.
     */
    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    /**
     * Returns true if both projects have the same name.
     * This defines a weaker notion of equality between two projects.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.name.equals(this.name);
    }

    public List<Employee> getEmployees() {
        return employeeList;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Project // instanceof handles nulls
                && name.equals(((Project) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
