import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Test class for priority commands.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
class StorageTest {

    private static Storage storage;
    private static File file;
    private static TaskList tasks;
    private static Deadline deadlineTest = new Deadline("Test",
        LocalDateTime.of(2019, 12, 12, 19, 0));

    @BeforeAll
    static void setup() {
        ArrayList<Task> testList = new ArrayList<Task>();
        tasks = new TaskList(testList);
        file = new File(System.getProperty("user.dir") + "/src/test/GsonTest");
        storage = new Storage(file);
        tasks.add(deadlineTest);
    }

    @Test
    void TestSave() throws ChronologerException, IOException {
        storage.saveFile(tasks.getTasks());
        String jsonString = Files.readString(Paths.get(String.valueOf(file)), StandardCharsets.US_ASCII);

        String expectedJson = "[{\"type\":\"DEADLINE\",\"description\":\"Test\"," +
            "\"startDate\":{\"date\":{\"year\":2019,\"month\":12,\"day\":12}," +
            "\"time\":{\"hour\":19,\"minute\":0,\"second\":0,\"nano\":0}},\"priority\":\"MEDIUM\"," +
            "\"reminder\":{\"reminderDate\":{\"date\":{\"year\":2019,\"month\":12,\"day\":9}," +
            "\"time\":{\"hour\":19,\"minute\":0,\"second\":0,\"nano\":0}}},\"comment\":\"\"," +
            "\"isIgnored\":false,\"isDone\":false,\"modCode\":\"\"}]";

        Assertions.assertEquals(expectedJson, jsonString);
    }

    @AfterAll
    static void teardownSetup() {
        assert file.delete();
    }


}
