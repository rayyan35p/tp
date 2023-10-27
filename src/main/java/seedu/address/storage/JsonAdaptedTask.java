package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Task}.
 */

public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tasks' %s field is missing!";
    private final String name;
//    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final LocalDateTime deadline;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given Task details.
     */

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name,
                           @JsonProperty("deadline") LocalDateTime deadline) {
        this.deadline = deadline;
        this.name = name;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName();
        deadline = source.getDeadline();
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
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS);
        }
        return new Task(name, deadline);
    }
}
