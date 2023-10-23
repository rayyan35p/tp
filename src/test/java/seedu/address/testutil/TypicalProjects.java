package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.GEORGE;
import static seedu.address.testutil.TypicalEmployees.getTypicalEmployees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskHub;
import seedu.address.model.project.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {
    public static final Project ALPHA = new ProjectBuilder().build();
    public static final Project BETA = new ProjectBuilder().withName("Beta").withEmployees(BENSON).build();
    public static final Project DELTA = new ProjectBuilder().withName("Delta").withEmployees(DANIEL, FIONA).build();

    //Manually added
    public static final Project GAMMA = new ProjectBuilder().withName("Gamma").withEmployees(GEORGE).build();

    private TypicalProjects() {} // prevents instantiation

    /**
     * Returns an {@code TaskHub} with all the typical projects.
     */
    public static TaskHub getTypicalTaskHub() {
        TaskHub ab = new TaskHub();
        ab.setEmployees(getTypicalEmployees());
        ab.setProjects(getTypicalProjects());
        return ab;
    }
    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALPHA, BETA, DELTA, GAMMA));
    }
}
