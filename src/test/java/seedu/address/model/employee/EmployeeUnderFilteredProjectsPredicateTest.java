package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskHub;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ProjectBuilder;

public class EmployeeUnderFilteredProjectsPredicateTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void testEquals() {
        ObservableList<Project> filteredProjects = model.getFilteredProjectList();

        EmployeeUnderFilteredProjectsPredicate firstPredicate =
                new EmployeeUnderFilteredProjectsPredicate(filteredProjects);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeUnderFilteredProjectsPredicate firstPredicateCopy =
                new EmployeeUnderFilteredProjectsPredicate(filteredProjects);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different filtered projects -> returns false
        Project firstProject = new ProjectBuilder().withName("First Project").build();
        Project secondProject = new ProjectBuilder().withName("Second Project").build();

        TaskHub firstTaskHub = new TaskHub();
        firstTaskHub.addProject(firstProject);
        firstPredicate =
                new EmployeeUnderFilteredProjectsPredicate(firstTaskHub.getProjectList());

        TaskHub secondTaskHub = new TaskHub();
        secondTaskHub.addProject(secondProject);
        EmployeeUnderFilteredProjectsPredicate secondPredicate =
                new EmployeeUnderFilteredProjectsPredicate(secondTaskHub.getProjectList());

        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeIsUnderOneOfFilteredProjects_returnsTrue() {
        Employee employee = new EmployeeBuilder().withName("Alice").build();
        Project project = new ProjectBuilder().withName("Project").withEmployees(employee).build();

        TaskHub taskHub = new TaskHub();
        taskHub.addProject(project);
        taskHub.addEmployee(employee);

        EmployeeUnderFilteredProjectsPredicate predicate =
                new EmployeeUnderFilteredProjectsPredicate(taskHub.getProjectList());
        assertTrue(predicate.test(employee));
    }

    @Test
    public void test_employeeIsNotUnderAnyOfFilteredProjects_returnsFalse() {
        Employee employee = new EmployeeBuilder().withName("Alice").build();
        Project project = new ProjectBuilder().withName("Project").build();

        TaskHub taskHub = new TaskHub();
        taskHub.addProject(project);
        taskHub.addEmployee(employee);

        EmployeeUnderFilteredProjectsPredicate predicate =
                new EmployeeUnderFilteredProjectsPredicate(taskHub.getProjectList());
        assertFalse(predicate.test(employee));
    }

    @Test
    public void toStringMethod() {
        ObservableList<Project> filteredProjects = model.getFilteredProjectList();

        EmployeeUnderFilteredProjectsPredicate firstPredicate =
                new EmployeeUnderFilteredProjectsPredicate(filteredProjects);

        String expected = EmployeeUnderFilteredProjectsPredicate.class.getCanonicalName()
                + "{filtered projects=" + filteredProjects + "}";
        assertEquals(expected, firstPredicate.toString());
    }
}
