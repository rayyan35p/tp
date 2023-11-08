package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalProjects.alphaFactory;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ProjectBuilder;

public class AssignProjectCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignProjectCommand(null, new ArrayList<>()));
    }

    @Test
    public void constructor_nullEmployeeIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignProjectCommand(Index.fromOneBased(1),
                                                                    null));
    }
    @Test
    public void execute_validInputs_success() {

        Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        expectedModel.setProject(alphaFactory(), new ProjectBuilder(alphaFactory())
                                                                    .withEmployees(ALICE, BENSON)
                                                                    .build());
        AssignProjectCommand assignProjectCommand =
                new AssignProjectCommand(Index.fromOneBased(1),
                        new ArrayList<>(Arrays.asList(Index.fromOneBased(2))));
        String expectedMessage = String.format(AssignProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(expectedModel.getFilteredProjectList().get(0)));
        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_employeeIndexOutOfBounds_throwsException() {
        Index outOfBoundsIndex = Index.fromOneBased(999);
        AssignProjectCommand assignProjectCommand =
                new AssignProjectCommand(Index.fromOneBased(1),
                        new ArrayList<>(Arrays.asList(outOfBoundsIndex)));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX, () ->
                assignProjectCommand.execute(model));
    }

}
