package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid deadlines
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("32/02/2022")); // invalid date (February 32nd)
        assertFalse(Deadline.isValidDeadline("01-01-2022")); // invalid date (use of hyphens instead of slashes)

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("")); // empty string, meaning deadline is not set
        assertTrue(Deadline.isValidDeadline("01/01/2022")); // valid date (January 1st, 2022)
        assertTrue(Deadline.isValidDeadline("17/02/2009")); // valid date (February 17th, 2009)
        assertTrue(Deadline.isValidDeadline("01/12/2023")); // valid date (December 1st, 2023)
        assertTrue(Deadline.isValidDeadline("10/03/1999")); // valid date (March 10th, 1999)
    }

    @Test
    public void hashCodeGenerator_nonEmptyString_isValidHashCode() {
        String testDeadlineString = "01/01/2022";
        Deadline deadline = new Deadline(testDeadlineString);
        assertEquals(deadline.hashCode(), testDeadlineString.hashCode());
    }

    @Test
    public void equals() {
        Deadline deadline = new Deadline("01/01/2022");

        // same values -> returns true
        assertTrue(deadline.equals(new Deadline("01/01/2022")));

        // same object -> returns true
        assertTrue(deadline.equals(deadline));

        // null -> returns false
        assertFalse(deadline.equals(null));

        // different types -> returns false
        assertFalse(deadline.equals(5.0f));

        // different values -> returns false
        assertFalse(deadline.equals(new Deadline("17/02/2009")));
    }
}
