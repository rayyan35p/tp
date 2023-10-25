package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Project's deadline in TaskHub.
 * Guarantees: immutable; is always valid
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Project deadline should be \n"
                    + "1. an empty string (to remove deadline); or \n"
                    + "2. a valid date in the dd-MM-yyyy format. Example: 17-02-2009";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);


    /**
     * Either dd/MM/yyyy or empty string
     */
    public final String value;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        value = deadline;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        // No deadline
        if (test.equals("")) {
            return true;
        }

        // Valid deadline in dd-MM-yyyy format
        try {
            LocalDate.parse(test, DATE_FORMATTER);
            return true; // Parsing success: Valid deadline
        } catch (Exception e) {
            return false; // Parsing failed: Invalid deadline
        }
    }

    /**
     * Returns date in LocalDate format
     */
    public LocalDate getLocalDate() {
        return LocalDate.parse(value, DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
