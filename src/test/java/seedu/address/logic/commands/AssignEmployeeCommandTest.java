package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskHub;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ProjectBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static seedu.address.logic.commands.AddProjectCommand.MESSAGE_DUPLICATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;
import static seedu.address.testutil.TypicalProjects.ALPHA;

public class AssignEmployeeCommandTest {
    private Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());

    @Test
    public void constructor_nullProjectIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignEmployeeCommand(null, new ArrayList<>()));
    }

    @Test
    public void constructor_nullEmployeeIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignEmployeeCommand(Index.fromOneBased(1),
                                                                    null));
    }
    @Test
    public void execute_validInputs_success() {

        Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        expectedModel.setProject(ALPHA, new ProjectBuilder(ALPHA).withEmployees(ALICE, BENSON).build());
        AssignEmployeeCommand assignEmployeeCommand = new AssignEmployeeCommand(Index.fromOneBased(1),
                                                                                new ArrayList<>(Arrays.asList(Index.fromOneBased(2))));
        String expectedMessage = String.format(AssignEmployeeCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(expectedModel.getFilteredProjectList().get(0)));
        assertCommandSuccess(assignEmployeeCommand, model, expectedMessage,expectedModel);
    }

    @Test
    public void execute_employeeIndexOutOfBounds_throwsException() {
        Index outOfBoundsIndex = Index.fromOneBased(999);
        AssignEmployeeCommand assignEmployeeCommand = new AssignEmployeeCommand(Index.fromOneBased(1),
                                                                                new ArrayList<>(Arrays.asList(outOfBoundsIndex)));
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX, () ->
                assignEmployeeCommand.execute(model));
    }

}
