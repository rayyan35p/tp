package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ModelStubWithProjectAndEmployee;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TaskBuilder;


public class SortTaskCommandTest {

    private ModelStubWithProjectAndEmployee modelStub;
    private ModelStubWithProjectAndEmployee expectedModelStub;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

        Employee employee = new EmployeeBuilder().withName("Alex").build();
        Task firstTask = new TaskBuilder().withName("First")
                .withDoneness(true)
                .withDeadline(LocalDateTime.parse("01-01-2020 1900", formatter)).build();

        Task secondTask = new TaskBuilder().withName("Second")
                .withDoneness(false)
                .withDeadline(LocalDateTime.parse("01-01-2021 1900", formatter)).build();

        Task thirdTask = new TaskBuilder().withName("Third")
                .withDoneness(true)
                .withDeadline(LocalDateTime.parse("01-01-2022 1900", formatter)).build();

        Task fourthTask = new TaskBuilder().withName("Fourth")
                .withDoneness(false)
                .withDeadline(LocalDateTime.parse("01-01-2022 1900", formatter)).build();

        Project project = new ProjectBuilder().withName("Build Website")
                .withTasks(firstTask, secondTask, thirdTask, fourthTask).build();
        Project expectedProject = new ProjectBuilder().withName("Build Website")
                .withTasks(secondTask, fourthTask, firstTask, thirdTask).build();

        modelStub = new ModelStubWithProjectAndEmployee(project, employee);
        expectedModelStub = new ModelStubWithProjectAndEmployee(expectedProject, employee);
    }

    @Test
    public void execute_taskListIsNotOrdered_ordersTaskList() {
        assertCommandSuccess(new SortTaskCommand(), modelStub, SortTaskCommand.MESSAGE_SUCCESS, expectedModelStub);
    }
}
