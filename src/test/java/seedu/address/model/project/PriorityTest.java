package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriorityTest() {
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("l o w")); // spaces between characters

        assertTrue(Priority.isValidPriority("low"));
        assertTrue(Priority.isValidPriority("normal"));
        assertTrue(Priority.isValidPriority("high"));
        assertTrue(Priority.isValidPriority("NORMAL")); // all capitalised
    }

    @Test
    public void equalsTest() {
        Priority priority = new Priority("low");

        assertTrue(priority.equals(new Priority("low")));

        assertTrue(priority.equals(priority));

        assertFalse(priority.equals(null));

        assertFalse(priority.equals(new Priority("high")));
    }
}
