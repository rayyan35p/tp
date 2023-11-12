package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.project.Priority.isValidPriority;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author rayyan35p
public class PriorityTest {

    //------------------------------ Tests for constructor --------------------------------------------------------
    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: null
        assertThrows(NullPointerException.class, () -> new Priority(null));
        assertThrows(AssertionError.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Priority(""));
        // EP: spaces only
        assertThrows(IllegalArgumentException.class, () -> new Priority(" "));
        // EP: spaces between characters
        assertThrows(IllegalArgumentException.class, () -> new Priority("l o w"));
        // EP: multiple valid levels combined
        assertThrows(IllegalArgumentException.class, () -> new Priority("lownormal"));
    }

    @Test
    public void constructor_validPriority_constructs() {
        assertEquals(Priority.Level.LOW, new Priority("low").getValue()); // EP: low level
        assertEquals(Priority.Level.NORMAL, new Priority("normal").getValue()); // EP: normal level
        assertEquals(Priority.Level.HIGH, new Priority("high").getValue()); // EP: high level
        assertEquals(Priority.Level.HIGH, new Priority("HIGH").getValue()); // EP: all capitalised
    }

    //-----------------------------------------------------------------------------------------------------------

    @Test
    public void isValidPriorityTest() {
        assertThrows(AssertionError.class, () -> isValidPriority(null)); // EP: null

        assertFalse(isValidPriority("")); // EP: empty string
        assertFalse(isValidPriority(" ")); // EP: spaces only
        assertFalse(isValidPriority("l o w")); // EP: spaces between characters
        assertFalse(isValidPriority("lownormal")); // EP: multiple valid levels combined

        assertTrue(isValidPriority("low")); // EP: low level
        assertTrue(isValidPriority("normal")); // EP: normal level
        assertTrue(isValidPriority("high")); // EP: high level
        assertTrue(isValidPriority("NORMAL")); // EP: all capitalised
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
