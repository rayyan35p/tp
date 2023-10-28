package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Project's completion status in TaskHub.
 * Guarantees: immutable; is always valid
 */
public class CompletionStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Completion status should only be true or false boolean values.";

    public final boolean isCompleted;

    /**
     * Constructs a {@code CompletionStatus}.
     *
     * @param isCompleted A valid completion status.
     */
    public CompletionStatus(boolean isCompleted) {
        requireNonNull(isCompleted);
        this.isCompleted = isCompleted;
    }

    public static boolean isValidCompletionStatus(boolean test) {
        return true;
    }

    @Override
    public String toString() {
        return isCompleted ? "true" : "false";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompletionStatus)) {
            return false;
        }

        CompletionStatus otherCompletionStatus = (CompletionStatus) other;
        return isCompleted == otherCompletionStatus.isCompleted;
    }

    @Override
    public int hashCode() {
        return isCompleted ? 1 : 0;
    }

}
