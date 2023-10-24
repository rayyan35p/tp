package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.CARL;
import static seedu.address.testutil.TypicalEmployees.ELLE;
import static seedu.address.testutil.TypicalEmployees.FIONA;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.project.ProjectDoneByFilteredEmployeesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmployeeCommand}.
 */
public class FindEmployeeCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void equals() {
        EmployeeNameContainsKeywordsPredicate firstPredicate =
                new EmployeeNameContainsKeywordsPredicate(Collections.singletonList("first"));
        EmployeeNameContainsKeywordsPredicate secondPredicate =
                new EmployeeNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEmployeeCommand findFirstCommand = new FindEmployeeCommand(firstPredicate);
        FindEmployeeCommand findSecondCommand = new FindEmployeeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEmployeeCommand findFirstCommandCopy = new FindEmployeeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEmployeeFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 0);
        EmployeeNameContainsKeywordsPredicate employeePredicate = preparePredicate(" ");
        FindEmployeeCommand command = new FindEmployeeCommand(employeePredicate);
        expectedModel.updateFilteredEmployeeList(employeePredicate);

        ProjectDoneByFilteredEmployeesPredicate projectPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(model.getFilteredEmployeeList());
        expectedModel.updateFilteredProjectList(projectPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 3);
        EmployeeNameContainsKeywordsPredicate employeePredicate = preparePredicate("Kurz Elle Kunz");
        FindEmployeeCommand command = new FindEmployeeCommand(employeePredicate);
        expectedModel.updateFilteredEmployeeList(employeePredicate);

        ProjectDoneByFilteredEmployeesPredicate projectPredicate =
                new ProjectDoneByFilteredEmployeesPredicate(model.getFilteredEmployeeList());
        expectedModel.updateFilteredProjectList(projectPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredEmployeeList());
    }

    @Test
    public void toStringMethod() {
        EmployeeNameContainsKeywordsPredicate predicate =
                new EmployeeNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindEmployeeCommand findEmployeeCommand = new FindEmployeeCommand(predicate);
        String expected = FindEmployeeCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findEmployeeCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code EmployeeNameContainsKeywordsPredicate}.
     */
    private EmployeeNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EmployeeNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
