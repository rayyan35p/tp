package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {

    @Test
    public void constructor_validCompletionStatus_success() {
        CompletionStatus completionStatus = new CompletionStatus(true);
        assertTrue(completionStatus.isCompleted);
    }

    @Test
    public void isValidCompletionStatus_validValues_success() {
        assertTrue(CompletionStatus.isValidCompletionStatus(true));
        assertTrue(CompletionStatus.isValidCompletionStatus(false));
    }

    @Test
    public void toString_validCompletionStatus_success() {
        assertEquals("true", new CompletionStatus(true).toString());
        assertEquals("false", new CompletionStatus(false).toString());
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = new CompletionStatus(true);

        // same values -> returns true
        assertTrue(completionStatus.equals(new CompletionStatus(true)));

        // same object -> returns true
        assertTrue(completionStatus.equals(completionStatus));

        // null -> returns false
        assertFalse(completionStatus.equals(null));

        // different types -> returns false
        assertFalse(completionStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(completionStatus.equals(new CompletionStatus(false)));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(1, new CompletionStatus(true).hashCode());
        assertEquals(0, new CompletionStatus(false).hashCode());
    }
}
