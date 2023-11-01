package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;

import org.junit.jupiter.api.Test;

public class ProjectTest {



    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Project((String) null));
    }

    @Test
    public void isValidProject() {
        // null project
        assertThrows(NullPointerException.class, () -> Project.isValidProject(null));

        // invalid projects
        assertFalse(Project.isValidProject("")); // empty string
        assertFalse(Project.isValidProject(" ")); // spaces only

        // valid projects
        assertTrue(Project.isValidProject("likes to study"));
        assertTrue(Project.isValidProject("-")); // one character
    }

    @Test
    public void equals() {
        Project project = new Project("Hello");

        // same object -> returns true
        assertTrue(project.equals(project));

        // same values -> returns true
        Project projectCopy = new Project(project.getName());
        assertTrue(project.equals(projectCopy));

        // different types -> returns false
        assertFalse(project.equals(1));

        // null -> returns false
        assertFalse(project.equals(null));

        // different project -> returns false
        Project differentProject = new Project("Bye");
        assertFalse(project.equals(differentProject));
    }

    @Test
    public void addEmployeeTest() {
        Project project = new Project("some project");
        project.addEmployee(ALICE);
        project.addEmployee(BOB);
        assertTrue(project.getEmployees().contains(ALICE) && project.getEmployees().contains(BOB));
    }

    @Test
    public void deleteEmployeeTest() {
        Project project = new Project("some project");
        project.addEmployee(ALICE);
        project.addEmployee(BOB);
        assertTrue(project.getEmployees().contains(ALICE) && project.getEmployees().contains(BOB));
        project.removeEmployee(ALICE);
        assertTrue(!project.getEmployees().contains(ALICE) && project.getEmployees().contains(BOB));
    }

    @Test
    public void getEmployeeListTest() {
        Project project = new Project("some project");
        project.addEmployee(ALICE);
        project.addEmployee(BOB);
        String expected = "1. Alice Pauline\n2. Bob Choo\n";

        assertTrue(project.getListOfEmployeeNames().equals(expected));
    }

    @Test
    void hashCodeTest() {
        Project project1 = new Project("Test Project 1");
        Project project2 = new Project("Test Project 1");

        assertEquals(project1.hashCode(), project2.hashCode());
        assertNotEquals(project1.hashCode(), new Project("Test Project 2").hashCode());
    }


}
