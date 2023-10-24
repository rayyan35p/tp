package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Project deadline should be a valid date in the format DD/MM/YY, with leading 0s. E.g.: 17/02/2009";

    /**
     * Either DD/MM/YYYY or empty string
     */
    public static final String VALIDATION_REGEX =
            "^(([0]?[1-9]|[1|2][0-9]|[3][0|1])[/]([0]?[1-9]|[1][0-2])[/]([0-9]{4}))?$";

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
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns date in LocalDate format
     */
    public LocalDate getLocalDate() {
        String[] date = value.split("/");
        return LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
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
