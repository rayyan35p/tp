package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;

import org.junit.jupiter.api.Test;

import seedu.address.model.TaskHub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTaskHub_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTaskHub_success() {
        Model model = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTaskHub(), new UserPrefs());
        expectedModel.setTaskHub(new TaskHub());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
