package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.TaskHub;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * An Immutable TaskHub that is serializable to JSON format.
 */
@JsonRootName(value = "taskhub")
class JsonSerializableTaskHub {

    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "Employees list contains duplicate employee(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";
    public static final String MESSAGE_NONEXISTENT_EMPLOYEE = "Employee(s) in project does not exist.";

    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskHub} with the given employees.
     */
    @JsonCreator
    public JsonSerializableTaskHub(@JsonProperty("employees") List<JsonAdaptedEmployee> employees,
                                   @JsonProperty("projects") List<JsonAdaptedProject> projects) {
        this.employees.addAll(employees);
        this.projects.addAll(projects);
    }

    /**
     * Converts a given {@code ReadOnlyTaskHub} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaskHub}.
     */
    public JsonSerializableTaskHub(ReadOnlyTaskHub source) {
        employees.addAll(source.getEmployeeList().stream().map(JsonAdaptedEmployee::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TaskHub into the model's {@code TaskHub} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaskHub toModelType() throws IllegalValueException {
        TaskHub taskHub = new TaskHub();
        for (JsonAdaptedEmployee jsonAdaptedEmployee : employees) {
            addEmployee(jsonAdaptedEmployee.toModelType(), taskHub);
        }
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            addProject(jsonAdaptedProject.toModelType(), taskHub);
        }
        return taskHub;
    }
    private void addEmployee(Employee employee, TaskHub taskHub) throws IllegalValueException {
        if (taskHub.hasEmployee(employee)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_EMPLOYEE);
        }
        taskHub.addEmployee(employee);
    }

    private void addProject(Project project, TaskHub taskHub) throws IllegalValueException {
        if (taskHub.hasProject(project)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
        }
        for (Employee maybeEmployee : project.getEmployees()) {
            if (!taskHub.strictlyHasEmployee(maybeEmployee)) {
                throw new IllegalValueException(MESSAGE_NONEXISTENT_EMPLOYEE);
            }
        }
        taskHub.addProject(project);
    }
}
