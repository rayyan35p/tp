package seedu.address.model.project;

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
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ProjectBuilder;

public class ProjectDoneByFilteredEmployeesPredicateTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void testEquals() {
        ObservableList<Employee> filteredEmployees = model.getFilteredEmployeeList();

        ProjectDoneByFilteredEmployeesPredicate firstPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(filteredEmployees);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProjectDoneByFilteredEmployeesPredicate firstPredicateCopy =
                new ProjectDoneByFilteredEmployeesPredicate(filteredEmployees);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different filtered employees -> returns false
        Employee firstEmployee = new EmployeeBuilder().withName("John Doe").build();
        Employee secondEmployee = new EmployeeBuilder().withName("Jane Doe").build();

        TaskHub firstTaskHub = new TaskHub();
        firstTaskHub.addEmployee(firstEmployee);
        firstPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(firstTaskHub.getEmployeeList());

        TaskHub secondTaskHub = new TaskHub();
        secondTaskHub.addEmployee(secondEmployee);
        ProjectDoneByFilteredEmployeesPredicate secondPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(secondTaskHub.getEmployeeList());

        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_projectHasOneOfFilteredEmployees_returnsTrue() {
        Employee employee = new EmployeeBuilder().withName("Alice").build();
        Project project = new ProjectBuilder().withName("Project").withEmployees(employee).build();

        TaskHub taskHub = new TaskHub();
        taskHub.addProject(project);
        taskHub.addEmployee(employee);

        ProjectDoneByFilteredEmployeesPredicate predicate =
                new ProjectDoneByFilteredEmployeesPredicate(taskHub.getEmployeeList());
        assertTrue(predicate.test(project));
    }

    @Test
    public void test_projectDoesNotHaveAnyOfFilteredEmployees_returnsFalse() {
        Employee employee = new EmployeeBuilder().withName("Alice").build();
        Project project = new ProjectBuilder().withName("Project").build();

        TaskHub taskHub = new TaskHub();
        taskHub.addProject(project);
        taskHub.addEmployee(employee);

        ProjectDoneByFilteredEmployeesPredicate predicate =
                new ProjectDoneByFilteredEmployeesPredicate(taskHub.getEmployeeList());
        assertFalse(predicate.test(project));
    }

    @Test
    public void testToString() {
        ObservableList<Employee> filteredEmployees = model.getFilteredEmployeeList();

        ProjectDoneByFilteredEmployeesPredicate firstPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(filteredEmployees);

        String expected = ProjectDoneByFilteredEmployeesPredicate.class.getCanonicalName()
                + "{filtered employees=" + filteredEmployees + "}";
        assertEquals(expected, firstPredicate.toString());
    }
}
