package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of Tasks that enforces uniqueness between its elements and does not allow nulls.
 * (inspired from UniqueEmployeeList.java)
 */
public class TaskList implements Iterable<Task> {
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a task to the list.
     * The employee must not already exist in the list.
     */
    public void add(Task task) {
        requireNonNull(task);
        internalList.add(task);
    }

    /**
     * Removes the equivalent Task from the list.
     * The Task must exist in the list.
     */
    public void remove(Task task) {
        requireNonNull(task);
        if (!internalList.remove(task)) {
            throw new TaskNotFoundException();
        }
    }

    public void setTasks(TaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Tasks}.
     * {@code Tasks} may contain duplicate Tasks.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.setAll(tasks);
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns the task at the specified index.
     * @param index Index of the task to be obtained.
     * @return The task at the specified index.
     */
    public Task getTask(Index index) {
        return internalList.get(index.getZeroBased());
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The project identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && contains(editedTask)) {
            throw new DuplicateProjectException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Returns the size of the internalList.
     * @return The size of the internalList.
     */
    public int getSize() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskList)) {
            return false;
        }

        TaskList otherTaskList = (TaskList) other;
        return internalList.equals(otherTaskList.internalList);
    }

}
