package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTaskHub;

/**
 * A class to access TaskHub data stored as a json file on the hard disk.
 */
public class JsonTaskHubStorage implements TaskHubStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskHubStorage.class);

    private Path filePath;

    public JsonTaskHubStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskHubFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskHub> readTaskHub() throws DataLoadingException {
        return readTaskHub(filePath);
    }

    /**
     * Similar to {@link #readTaskHub()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTaskHub> readTaskHub(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskHub> jsonTaskHub = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskHub.class);
        if (!jsonTaskHub.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaskHub.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTaskHub(ReadOnlyTaskHub taskHub) throws IOException {
        saveTaskHub(taskHub, filePath);
    }

    /**
     * Similar to {@link #saveTaskHub(ReadOnlyTaskHub)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskHub(ReadOnlyTaskHub taskHub, Path filePath) throws IOException {
        requireNonNull(taskHub);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskHub(taskHub), filePath);
    }

}
