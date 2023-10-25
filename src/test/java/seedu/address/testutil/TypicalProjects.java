package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.GEORGE;

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
    public static final Project ALPHA = new ProjectBuilder().build();
    public static final Project BETA = new ProjectBuilder().withName("Beta").withEmployees(BENSON).build();
    public static final Project DELTA = new ProjectBuilder().withName("Delta").withEmployees(DANIEL, FIONA).build();

    //Manually added
    public static final Project GAMMA = new ProjectBuilder().withName("Gamma").withEmployees(GEORGE).build();

    public static final Project HIGH_PRIORITY_PROJECT = new ProjectBuilder()
            .withName("High Priority Project")
            .withPriority(new ProjectPriority("high"))
            .build();

    public static final Project LOW_PRIORITY_PROJECT = new ProjectBuilder()
            .withName("Low Priority Project")
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
        return new ArrayList<>(Arrays.asList(ALPHA, BETA, DELTA, GAMMA));
    }
}
