package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.GEORGE;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;
import static seedu.address.testutil.TypicalTasks.CHARLIE_TASK;
import static seedu.address.testutil.TypicalTasks.DELTA_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskHub;
import seedu.address.model.project.Project;

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
    public static final Project ECHO = new ProjectBuilder().withName("Echo").withEmployees()
            .withTasks(ALPHA_TASK, BETA_TASK)
            .withDeadline("20-10-2024").withCompletionStatus(false).build();

    private TypicalProjects() {} // prevents instantiation

    /**
     * Factory method for Project with name ALPHA.
     */
    public static Project alphaFactory() {
        return new ProjectBuilder().withTasks(ALPHA_TASK).withEmployees(ALICE).build();
    }

    /**
     * Factory method for Project with name BETA.
     */
    public static Project betaFactory() {
        return new ProjectBuilder().withName("Beta").withEmployees(BENSON).withTasks(ALPHA_TASK)
                .withDeadline("10-12-2022").withCompletionStatus(false).build();
    }

    /**
     * Factory method for Project with name DELTA.
     */
    public static Project deltaFactory() {
        return new ProjectBuilder().withName("Delta").withEmployees(DANIEL, FIONA)
                .withTasks(ALPHA_TASK, BETA_TASK)
                .withDeadline("24-11-2024").withCompletionStatus(true).build();

    }
    //Manually added

    /**
     * Factory method for Project with name GAMMA.
     */
    public static Project gammaFactory() {
        return new ProjectBuilder().withName("Gamma").withEmployees(GEORGE)
                .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
                .withDeadline("08-07-2023").build();
    }

    /**
     * Factory method for Project with high Priority.
     */
    private static Project highPriorityProjectFactory() {
        return new ProjectBuilder()
                .withName("High Priority Project")
                .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
                .withPriority("high")
                .build();
    }

    /**
     * Factory method for Project with low Priority.
     */
    private static Project lowPriorityProjectFactory() {
        return new ProjectBuilder()
                .withName("Low Priority Project")
                .withTasks(ALPHA_TASK, BETA_TASK, CHARLIE_TASK)
                .withPriority("low")
                .build();
    }

    /**
     * Factory method for Project with assigned tasks.
     */
    private static Project assignedTasksProjectFactory() {
        return new ProjectBuilder()
                .withName("Project with assigned task")
                .withEmployees(ALICE)
                .withTasks(ALPHA_TASK, DELTA_TASK)
                .build();
    }

    /**
     * Returns an {@code TaskHub} with all the typical projects.
     */
    public static TaskHub getTypicalTaskHub() {
        return TypicalEmployees.getTypicalTaskHub();
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(alphaFactory(), betaFactory(), deltaFactory(), gammaFactory(),
                highPriorityProjectFactory(), lowPriorityProjectFactory(),
                assignedTasksProjectFactory()));
    }


}
