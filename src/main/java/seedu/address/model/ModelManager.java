package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the TaskHub data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskHub taskHub;
    private final UserPrefs userPrefs;
    private final FilteredList<Employee> filteredEmployees;
    private final FilteredList<Project> filteredProjects;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskHub and userPrefs.
     */
    public ModelManager(ReadOnlyTaskHub taskHub, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(taskHub, userPrefs);

        logger.fine("Initializing with TaskHub: " + taskHub + " and user prefs " + userPrefs);

        this.taskHub = new TaskHub(taskHub);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEmployees = new FilteredList<>(this.taskHub.getEmployeeList());
        filteredProjects = new FilteredList<>(this.taskHub.getProjectList());
        filteredTasks = new FilteredList<>(this.taskHub.getTaskList());
    }

    public ModelManager() {
        this(new TaskHub(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTaskHubFilePath() {
        return userPrefs.getTaskHubFilePath();
    }

    @Override
    public void setTaskHubFilePath(Path taskHubFilePath) {
        requireNonNull(taskHubFilePath);
        userPrefs.setTaskHubFilePath(taskHubFilePath);
    }

    //=========== TaskHub ================================================================================

    @Override
    public void setTaskHub(ReadOnlyTaskHub taskHub) {
        this.taskHub.resetData(taskHub);
    }

    @Override
    public ReadOnlyTaskHub getTaskHub() {
        return taskHub;
    }

    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return taskHub.hasEmployee(employee);
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return taskHub.hasProject(project);
    }

    @Override
    public void deleteEmployee(Employee target) {
        taskHub.removeEmployee(target);
    }

    @Override
    public void deleteProject(Project project) {
        taskHub.removeProject(project);
    }

    @Override
    public void deleteTask(Task task) {
        taskHub.removeTask(task);
    }

    @Override
    public void addEmployee(Employee employee) {
        taskHub.addEmployee(employee);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
    }

    @Override
    public void addProject(Project project) {
        taskHub.addProject(project);
    }
    @Override
    public void addTask(Task task) {
        taskHub.addTask(task);
    }

    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);

        taskHub.setEmployee(target, editedEmployee);
    }

    @Override
    public void setProject(Project project, Project editedProject) {
        requireAllNonNull(project, editedProject);

        taskHub.setProject(project, editedProject);
    }

    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedTaskHub}
     */
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return filteredEmployees;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedTaskHub}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return taskHub.equals(otherModelManager.taskHub)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredEmployees.equals(otherModelManager.filteredEmployees);
    }

}
