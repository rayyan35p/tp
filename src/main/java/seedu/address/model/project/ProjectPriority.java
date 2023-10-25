package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Project's priority in TaskHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class ProjectPriority {
    public static final String MESSAGE_CONSTRAINTS =
            "Priority should only be low, normal, or high.";

    public final String value;

    /**
     * Constructs a {@code ProjectPriority}.
     *
     * @param priority A valid priority.
     */
    public ProjectPriority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.value = priority.toLowerCase().trim();
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        String lowerCaseTest = test.toLowerCase().trim();
        return lowerCaseTest.equals("low") || lowerCaseTest.equals("normal") || lowerCaseTest.equals("high");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectPriority)) {
            return false;
        }

        ProjectPriority otherPriority = (ProjectPriority) other;
        return value.equals(otherPriority.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
