package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building Project objects.
 * Default {@code ProjectBuilder} has name "Alpha" and typical employee Alice.
 */
public class ProjectBuilder {
    public static final String DEFAULT_NAME = "Alpha";
    private String projectName;
    private UniqueEmployeeList employeeList;

    /**
     * Instantiates a {@code Project} with the default details
     */
    public ProjectBuilder() {
        projectName = DEFAULT_NAME;
        employeeList = new UniqueEmployeeList();
        employeeList.add(ALICE);
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
        this.employeeList = new UniqueEmployeeList();
        for (Employee employee : employees) {
            employeeList.add(employee);
        }
        return this;
    }
    public Project build() {
        return new Project(projectName, employeeList);
    }
}
