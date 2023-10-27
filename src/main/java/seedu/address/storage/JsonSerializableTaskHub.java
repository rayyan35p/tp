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
import seedu.address.model.employee.Project;
import seedu.address.model.task.Task;

/**
 * An Immutable TaskHub that is serializable to JSON format.
 */
@JsonRootName(value = "taskhub")
class JsonSerializableTaskHub {

    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "Employees list contains duplicate employee(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";

    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskHub} with the given employees.
     */
    @JsonCreator
    public JsonSerializableTaskHub(@JsonProperty("employees") List<JsonAdaptedEmployee> employees,
                                   @JsonProperty("projects") List<JsonAdaptedProject> projects,
                                   @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.employees.addAll(employees);
        this.projects.addAll(projects);
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyTaskHub} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaskHub}.
     */
    public JsonSerializableTaskHub(ReadOnlyTaskHub source) {
        employees.addAll(source.getEmployeeList().stream().map(JsonAdaptedEmployee::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TaskHub into the model's {@code TaskHub} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaskHub toModelType() throws IllegalValueException {
        TaskHub taskHub = new TaskHub();
        for (JsonAdaptedEmployee jsonAdaptedEmployee : employees) {
            Employee employee = jsonAdaptedEmployee.toModelType();
            if (taskHub.hasEmployee(employee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EMPLOYEE);
            }
            taskHub.addEmployee(employee);
        }
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (taskHub.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            taskHub.addProject(project);
        }
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            taskHub.addTask(task);
        }
        return taskHub;
    }

}
