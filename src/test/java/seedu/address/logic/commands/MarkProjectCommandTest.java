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
 * Contains integration tests (interaction with the Model) for {@code MarkProjectCommand}.
 */
public class MarkProjectCommandTest {

    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    // Invalid input: none (successful test)
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project projectToMark = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        MarkProjectCommand markProjectCommand = new MarkProjectCommand(List.of(INDEX_FIRST_PROJECT));

        String expectedMessage = String.format(MarkProjectCommand.MESSAGE_PROJECTS_MARKED_SUCCESSFULLY, 1);

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        Project markedProject = new ProjectBuilder(projectToMark).withCompletionStatus(true).build();
        expectedModel.setProject(projectToMark, markedProject);

        assertCommandSuccess(markProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: none (successful test)
    @Test
    public void execute_validIndexesUnfilteredList_success() {
        List<Index> indexes = List.of(INDEX_FIRST_PROJECT, INDEX_SECOND_PROJECT);
        Project projectToMark1 = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectToMark2 = model.getFilteredProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        MarkProjectCommand markProjectCommand = new MarkProjectCommand(indexes);

        String expectedMessage = String.format(MarkProjectCommand.MESSAGE_PROJECTS_MARKED_SUCCESSFULLY, 2);

        ModelManager expectedModel = new ModelManager(model.getTaskHub(), new UserPrefs());
        Project markedProject1 = new ProjectBuilder(projectToMark1).withCompletionStatus(true).build();
        Project markedProject2 = new ProjectBuilder(projectToMark2).withCompletionStatus(true).build();
        expectedModel.setProject(projectToMark1, markedProject1);
        expectedModel.setProject(projectToMark2, markedProject2);

        assertCommandSuccess(markProjectCommand, model, expectedMessage, expectedModel);
    }

    // Invalid input: project index
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        List<Index> indexes = List.of(outOfBoundIndex);
        MarkProjectCommand markProjectCommand = new MarkProjectCommand(indexes);

        assertCommandFailure(markProjectCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PROJECT);
        MarkProjectCommand markFirstCommand = new MarkProjectCommand(indexes);
        List<Index> otherIndexes = new ArrayList<>();
        otherIndexes.add(INDEX_SECOND_PROJECT);
        MarkProjectCommand markSecondCommand = new MarkProjectCommand(otherIndexes);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkProjectCommand markFirstCommandCopy = new MarkProjectCommand(indexes);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different projectIndexes -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }
}
