package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;

/**
 * Represents a Project in TaskHub.
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
    public final Deadline deadline;
    public final UniqueEmployeeList employeeList;
    private final ProjectPriority projectPriority;


    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     */
    public Project(String project) {
        requireNonNull(project);
        this.name = project;
        this.projectPriority = new ProjectPriority("normal");
        this.deadline = new Deadline("");
        this.employeeList = new UniqueEmployeeList();
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param project A valid Project.
     * @param employees A list of Employees that are in the project
     * @param priority A valid ProjectPriority for the project.
     * @param deadline A valid Deadline for the project.
     */
    public Project(String project, UniqueEmployeeList employees, ProjectPriority priority, Deadline deadline) {
        requireNonNull(project);
        this.name = project;
        this.projectPriority = priority;
        this.employeeList = employees;
        this.deadline = deadline;
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

    public UniqueEmployeeList getEmployees() {
        return employeeList;
    }

    public ProjectPriority getProjectPriority() {
        return projectPriority;
    }

    public String getListOfEmployeeNames() {
        StringBuilder employeeListString = new StringBuilder();
        for (Employee employee : employeeList) {
            employeeListString.append(employee.getName() + ", ");
        }
        if (employeeList.asUnmodifiableObservableList().size() != 0) {
            employeeListString.delete(employeeListString.length() - 2,
                    employeeListString.length());
        }
        return employeeListString.toString();
    }

    public String getNameString() {
        return this.name;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Project // instanceof handles nulls
                && name.equals(((Project) other).name)
                && (Objects.equals(employeeList, ((Project) other).employeeList)));
        // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
