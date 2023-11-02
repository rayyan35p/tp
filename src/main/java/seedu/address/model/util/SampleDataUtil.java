package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.TaskHub;
import seedu.address.model.employee.Address;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.CompletionStatus;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Contains utility methods for populating {@code TaskHub} with sample data.
 */
public class SampleDataUtil {

    // Employees
    public static final Employee EMPLOYEE_ALEX = new Employee(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
            getTagSet("Team1", "Manager"));
    public static final Employee EMPLOYEE_BERNICE = new Employee(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getTagSet("Team1", "Junior"));
    public static final Employee EMPLOYEE_CHARLOTTE = new Employee(new Name("Charlotte Oliveiro"),
            new Phone("93210283"),
            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getTagSet("Team2", "Manager"));
    public static final Employee EMPLOYEE_DAVID = new Employee(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getTagSet("Team2", "Junior"));
    public static final Employee EMPLOYEE_IRFAN = new Employee(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
            getTagSet("Team3", "Manager"));
    public static final Employee EMPLOYEE_ROY = new Employee(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
            getTagSet("Team3", "Junior"));

    // Employee Lists to be used in Projects
    private static final UniqueEmployeeList employeeListA = new UniqueEmployeeList();
    private static final UniqueEmployeeList employeeListB = new UniqueEmployeeList();
    private static final UniqueEmployeeList employeeListC = new UniqueEmployeeList();

    // Task deadlines to be used in Tasks
    private static final LocalDateTime deadlineA1 = LocalDateTime.of(2023, 10, 1, 12, 0);
    private static final LocalDateTime deadlineA2 = LocalDateTime.of(2023, 10, 5, 23, 59);
    private static final LocalDateTime deadlineA3 = LocalDateTime.of(2023, 11, 10, 0, 0);
    private static final LocalDateTime deadlineB1 = LocalDateTime.of(2023, 12, 16, 14, 30);
    private static final LocalDateTime deadlineB2 = LocalDateTime.of(2023, 12, 28, 18, 0);

    // Tasks
    public static final Task TASK_A1 = new Task("Conduct User Research", deadlineA1,
            true, EMPLOYEE_ALEX);
    public static final Task TASK_A2 = new Task("Create Wireframes", deadlineA2, true, EMPLOYEE_BERNICE);
    public static final Task TASK_A3 = new Task("Responsive Web Design", deadlineA3, true, EMPLOYEE_ALEX);
    public static final Task TASK_B1 = new Task("Market Survey", deadlineB1, false, EMPLOYEE_CHARLOTTE);
    public static final Task TASK_B2 = new Task("Cost-benefit Analysis", deadlineB2, true, EMPLOYEE_DAVID);

    // Task Lists to be used in Projects
    private static final TaskList taskListA = new TaskList();
    private static final TaskList taskListB = new TaskList();
    private static final TaskList taskListC = new TaskList();

    // Projects
    public static final Project PROJECT_A = new Project(new seedu.address.model.project.Name("Website Redesign"),
            employeeListA, taskListA, new Priority("high"), new Deadline("15-11-2023"),
            new CompletionStatus(true));

    public static final Project PROJECT_B = new Project(new seedu.address.model.project.Name("Market Expansion"),
            employeeListB, taskListB, new Priority("normal"), new Deadline("30-12-2023"),
            new CompletionStatus(false));

    public static final Project PROJECT_C = new Project(new seedu.address.model.project.Name("New Product Launch"),
            employeeListC, taskListC, new Priority("low"), new Deadline("01-02-2024"),
            new CompletionStatus(false));

    // Set the employees for each employee list
    public static void setEmployeeLists() {
        employeeListA.add(EMPLOYEE_ALEX);
        employeeListA.add(EMPLOYEE_BERNICE);
        employeeListB.add(EMPLOYEE_CHARLOTTE);
        employeeListB.add(EMPLOYEE_DAVID);
        employeeListC.add(EMPLOYEE_IRFAN);
        employeeListC.add(EMPLOYEE_ROY);
    }

    // Set the tasks for each task list
    public static void setTaskLists() {
        taskListA.add(TASK_A1);
        taskListA.add(TASK_A2);
        taskListA.add(TASK_A3);
        taskListB.add(TASK_B1);
        taskListB.add(TASK_B2);
    }

    public static Employee[] getSampleEmployees() {
        return new Employee[] {
            EMPLOYEE_ALEX, EMPLOYEE_BERNICE, EMPLOYEE_CHARLOTTE, EMPLOYEE_DAVID, EMPLOYEE_IRFAN, EMPLOYEE_ROY
        };
    }

    public static Project[] getSampleProjects() {
        return new Project[] {
            PROJECT_A, PROJECT_B, PROJECT_C
        };
    }

    public static ReadOnlyTaskHub getSampleTaskHub() {
        TaskHub sampleAb = new TaskHub();
        setEmployeeLists();
        setTaskLists();
        for (Employee sampleEmployee : getSampleEmployees()) {
            sampleAb.addEmployee(sampleEmployee);
        }
        for (Project sampleProject : getSampleProjects()) {
            sampleAb.addProject(sampleProject);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
