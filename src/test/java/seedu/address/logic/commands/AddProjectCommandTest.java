package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TaskHub;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;
import seedu.address.testutil.EmployeeBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

public class AddProjectCommandTest {
    /*
    @Test
    public void constructor_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null,null));
    }

    @Test
    public void execute_employeeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProjectAdded modelStub = new ModelStubAcceptingProjectAdded();
        Project validProject = new EmployeeBuilder().build();

        CommandResult commandResult = new AddCommand(validEmployee).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validEmployee)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEmployee), modelStub.employeesAdded);

     */
    }






