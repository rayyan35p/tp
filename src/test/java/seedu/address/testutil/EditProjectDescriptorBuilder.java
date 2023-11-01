package seedu.address.testutil;

import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.EditProjectCommand.EditProjectDescriptor;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Name;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * A utility class to help with building EditProjectDescriptor objects.
 */
public class EditProjectDescriptorBuilder {

    private EditProjectDescriptor descriptor;

    public EditProjectDescriptorBuilder() {
        descriptor = new EditProjectDescriptor();
    }

    public EditProjectDescriptorBuilder(EditProjectDescriptor descriptor) {
        this.descriptor = new EditProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProjectDescriptor} with fields containing {@code project}'s details
     */
    public EditProjectDescriptorBuilder(Project project) {
        descriptor = new EditProjectCommand.EditProjectDescriptor();
        descriptor.setName(project.getName());
        descriptor.setPriority(project.getProjectPriority());
        descriptor.setDeadline(project.getDeadline());
    }

    /**
     * Sets the {@code Name} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code ProjectPriority} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new ProjectPriority(priority));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    public EditProjectDescriptor build() {
        return descriptor;
    }
}
