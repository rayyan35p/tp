package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.HOON;
import static seedu.address.testutil.TypicalEmployees.IDA;
import static seedu.address.testutil.TypicalEmployees.getTypicalTaskHub;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.TaskHub;
import seedu.address.model.ReadOnlyTaskHub;

public class JsonTaskHubStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskHubStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaskHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskHub(null));
    }

    private java.util.Optional<ReadOnlyTaskHub> readTaskHub(String filePath) throws Exception {
        return new JsonTaskHubStorage(Paths.get(filePath)).readTaskHub(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskHub("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTaskHub("notJsonFormatTaskHub.json"));
    }

    @Test
    public void readTaskHub_invalidEmployeeTaskHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskHub("invalidEmployeeTaskHub.json"));
    }

    @Test
    public void readTaskHub_invalidAndValidEmployeeTaskHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskHub("invalidAndValidEmployeeTaskHub.json"));
    }

    @Test
    public void readAndSaveTaskHub_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskHub.json");
        TaskHub original = getTypicalTaskHub();
        JsonTaskHubStorage jsonTaskHubStorage = new JsonTaskHubStorage(filePath);

        // Save in new file and read back
        jsonTaskHubStorage.saveTaskHub(original, filePath);
        ReadOnlyTaskHub readBack = jsonTaskHubStorage.readTaskHub(filePath).get();
        assertEquals(original, new TaskHub(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEmployee(HOON);
        original.removeEmployee(ALICE);
        jsonTaskHubStorage.saveTaskHub(original, filePath);
        readBack = jsonTaskHubStorage.readTaskHub(filePath).get();
        assertEquals(original, new TaskHub(readBack));

        // Save and read without specifying file path
        original.addEmployee(IDA);
        jsonTaskHubStorage.saveTaskHub(original); // file path not specified
        readBack = jsonTaskHubStorage.readTaskHub().get(); // file path not specified
        assertEquals(original, new TaskHub(readBack));

    }

    @Test
    public void saveTaskHub_nullTaskHub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskHub(null, "SomeFile.json"));
    }

    /**
     * Saves {@code taskHub} at the specified {@code filePath}.
     */
    private void saveTaskHub(ReadOnlyTaskHub taskHub, String filePath) {
        try {
            new JsonTaskHubStorage(Paths.get(filePath))
                    .saveTaskHub(taskHub, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskHub(new TaskHub(), null));
    }
}
