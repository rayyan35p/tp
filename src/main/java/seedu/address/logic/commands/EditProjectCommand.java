package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Name;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectPriority;

/**
 * Edits the details of an existing project in the TaskHub.
 */
public class EditProjectCommand extends Command {

    public static final String COMMAND_WORD = "editP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the project identified "
            + "by the index number used in the displayed project list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "HIGH "
            + PREFIX_DEADLINE + "2020-12-31";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the TaskHub.";

    public final Index index;
    public final EditProjectDescriptor editProjectDescriptor;

    /**
     * Creates an EditProjectCommand to edit the specified {@code Project}
     * @param index of the project in the filtered project list to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditProjectCommand(Index index, EditProjectDescriptor editProjectDescriptor) {
        requireNonNull(index);
        requireNonNull(editProjectDescriptor);

        this.index = index;
        this.editProjectDescriptor = editProjectDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        Project editedProject = createEditedProject(projectToEdit, editProjectDescriptor);

        if (!projectToEdit.isSameProject(editedProject) && model.hasProject(editedProject)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, Messages.format(editedProject)));
    }

    private static Project createEditedProject(Project projectToEdit, EditProjectDescriptor editProjectDescriptor) {
        assert projectToEdit != null;

        Name updatedName = editProjectDescriptor.getName().orElse(projectToEdit.getName());
        ProjectPriority updatedPriority = editProjectDescriptor.getPriority()
                .orElse(projectToEdit.getProjectPriority());
        Deadline updatedDeadline = editProjectDescriptor.getDeadline().orElse(projectToEdit.getDeadline());

        return new Project(updatedName, projectToEdit.getEmployees(), projectToEdit.getTasks(), updatedPriority,
                updatedDeadline, projectToEdit.getCompletionStatus());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditProjectCommand)) {
            return false;
        }

        EditProjectCommand otherEditProjectCommand = (EditProjectCommand) other;
        return index.equals(otherEditProjectCommand.index)
                && editProjectDescriptor.equals(otherEditProjectCommand.editProjectDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editProjectDescriptor", editProjectDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class EditProjectDescriptor {
        private Name name;
        private ProjectPriority priority;
        private Deadline deadline;

        public EditProjectDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProjectDescriptor(EditProjectDescriptor toCopy) {
            setName(toCopy.name);
            setPriority(toCopy.priority);
            setDeadline(toCopy.deadline);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, priority, deadline);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPriority(ProjectPriority priority) {
            this.priority = priority;
        }

        public Optional<ProjectPriority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditProjectDescriptor)) {
                return false;
            }

            EditProjectDescriptor otherEditProjectDescriptor = (EditProjectDescriptor) other;
            return Objects.equals(name, otherEditProjectDescriptor.name)
                    && Objects.equals(priority, otherEditProjectDescriptor.priority)
                    && Objects.equals(deadline, otherEditProjectDescriptor.deadline);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("priority", priority)
                    .add("deadline", deadline)
                    .toString();
        }
    }
}
