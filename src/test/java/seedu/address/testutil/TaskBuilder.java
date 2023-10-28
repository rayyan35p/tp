package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building task objects.
 * Default {@code taskBuilder} has name "Alpha", typical employee Alice and normal priority.
 */
public class TaskBuilder {
    public static final String DEFAULT_NAME = "Read Book";
    public static final String DEFAULT_DEADLINE = "2023-11-11 2359";
    public static final boolean DEFAULT_COMPLETION_STATUS = false;

    private String name;
    private LocalDateTime deadline;
    private boolean isDone;

    /**
     * Instantiates a {@code task} with the default details
     */
    public TaskBuilder() {
        name = DEFAULT_NAME;
        try {
            deadline = ParserUtil.parseLocalDateTime(DEFAULT_DEADLINE);
        } catch (ParseException e) {
            System.out.print("This should not print");
        }
        isDone = DEFAULT_COMPLETION_STATUS;
    }

    /**
     * Initializes the taskBuilder with details of {@code toCopy}
     */
    public TaskBuilder(Task toCopy) {
        name = toCopy.getName();
        deadline = toCopy.getDeadline();
        isDone = toCopy.isDone();
    }

    /**
     * Sets the name of the {@code task} we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the deadline of the {@code task} we are building.
     */
    public TaskBuilder withDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * Sets the doneness of the {@code task} we are building.
     */
    public TaskBuilder withDoneness(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public Task build() {
        return new Task(name, deadline, isDone);
    }
}
