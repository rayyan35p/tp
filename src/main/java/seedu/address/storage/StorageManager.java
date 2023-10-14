package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTaskHub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TaskHub data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskHubStorage taskHubStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TaskHubStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TaskHubStorage taskHubStorage, UserPrefsStorage userPrefsStorage) {
        this.taskHubStorage = taskHubStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskHub methods ==============================

    @Override
    public Path getTaskHubFilePath() {
        return taskHubStorage.getTaskHubFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskHub> readTaskHub() throws DataLoadingException {
        return readTaskHub(taskHubStorage.getTaskHubFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskHub> readTaskHub(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskHubStorage.readTaskHub(filePath);
    }

    @Override
    public void saveTaskHub(ReadOnlyTaskHub taskHub) throws IOException {
        saveTaskHub(taskHub, taskHubStorage.getTaskHubFilePath());
    }

    @Override
    public void saveTaskHub(ReadOnlyTaskHub taskHub, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskHubStorage.saveTaskHub(taskHub, filePath);
    }

}
