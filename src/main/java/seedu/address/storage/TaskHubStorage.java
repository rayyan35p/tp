package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.TaskHub;

/**
 * Represents a storage for {@link TaskHub}.
 */
public interface TaskHubStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTaskHubFilePath();

    /**
     * Returns TaskHub data as a {@link ReadOnlyTaskHub}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTaskHub> readTaskHub() throws DataLoadingException;

    /**
     * @see #getTaskHubFilePath()
     */
    Optional<ReadOnlyTaskHub> readTaskHub(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTaskHub} to the storage.
     * @param taskHub cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskHub(ReadOnlyTaskHub taskHub) throws IOException;

    /**
     * @see #saveTaskHub(ReadOnlyTaskHub)
     */
    void saveTaskHub(ReadOnlyTaskHub taskHub, Path filePath) throws IOException;

}
