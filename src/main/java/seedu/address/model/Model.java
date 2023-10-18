package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.employee.Employee;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_EMPLOYEES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TaskHub file path.
     */
    Path getTaskHubFilePath();

    /**
     * Sets the user prefs' TaskHub file path.
     */
    void setTaskHubFilePath(Path taskHubFilePath);

    /**
     * Replaces TaskHub data with the data in {@code taskHub}.
     */
    void setTaskHub(ReadOnlyTaskHub taskHub);

    /** Returns the TaskHub */
    ReadOnlyTaskHub getTaskHub();

    /**
     * Returns true if a employee with the same identity as {@code employee} exists in the TaskHub.
     */
    boolean hasEmployee(Employee employee);

    /**
     * Deletes the given employee.
     * The employee must exist in the TaskHub.
     */
    void deleteEmployee(Employee target);

    /**
     * Adds the given employee.
     * {@code employee} must not already exist in the TaskHub.
     */
    void addEmployee(Employee employee);

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     * {@code target} must exist in the TaskHub.
     * The employee identity of {@code editedEmployee} must not be the same as another existing employee in the
     * TaskHub.
     */
    void setEmployee(Employee target, Employee editedEmployee);

    /** Returns an unmodifiable view of the filtered employee list */
    ObservableList<Employee> getFilteredEmployeeList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEmployeeList(Predicate<Employee> predicate);
}
