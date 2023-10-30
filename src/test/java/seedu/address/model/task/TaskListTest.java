package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;
import static seedu.address.testutil.TypicalTasks.BETA_TASK;
import static seedu.address.testutil.TypicalTasks.CHARLIE_TASK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.task.exceptions.TaskNotFoundException;

public class TaskListTest {

    private final TaskList taskList = new TaskList();

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    public void setTasks_nullTargetTasks_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((TaskList) null));
    }

    public void setTasks_nullTargetTasksArrayList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((List<Task>) null));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove((Task) null));
    }
    @Test
    public void remove_nullTaskIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove((Index) null));
    }

    @Test
    public void remove_nonExistentTask_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.remove(ALPHA_TASK));
    }

    @Test
    public void remove_existingTask_removesTask() {
        taskList.add(ALPHA_TASK);
        taskList.remove(ALPHA_TASK);
        TaskList expectedTaskList = new TaskList();
        assertEquals(expectedTaskList, taskList);
    }
    @Test
    public void setTasks_arrayList_replacesOwnListWithProvidedList() {
        taskList.add(ALPHA_TASK);
        List<Task> tasks = Collections.singletonList(BETA_TASK);
        taskList.setTasks(tasks);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(BETA_TASK);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        taskList.add(ALPHA_TASK);
        TaskList tasks = new TaskList();
        tasks.add(BETA_TASK);
        taskList.setTasks(tasks);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(BETA_TASK);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> taskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsTest() {
        assertTrue(taskList.equals(taskList));
        assertTrue(!taskList.equals(null));
    }

    @Test
    void iterator_shouldIterateOverTasksInOrder() {
        // Create a list of tasks
        List<Task> tasks = new ArrayList<>();
        tasks.add(ALPHA_TASK);
        tasks.add(BETA_TASK);
        tasks.add(CHARLIE_TASK);

        // Create a TaskList with the tasks
        TaskList taskList = new TaskList();
        taskList.setTasks(tasks);

        // Create an iterator
        Iterator<Task> iterator = taskList.iterator();

        // Check if the iterator returns tasks in the correct order
        assertTrue(iterator.hasNext());
        assertEquals(ALPHA_TASK, iterator.next());
        assertEquals(BETA_TASK, iterator.next());
        assertEquals(CHARLIE_TASK, iterator.next());


        assertFalse(iterator.hasNext());
    }
}
