package seedu.address.testutil;

import seedu.address.model.TaskHub;
import seedu.address.model.employee.Employee;

/**
 * A utility class to help with building Taskhub objects.
 * Example usage: <br>
 *     {@code TaskHub ab = new TaskHubBuilder().withEmployee("John", "Doe").build();}
 */
public class TaskHubBuilder {

    private TaskHub taskHub;

    public TaskHubBuilder() {
        taskHub = new TaskHub();
    }

    public TaskHubBuilder(TaskHub taskHub) {
        this.taskHub = taskHub;
    }

    /**
     * Adds a new {@code Employee} to the {@code TaskHub} that we are building.
     */
    public TaskHubBuilder withEmployee(Employee employee) {
        taskHub.addEmployee(employee);
        return this;
    }

    public TaskHub build() {
        return taskHub;
    }
}
