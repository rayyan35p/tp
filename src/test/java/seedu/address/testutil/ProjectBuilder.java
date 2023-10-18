package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

/**
 * A utility class to help with building Project objects.
 */
public class ProjectBuilder {
    public static final String DEFAULT_NAME = "Alpha";
    public static final List<Employee> DEFAULT_LIST = new ArrayList<>(Arrays.asList(ALICE));
    private String projectName;
    private List<Employee> employeeList;

    /**
     * Instantiates a {@code Project} with the default details
     */
    public ProjectBuilder() {
        projectName = DEFAULT_NAME;
        employeeList = DEFAULT_LIST;
    }

    /**
     * Initializes the ProjectBuilder with details of {@code toCopy}
     */
    public ProjectBuilder(Project toCopy) {
        projectName = toCopy.name;
        employeeList = toCopy.getEmployees();
    }

    /**
     * Sets the name of the {@code Project} we are building.
     */
    public ProjectBuilder withName(String name) {
        this.projectName = name;
        return this;
    }

    /**
     * Sets the employees of the {@code Project} we are building.
     */
    public ProjectBuilder withEmployees(Employee... employees) {
        this.employeeList = new ArrayList<>(Arrays.asList(employees));
        return this;
    }
    public Project build() {
        return new Project(projectName, employeeList);
    }
}
