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
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * Jackson-friendly version of {@link Project}.
 */

public class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Projects' %s field is missing!";
    private final String name;
    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */

    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("employees") List<JsonAdaptedEmployee> employees,
                              @JsonProperty("priority") String priority) {
        this.name = name;
        if (employees != null) {
            this.employees.addAll(employees);
        }
        this.priority = priority;
    }

    /**
     * Converts a given {@code project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.name;
        employees.addAll(source.getEmployees().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedEmployee::new)
                .collect(Collectors.toList()));
        priority = source.getProjectPriority().value;
    }

    /**
     * Converts this Jackson-friendly adapted employee object into the model's {@code Employee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee.
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
        return new Project(name, employeeList, modelPriority);
    }


}
