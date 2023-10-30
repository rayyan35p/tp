package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.GEORGE;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;
import static seedu.address.testutil.TypicalTasks.CHARLIE_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskHub;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {
    public static final Project ALPHA = new ProjectBuilder().withTasks(ALPHA_TASK).build();
    public static final Project BETA = new ProjectBuilder().withName("Beta").withEmployees(BENSON).withTasks(ALPHA_TASK)
            .withDeadline("10-12-2022").withCompletionStatus(false).build();
    public static final Project DELTA = new ProjectBuilder().withName("Delta").withEmployees(DANIEL, FIONA)
            .withTasks(ALPHA_TASK, BETA_TASK)
            .withDeadline("24-11-2024").withCompletionStatus(true).build();

    //Manually added
    public static final Project GAMMA = new ProjectBuilder().withName("Gamma").withEmployees(GEORGE)
            .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
            .withDeadline("08-07-2023").build();

    public static final Project HIGH_PRIORITY_PROJECT = new ProjectBuilder()
            .withName("High Priority Project")
            .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
            .withPriority(new ProjectPriority("high"))
            .build();

    public static final Project LOW_PRIORITY_PROJECT = new ProjectBuilder()
            .withName("Low Priority Project")
            .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
            .withPriority(new ProjectPriority("low"))
            .build();

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code TaskHub} with all the typical projects.
     */
    public static TaskHub getTypicalTaskHub() {
        return TypicalEmployees.getTypicalTaskHub();
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALPHA, BETA, DELTA, GAMMA, HIGH_PRIORITY_PROJECT, LOW_PRIORITY_PROJECT));
    }
}
