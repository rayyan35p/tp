package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalProjects.ALPHA_FACTORY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.testutil.EmployeeBuilder;

public class TaskHubTest {
    private final TaskHub taskHub = new TaskHub();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskHub.getEmployeeList());
        assertEquals(Collections.emptyList(), taskHub.getProjectList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskHub.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaskHub_replacesData() {
        TaskHub newData = getTypicalTaskHub();
        taskHub.resetData(newData);
        assertEquals(newData, taskHub);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Employee> newEmployees = Arrays.asList(ALICE, editedAlice);
        TaskHubStub newData = new TaskHubStub(newEmployees, new ArrayList<>(), new ArrayList<>());

        assertThrows(DuplicateEmployeeException.class, () -> taskHub.resetData(newData));
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskHub.hasEmployee(null));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskHub.hasProject(null));
    }

    @Test
    public void hasEmployee_employeeNotInTaskHub_returnsFalse() {
        assertFalse(taskHub.hasEmployee(ALICE));
    }

    @Test
    public void hasProject_projectNotInTaskHub_returnFalse() {
        assertFalse(taskHub.hasProject(ALPHA_FACTORY()));
    }

    @Test
    public void hasProject_projectInTaskHub_returnsTrue() {
        taskHub.addProject(ALPHA_FACTORY());
        assertTrue(taskHub.hasProject(ALPHA_FACTORY()));
    }

    @Test
    public void hasEmployee_employeeInTaskHub_returnsTrue() {
        taskHub.addEmployee(ALICE);
        assertTrue(taskHub.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_employeeWithSameIdentityFieldsInTaskHub_returnsTrue() {
        taskHub.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(taskHub.hasEmployee(editedAlice));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskHub.getEmployeeList().remove(0));
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskHub.getProjectList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TaskHub.class.getCanonicalName() + "{employees=" + taskHub.getEmployeeList() + "}";
        assertEquals(expected, taskHub.toString());
    }

    @Test
    public void hashCodeGenerator_nonEmptyString_isValidHashCode() {
        UniqueEmployeeList list = new UniqueEmployeeList();
        TaskHub taskHub = new TaskHub();
        assertEquals(taskHub.hashCode(), list.hashCode());
    }

    @Test
    public void equals() {
        TaskHub taskHub = new TaskHub();

        // same object -> returns true
        assertTrue(taskHub.equals(taskHub));

        // null -> returns false
        assertFalse(taskHub.equals(null));
    }

    /**
     * A stub ReadOnlyTaskHub whose employees list can violate interface constraints.
     */
    private static class TaskHubStub implements ReadOnlyTaskHub {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();
        private final ObservableList<Project> projects = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskHubStub(Collection<Employee> employees, Collection<Project> projects, Collection<Task> tasks) {
            this.employees.setAll(employees);
            this.projects.setAll(projects);
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

    }

}
