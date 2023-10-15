package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Project;

/**
 * Jackson-friendly version of {@link Project}.
 */

public class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Projects' %s field is missing!";
    private final String name;
    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */

    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name, @JsonProperty("employees") List<JsonAdaptedEmployee> employees){
        this.name = name;
        if (employees != null) {
           this.employees.addAll(employees);
        }
    }

    /**
     * Converts a given {@code project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.name;
        employees.addAll(source.getEmployees().stream()
                .map(JsonAdaptedEmployee::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted employee object into the model's {@code Employee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee.
     */
    public Project toModelType() throws IllegalValueException {
        final List<Employee> employeeList = new ArrayList<>();
        for (JsonAdaptedEmployee employee : employees) {
            employeeList.add(employee.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Project.class.getSimpleName()));
        }
        if (!Project.isValidProject(name)) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }
        return new Project(name,employeeList);
    }


}
