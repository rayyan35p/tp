package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Name;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Jackson-friendly version of {@link Project}.
 */

public class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";
    public static final String MESSAGE_NOT_ASSIGNED_EMPLOYEE = "Employee(s) assigned to task not found in project!";
    private final String name;
    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final String priority;
    private final String deadline;
    private final boolean completionStatus;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */

    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("employees") List<JsonAdaptedEmployee> employees,
                              @JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                              @JsonProperty("priority") String priority,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("completionStatus") boolean completionStatus) {
        this.name = name;
        if (employees != null) {
            this.employees.addAll(employees);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
        this.priority = priority;
        this.deadline = deadline;
        this.completionStatus = completionStatus;
    }

    /**
     * Converts a given {@code project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.getName().toString();
        employees.addAll(source.getEmployees().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedEmployee::new)
                .collect(Collectors.toList()));
        tasks.addAll(source.getTasks().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
        priority = source.getPriority().toString();
        deadline = source.getDeadline().toString();
        completionStatus = source.getCompletionStatus().isCompleted;
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        // convert employees
        final UniqueEmployeeList employeeList = new UniqueEmployeeList();
        try {
            for (JsonAdaptedEmployee employee : employees) {
                employeeList.add(employee.toModelType());
            }
        } catch (DuplicateEmployeeException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // convert tasks
        final TaskList taskList = new TaskList();
        try {
            for (JsonAdaptedTask task : tasks) {
                Task modeledTask = task.toModelType();
                for(Employee assignedEmployee : modeledTask.getEmployee()) {
                    if(!employeeList.strictlyContains(assignedEmployee)) {
                        throw new IllegalValueException(MESSAGE_NOT_ASSIGNED_EMPLOYEE);
                    }
                }
                taskList.add(modeledTask);
            }
        } catch (DuplicateEmployeeException e) {
            throw new IllegalValueException(e.getMessage());
        }

        // convert other attributes
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Project.isValidProjectName(name)) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(this.deadline);

        // Since completionStatus is a boolean primitive type, we do not need to check if it is null
        if (!CompletionStatus.isValidCompletionStatus(completionStatus)) {
            throw new IllegalValueException(CompletionStatus.MESSAGE_CONSTRAINTS);
        }
        final CompletionStatus modelCompletionStatus = new CompletionStatus(this.completionStatus);

        return new Project(modelName, employeeList, taskList, modelPriority, modelDeadline, modelCompletionStatus);
    }
}
