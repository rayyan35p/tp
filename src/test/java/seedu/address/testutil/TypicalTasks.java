package seedu.address.testutil;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskHub;
import seedu.address.model.task.Task;
/**
 * A utility class containing a list of {@code task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    public static final LocalDateTime DEFAULT_DEADLINE = LocalDateTime.parse("11-11-2023 2359", FORMATTER);

    public static final Task ALPHA_TASK = new TaskBuilder().build();
    public static final Task BETA_TASK = new TaskBuilder().withName("Beta")
            .withDeadline(DEFAULT_DEADLINE)
            .withDoneness(false)
            .build();
    public static final Task CHARLIE_TASK = new TaskBuilder().withName("Charlie")
            .withDeadline(DEFAULT_DEADLINE)
            .withDoneness(true)
            .build();
    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code TaskHub} with all the typical tasks.
     */
    public static TaskHub getTypicalTaskHub() {
        return TypicalEmployees.getTypicalTaskHub();
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALPHA_TASK, BETA_TASK, CHARLIE_TASK));
    }
}
