package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalTaskHub;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.Project;
import seedu.address.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code UnmarkProjectCommand}.
 */
public class UnmarkProjectCommandTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    // Invalid input: none (successful test)
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project projectToUnmark = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        UnmarkProjectCommand unmarkProjectCommand = new UnmarkProjectCommand(List.of(INDEX_FIRST_PROJECT));

        String expectedMessage = String.format(UnmarkProjectCommand.MESSAGE_PROJECTS_UNMARKED_SUCCESSFULLY, 1);

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        Project unmarkedProject = new ProjectBuilder(projectToUnmark).withCompletionStatus(false).build();
        expectedModel.setProject(projectToUnmark, unmarkedProject);

        assertCommandSuccess(unmarkProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: none (successful test)
    @Test
    public void execute_validIndexesUnfilteredList_success() {
        List<Index> indexes = List.of(INDEX_FIRST_PROJECT, INDEX_SECOND_PROJECT);
        Project projectToUnmark1 = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectToUnmark2 = model.getFilteredProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        UnmarkProjectCommand unmarkProjectCommand = new UnmarkProjectCommand(indexes);

        String expectedMessage = String.format(UnmarkProjectCommand.MESSAGE_PROJECTS_UNMARKED_SUCCESSFULLY, 2);

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        Project unmarkedProject1 = new ProjectBuilder(projectToUnmark1).withCompletionStatus(false).build();
        Project unmarkedProject2 = new ProjectBuilder(projectToUnmark2).withCompletionStatus(false).build();
        expectedModel.setProject(projectToUnmark1, unmarkedProject1);
        expectedModel.setProject(projectToUnmark2, unmarkedProject2);

        assertCommandSuccess(unmarkProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: project index
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        List<Index> indexes = List.of(outOfBoundIndex);
        UnmarkProjectCommand unmarkProjectCommand = new UnmarkProjectCommand(indexes);

        assertCommandFailure(unmarkProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PROJECT);
        UnmarkProjectCommand unmarkFirstCommand = new UnmarkProjectCommand(indexes);
        List<Index> otherIndexes = new ArrayList<>();
        otherIndexes.add(INDEX_SECOND_PROJECT);
        UnmarkProjectCommand unmarkSecondCommand = new UnmarkProjectCommand(otherIndexes);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkProjectCommand unmarkFirstCommandCopy = new UnmarkProjectCommand(indexes);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different projectIndexes -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }
}
