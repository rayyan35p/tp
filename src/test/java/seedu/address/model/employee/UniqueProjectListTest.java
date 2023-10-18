package seedu.address.model.employee;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.exceptions.DuplicateProjectException;
import seedu.address.model.employee.exceptions.ProjectNotFoundException;
import seedu.address.testutil.ProjectBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalProjects.ALPHA;

public class UniqueProjectListTest {
    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(ALPHA));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(ALPHA);
        assertTrue(uniqueProjectList.contains(ALPHA));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProjectList.add(ALPHA);
        Project editedAlpha = new ProjectBuilder(ALPHA).withEmployees(BOB).build();
        assertTrue(uniqueProjectList.contains(editedAlpha));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniqueProjectList.add(ALPHA);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.add(ALPHA));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.remove(ALPHA));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(ALPHA);
        uniqueProjectList.remove(ALPHA);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueProjectList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueProjectList.asUnmodifiableObservableList().toString(), uniqueProjectList.toString());
    }
}
