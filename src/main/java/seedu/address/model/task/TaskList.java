package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of Tasks that does not allow nulls.
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
    /**
     * Removes the Task at index i from the list.
     * The Task must exist in the list.
     */
    public void remove(Index i) {
        requireNonNull(i);
        try {
            internalList.remove(i.getZeroBased());
        } catch (UnsupportedOperationException | IndexOutOfBoundsException e) {
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
     * Replaces the task at {@code targetIndex} in the list with {@code editedTask}.
     * {@code targetIndex} must be a valid index of the list.
     * The project identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Index targetIndex, Task editedTask) {
        requireAllNonNull(targetIndex, editedTask);

        Task targetTask = internalList.get(targetIndex.getZeroBased());

        internalList.set(targetIndex.getZeroBased(), editedTask);
    }

    /**
     * Sorts tasks in internalList according to completion and deadline.
     * Incomplete tasks will appear before complete tasks and tasks will be arranged in ascending deadline order.
     */
    public void sortTasksByDeadlineAndCompletion() {
        Comparator<Task> customComparator = Comparator.comparing(Task::isDone)
                .thenComparing(Task::getDeadline);
        FXCollections.sort(internalList, customComparator);
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
