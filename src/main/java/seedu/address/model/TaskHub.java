package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Project;
import seedu.address.model.project.UniqueProjectList;

/**
 * Wraps all data at the task-hub level
 * Duplicates are not allowed (by .isSameEmployee comparison)
 */
public class TaskHub implements ReadOnlyTaskHub {

    private final UniqueEmployeeList employees;
    private final UniqueProjectList projects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        employees = new UniqueEmployeeList();
        projects = new UniqueProjectList();
    }

    public TaskHub() {}

    /**
     * Creates an TaskHub using the Employees in the {@code toBeCopied}
     */
    public TaskHub(ReadOnlyTaskHub toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the employee list with {@code employees}.
     * {@code employees} must not contain duplicate employees.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees.setEmployees(employees);
    }

    /**
     * Replaces the contents of the Project list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setProjects(projects);
    }


    /**
     * Resets the existing data of this {@code TaskHub} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskHub newData) {
        requireNonNull(newData);

        setEmployees(newData.getEmployeeList());
        setProjects(newData.getProjectList());
    }

    //// employee-level operations

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the TaskHub.
     */
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return employees.contains(employee);
    }

    /**
     * Adds a employee to the TaskHub.
     * The employee must not already exist in the TaskHub.
     */
    public void addEmployee(Employee p) {
        employees.add(p);
    }

    /**
     * Replaces the given employee {@code target} in the list with {@code editedEmployee}.
     * {@code target} must exist in the TaskHub.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the
     * TaskHub.
     */
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireNonNull(editedEmployee);

        employees.setEmployee(target, editedEmployee);
    }

    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);

        projects.setProject(target, editedProject);
    }

    /**
     * Removes {@code key} from this {@code TaskHub}.
     * {@code key} must exist in the TaskHub.
     */
    public void removeEmployee(Employee key) {
        employees.remove(key);
    }

    //// project-level operations
    /**
     * Returns true if a project with the same identity as {@code project} exists in the TaskHub.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Adds a project to the TaskHub.
     * The project must not already exist in the TaskHub.
     */
    public void addProject(Project p) {
        projects.add(p);
    }

    /**
     * Removes {@code key} from this {@code TaskHub}.
     * {@code key} must exist in the TaskHub.
     */
    public void removeProject(Project key) {
        projects.remove(key);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("employees", employees)
                .toString();
    }

    @Override
    public ObservableList<Employee> getEmployeeList() {
        return employees.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskHub)) {
            return false;
        }

        TaskHub otherTaskHub = (TaskHub) other;
        return employees.equals(otherTaskHub.employees) && projects.equals(otherTaskHub.projects);
    }

    @Override
    public int hashCode() {
        return employees.hashCode();
    }
}
