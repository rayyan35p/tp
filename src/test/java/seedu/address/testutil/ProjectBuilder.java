package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building Project objects.
 * Default {@code ProjectBuilder} has name "Alpha" and typical employee Alice.
 */
public class ProjectBuilder {
    public static final String DEFAULT_NAME = "Alpha";
    public static final String DEFAULT_DEADLINE = "01/01/2020";

    private String projectName;
    private UniqueEmployeeList employeeList;
    private Deadline deadline;

    /**
     * Instantiates a {@code Project} with the default details
     */
    public ProjectBuilder() {
        projectName = DEFAULT_NAME;
        employeeList = new UniqueEmployeeList();
        employeeList.add(ALICE);
        deadline = new Deadline(DEFAULT_DEADLINE);
    }

    /**
     * Initializes the ProjectBuilder with details of {@code toCopy}
     */
    public ProjectBuilder(Project toCopy) {
        projectName = toCopy.name;
        employeeList = toCopy.getEmployees();
        deadline = toCopy.getDeadline();
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

    /**
     * Sets the {@code Deadline} of the {@code Project} we are building.
     */
    public ProjectBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    public Project build() {
        return new Project(projectName, employeeList, deadline);
    }
}
