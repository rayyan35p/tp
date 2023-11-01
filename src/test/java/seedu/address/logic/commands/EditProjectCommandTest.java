package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalTaskHub;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditProjectCommand.EditProjectDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskHub;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.testutil.EditProjectDescriptorBuilder;
import seedu.address.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditProjectCommand.
 */
public class EditProjectCommandTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project editedProject = new ProjectBuilder().build();
        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(editedProject).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_FIRST_PROJECT, descriptor);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProject = Index.fromOneBased(model.getFilteredProjectList().size());
        Project lastProject = model.getFilteredProjectList().get(indexLastProject.getZeroBased());

        ProjectBuilder projectInList = new ProjectBuilder(lastProject);
        Project editedProject = projectInList.withName(VALID_NAME_AMY).withPriority(VALID_PRIORITY_AMY)
                .withDeadline(VALID_DEADLINE_PROJECT_AMY).build();

        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPriority(VALID_PRIORITY_AMY).withDeadline(VALID_DEADLINE_PROJECT_AMY).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(indexLastProject, descriptor);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setProject(lastProject, editedProject);

        assertCommandSuccess(editProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_FIRST_PROJECT, new EditProjectDescriptor());
        Project editedProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());

        assertCommandSuccess(editProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project projectInFilteredList = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(projectInFilteredList).withName(VALID_NAME_AMY)
                .withPriority(VALID_PRIORITY_AMY).withDeadline(VALID_DEADLINE_PROJECT_AMY).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY)
                        .withPriority(VALID_PRIORITY_AMY).withDeadline(VALID_DEADLINE_PROJECT_AMY).build());

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new TaskHub(model.getTaskHub()), new UserPrefs());
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectUnfilteredList_failure() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(firstProject).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_SECOND_PROJECT, descriptor);

        assertCommandFailure(editProjectCommand, model, EditProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_duplicateProjectFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        // edit project in filtered list into a duplicate in TaskHub
        Project projectInList = model.getTaskHub().getProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        EditProjectCommand editProjectCommand = new EditProjectCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder(projectInList).build());

        assertCommandFailure(editProjectCommand, model, EditProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditProjectCommand editProjectCommand = new EditProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of TaskHub
     */
    @Test
    public void execute_invalidProjectIndexFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        Index outOfBoundIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of TaskHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskHub().getProjectList().size());

        EditProjectCommand editProjectCommand = new EditProjectCommand(outOfBoundIndex,
                new EditProjectDescriptorBuilder().withName(VALID_NAME_AMY).build());

        assertCommandFailure(editProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditProjectCommand standardCommand = new EditProjectCommand(INDEX_FIRST_PROJECT, DESC_PROJECT_AMY);

        // same values -> returns true
        EditProjectDescriptor copyDescriptor = new EditProjectDescriptor(DESC_PROJECT_AMY);
        EditProjectCommand commandWithSameValues = new EditProjectCommand(INDEX_FIRST_PROJECT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_SECOND_PROJECT, DESC_PROJECT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_FIRST_PROJECT, DESC_PROJECT_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();
        EditProjectCommand editProjectCommand = new EditProjectCommand(index, editProjectDescriptor);
        String expected = EditProjectCommand.class.getCanonicalName() + "{index=" + index + ", editProjectDescriptor="
                + editProjectDescriptor + "}";
        assertEquals(expected, editProjectCommand.toString());
    }
}
