
package seedu.address.model.employee;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

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

    public final String value;
    public List<Employee> employeeList;

    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     */
    public Project(String project) {
        requireNonNull(project);
        value = project;
        employeeList = new ArrayList<>();
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
    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    /**
     * Removes the employee from the Project.
     */
    public void removeEmployee(Employee employee){
        employeeList.remove(employee);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Project // instanceof handles nulls
                && value.equals(((Project) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
