package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;

import org.junit.jupiter.api.Test;


public class TaskTest {

    @Test
    public void markAsDoneTest() {
        ALPHA_TASK.markAsDone();
        assertTrue(ALPHA_TASK.isDone());
    }

    @Test
    public void markAsUndoneTest() {
        ALPHA_TASK.markAsUndone();
        assertTrue(!ALPHA_TASK.isDone());
    }
    @Test
    public void equalsTest_false() {
        assertTrue(!ALPHA_TASK.equals(BETA_TASK));
        assertTrue(!ALPHA_TASK.equals(null));
    }


}
