package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * Jackson-friendly version of {@link Project}.
 */

public class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";
    private final String name;
    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final String priority;
    private final String deadline;
    private final boolean completionStatus;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */

    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("employees") List<JsonAdaptedEmployee> employees,
                              @JsonProperty("priority") String priority,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("completionStatus") boolean completionStatus) {
        this.name = name;
        if (employees != null) {
            this.employees.addAll(employees);
        }
        this.priority = priority;
        this.deadline = deadline;
        this.completionStatus = completionStatus;
    }

    /**
     * Converts a given {@code project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.name;
        employees.addAll(source.getEmployees().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedEmployee::new)
                .collect(Collectors.toList()));
        priority = source.getProjectPriority().toString();
        deadline = source.getDeadline().toString();
        completionStatus = source.getCompletionStatus().isCompleted;
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        final UniqueEmployeeList employeeList = new UniqueEmployeeList();
        try {
            for (JsonAdaptedEmployee employee : employees) {
                employeeList.add(employee.toModelType());
            }
        } catch (DuplicateEmployeeException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Project.isValidProject(name)) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProjectPriority.class.getSimpleName()));
        }
        if (!ProjectPriority.isValidPriority(priority)) {
            throw new IllegalValueException(ProjectPriority.MESSAGE_CONSTRAINTS);
        }
        final ProjectPriority modelPriority = new ProjectPriority(priority);

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

        return new Project(name, employeeList, modelPriority, modelDeadline, modelCompletionStatus);
    }
}
