package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProjectPriorityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProjectPriority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new ProjectPriority(invalidPriority));
    }

    @Test
    public void isValidPriorityTest() {
        assertThrows(NullPointerException.class, () -> ProjectPriority.isValidPriority(null));

        assertFalse(ProjectPriority.isValidPriority("")); // empty string
        assertFalse(ProjectPriority.isValidPriority(" ")); // spaces only
        assertFalse(ProjectPriority.isValidPriority("l o w")); // spaces between characters

        assertTrue(ProjectPriority.isValidPriority("low"));
        assertTrue(ProjectPriority.isValidPriority("normal"));
        assertTrue(ProjectPriority.isValidPriority("high"));
        assertTrue(ProjectPriority.isValidPriority("NORMAL")); // all capitalised
    }

    @Test
    public void equalsTest() {
        ProjectPriority priority = new ProjectPriority("low");

        assertTrue(priority.equals(new ProjectPriority("low")));

        assertTrue(priority.equals(priority));

        assertFalse(priority.equals(null));

        assertFalse(priority.equals(new ProjectPriority("high")));
    }
}
