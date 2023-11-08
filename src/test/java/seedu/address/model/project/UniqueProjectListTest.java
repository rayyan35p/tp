package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalProjects.alphaFactory;

import org.junit.jupiter.api.Test;

import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;
import seedu.address.testutil.ProjectBuilder;

public class UniqueProjectListTest {
    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(alphaFactory()));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(alphaFactory());
        assertTrue(uniqueProjectList.contains(alphaFactory()));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProjectList.add(alphaFactory());
        Project editedAlpha = new ProjectBuilder(alphaFactory()).withEmployees(BOB).build();
        assertTrue(uniqueProjectList.contains(editedAlpha));
    }

    @Test
    public void updateProject_success() {
        uniqueProjectList.add(alphaFactory());
        UniqueProjectList otherList = new UniqueProjectList();
        otherList.setProjects(uniqueProjectList);

        assertEquals(uniqueProjectList, otherList);
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniqueProjectList.add(alphaFactory());
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.add(alphaFactory()));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.remove(alphaFactory()));
    }
    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(alphaFactory());
        uniqueProjectList.remove(alphaFactory());
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
