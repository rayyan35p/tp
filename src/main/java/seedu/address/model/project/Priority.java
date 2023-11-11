package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Project's priority in TaskHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    enum Level {
        LOW,
        NORMAL,
        HIGH
    }
    public static final String MESSAGE_CONSTRAINTS =
            "Priority should only be low, normal, or high.";

    public final Level value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.value = toLevel(priority.toLowerCase().trim());
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        assert test != null;
        String lowerCaseTest = test.toLowerCase().trim();
        return lowerCaseTest.equals("low") || lowerCaseTest.equals("normal") || lowerCaseTest.equals("high");
    }

    /**
     * Converts a String priority into a Level enum. Defaults to a normal level if the input is neither high nor low.
     */
    private static Level toLevel(String priority) {
        assert priority != null;
        if (priority.equals("low")) {
            return Level.LOW;
        }
        if (priority.equals("high")) {
            return Level.HIGH;
        }
        return Level.NORMAL;
    }

    @Override
    public String toString() {
        switch(value) {
        case LOW:
            return "low";
        case HIGH:
            return "high";
        default:
            return "normal";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return value.equals(otherPriority.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
