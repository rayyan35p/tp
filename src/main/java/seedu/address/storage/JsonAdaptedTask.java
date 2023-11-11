package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Name;
import seedu.address.model.task.Task;


/**
 * Jackson-friendly version of {@link Task}.
 */

public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tasks' %s field is missing!";
    private final String name;
    private final LocalDateTime deadline;
    private final boolean isDone;
    private final List<JsonAdaptedEmployee> employee = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given Task details.
     */

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name,
                           @JsonProperty("deadline") LocalDateTime deadline,
                           @JsonProperty("isDone") Boolean isDone,
                           @JsonProperty("employee") List<JsonAdaptedEmployee> employee) {
        this.deadline = deadline;
        this.name = name;
        this.isDone = isDone;
        if (employee != null) {
            this.employee.addAll(employee);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName();
        deadline = source.getDeadline();
        isDone = source.isDone();
        employee.addAll(source.getEmployee().stream()
                .map(JsonAdaptedEmployee::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee.
     */
    public Task toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Task.isValidTask(name)) {
            throw new IllegalValueException(Task.MESSAGE_NO_TASK);
        }
        if (employee.isEmpty()) {
            return new Task(name, deadline, isDone);
        }
        return new Task(name, deadline, isDone, employee.get(0).toModelType());
    }
}
