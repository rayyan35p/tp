package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PROJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PROJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalTaskHub;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeadlineProjectCommand.
 */
public class DeadlineProjectCommandTest {

    private static final String DEADLINE_STUB = "21-02-2023";

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    // Invalid input: none (successful test)
    @Test
    public void execute_addDeadlineUnfilteredList_success() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(firstProject)
                .withDeadline(DEADLINE_STUB).build();

        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(editedProject.getDeadline().value));

        String expectedMessage = String.format(DeadlineProjectCommand.MESSAGE_ADD_DEADLINE_SUCCESS,
                editedProject.getDeadline(), 1);

        Model expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(firstProject, editedProject);

        assertCommandSuccess(deadlineProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: none (successful test)
    @Test
    public void execute_addDeadlineMultipleProjects_success() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project secondProject = model.getFilteredProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        Project editedFirstProject = new ProjectBuilder(firstProject)
                .withDeadline(DEADLINE_STUB).build();
        Project editedSecondProject = new ProjectBuilder(secondProject)
                .withDeadline(DEADLINE_STUB).build();

        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT,
                INDEX_SECOND_PROJECT), new Deadline(editedFirstProject.getDeadline().value));

        String expectedMessage = String.format(DeadlineProjectCommand.MESSAGE_ADD_DEADLINE_SUCCESS,
                editedFirstProject.getDeadline(), 2);

        Model expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(firstProject, editedFirstProject);
        expectedModel.setProject(secondProject, editedSecondProject);

        assertCommandSuccess(deadlineProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: none (successful test)
    @Test
    public void execute_deleteDeadlineUnfilteredList_success() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(firstProject).withDeadline("").build();

        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(editedProject.getDeadline().toString()));

        String expectedMessage = String.format(DeadlineProjectCommand.MESSAGE_DELETE_DEADLINE_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(firstProject, editedProject);

        assertCommandSuccess(deadlineProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: none (successful test)
    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(model.getFilteredProjectList()
                .get(INDEX_FIRST_PROJECT.getZeroBased())).withDeadline(DEADLINE_STUB).build();

        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(editedProject.getDeadline().value));

        String expectedMessage = String.format(DeadlineProjectCommand.MESSAGE_ADD_DEADLINE_SUCCESS,
                editedProject.getDeadline(), 1);

        Model expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        expectedModel.setProject(firstProject, editedProject);

        assertCommandSuccess(deadlineProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: project index
    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(outOfBoundIndex),
                new Deadline(VALID_DEADLINE_PROJECT_BOB));

        assertCommandFailure(deadlineProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
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

        DeadlineProjectCommand deadlineProjectCommand = new DeadlineProjectCommand(List.of(outOfBoundIndex),
                new Deadline(VALID_DEADLINE_PROJECT_BOB));

        assertCommandFailure(deadlineProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeadlineProjectCommand standardCommand = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(VALID_DEADLINE_PROJECT_AMY));

        // same values -> returns true
        DeadlineProjectCommand commandWithSameValues = new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(VALID_DEADLINE_PROJECT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeadlineProjectCommand(List.of(INDEX_SECOND_PROJECT),
                new Deadline(VALID_DEADLINE_PROJECT_AMY))));

        // different deadline -> returns false
        assertFalse(standardCommand.equals(new DeadlineProjectCommand(List.of(INDEX_FIRST_PROJECT),
                new Deadline(VALID_DEADLINE_PROJECT_BOB))));
    }
}
