package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Reads and writes to persistent storage in Json format.
 *
 * @author Tan Yi Xiang
 * @version v3.0
 */
public class Storage {

    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedReader bufferedReader;

    /**
     * This Storage constructor is used to function is used to assign the different
     * parameters required by the Storage methods.
     *
     * @param file This parameter holds the file to write to.
     */
    public Storage(File file) {
        this.file = file;
        this.file.getParentFile().mkdirs();
    }

    /**
     * Save updates made to the TaskList to the persistent storage in Json form.
     *
     * @param listOfTasks This parameter holds the updated TaskList of the user and
     *                    used to save the updated TaskList.
     * @throws ChronologerException This exception is thrown if there is not file at the
     *                              given location to save to.
     */
    public void saveFile(ArrayList<Task> listOfTasks) throws ChronologerException {
        try {
            setOutputStreams();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(listOfTasks);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            UiTemporary.printOutput(ChronologerException.unableToWriteFile());
            throw new ChronologerException(ChronologerException.unableToWriteFile());
        }
    }

    /**
     * Loads the tasklist stored in the Json file.
     *
     * @param file This parameter is passed as to be able to write to the file.
     * @throws ChronologerException This exception is thrown for any unexpected issues such
     *                              as no file in location, unable to read the file or a
     *                              class in not found.
     */
    public TaskList loadFile(File file) throws ChronologerException {
        try {
            setInputStreams(file);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            Gson gson = new GsonBuilder().registerTypeAdapter(TaskList.class, new TaskListAdapter())
                .create();
            TaskList taskList = gson.fromJson(json,TaskList.class);
            bufferedReader.close();
            return taskList;

        } catch (FileNotFoundException e) {
            UiTemporary.printOutput(ChronologerException.fileDoesNotExist());
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        } catch (IOException e) {
            UiTemporary.printOutput(ChronologerException.unableToReadFile());
            throw new ChronologerException(ChronologerException.unableToReadFile());
        }
    }

    private void setOutputStreams() throws IOException {
        this.fileOutputStream = new FileOutputStream(file);
    }

    private void setInputStreams(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream);
        this.bufferedReader = new BufferedReader(reader);
    }

}
