package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProjects.BETA_FACTORY;
import static seedu.address.testutil.TypicalProjects.DELTA_FACTORY;
import static seedu.address.testutil.TypicalProjects.GAMMA_FACTORY;
import static seedu.address.testutil.TypicalProjects.getTypicalTaskHub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.EmployeeUnderFilteredProjectsPredicate;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindProjectCommand}.
 */
public class FindProjectCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void equals() {
        ProjectNameContainsKeywordsPredicate firstPredicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ProjectNameContainsKeywordsPredicate secondPredicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindProjectCommand findFirstCommand = new FindProjectCommand(firstPredicate);
        FindProjectCommand findSecondCommand = new FindProjectCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindProjectCommand findFirstCommandCopy = new FindProjectCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        ProjectNameContainsKeywordsPredicate projectPredicate = preparePredicate(" ");
        FindProjectCommand command = new FindProjectCommand(projectPredicate);
        expectedModel.updateFilteredProjectList(projectPredicate);

        EmployeeUnderFilteredProjectsPredicate employeePredicate =
                new EmployeeUnderFilteredProjectsPredicate(expectedModel.getFilteredProjectList());
        expectedModel.updateFilteredEmployeeList(employeePredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void execute_multipleKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 3);
        ProjectNameContainsKeywordsPredicate projectPredicate = preparePredicate("Beta Gamma Delta");
        FindProjectCommand command = new FindProjectCommand(projectPredicate);
        expectedModel.updateFilteredProjectList(projectPredicate);

        EmployeeUnderFilteredProjectsPredicate employeePredicate =
                new EmployeeUnderFilteredProjectsPredicate(expectedModel.getFilteredProjectList());
        expectedModel.updateFilteredEmployeeList(employeePredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BETA_FACTORY(), DELTA_FACTORY(), GAMMA_FACTORY()), model.getFilteredProjectList());
    }

    @Test
    public void toStringMethod() {
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindProjectCommand findProjectCommand = new FindProjectCommand(predicate);
        String expected = FindProjectCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findProjectCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ProjectNameContainsKeywordsPredicate}.
     */
    private ProjectNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProjectNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
