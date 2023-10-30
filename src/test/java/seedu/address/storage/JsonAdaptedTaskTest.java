package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;





public class JsonAdaptedTaskTest {
    public static final LocalDateTime VALID_DEADLINE = ALPHA_TASK.getDeadline();
    public static final Boolean VALID_COMPLETION_STATUS = ALPHA_TASK.isDone();
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//            new JsonAdaptedTask(null, VALID_DEADLINE, VALID_COMPLETION_STATUS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_whiteSpaceInName_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//            new JsonAdaptedTask(" taskName", VALID_DEADLINE, VALID_COMPLETION_STATUS);
//        String expectedMessage = Task.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
}
