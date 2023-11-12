package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;





public class JsonAdaptedTaskTest {
    public static final LocalDateTime VALID_DEADLINE = ALPHA_TASK.getDeadline();
    public static final Boolean VALID_COMPLETION_STATUS = ALPHA_TASK.isDone();
    public static final List<JsonAdaptedEmployee> VALID_EMPLOYEE = ALPHA_TASK.getEmployee()
            .stream()
            .map(JsonAdaptedEmployee::new)
            .collect(Collectors.toList());
    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task =
            new JsonAdaptedTask(null, VALID_DEADLINE, VALID_COMPLETION_STATUS, VALID_EMPLOYEE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_emptyName_throwsIllegalValueException() {
        JsonAdaptedTask task =
            new JsonAdaptedTask("", VALID_DEADLINE, VALID_COMPLETION_STATUS, VALID_EMPLOYEE);
        String expectedMessage = Task.MESSAGE_NO_TASK;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }
}
