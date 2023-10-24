package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.ALPHA;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Name;
import seedu.address.model.project.Project;

public class JsonAdaptedProjectTest {
    public static final String INVALID_NAME = "     ";
    public static final String INVALID_DEADLINE = "32/13/2024";

    public static final String VALID_NAME = ALPHA.name;
    public static final List<JsonAdaptedEmployee> VALID_EMPLOYEES = ALPHA.getEmployees().asUnmodifiableObservableList()
            .stream()
            .map(JsonAdaptedEmployee::new)
            .collect(Collectors.toList());
    public static final String VALID_DEADLINE = ALPHA.getDeadline().toString();


    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        JsonAdaptedProject project = new JsonAdaptedProject(ALPHA);
        assertEquals(ALPHA, project.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject project =
                new JsonAdaptedProject(INVALID_NAME, VALID_EMPLOYEES, VALID_DEADLINE);
        String expectedMessage = Project.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProject project =
            new JsonAdaptedProject(null, VALID_EMPLOYEES, VALID_DEADLINE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, project::toModelType);
    }


}
