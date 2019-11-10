package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.ui.UiMessageHandler;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class ChronologerStateList implements Serializable {


    private static Stack<Object> chronologerUndoStack = new Stack<>();
    private static Stack<Object> chronologerRedoStack = new Stack<>();
    private static final String filePath1 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version1";
    private static final String filePath2 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version2";
    private static final String filePath3 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version3";
    private static final File file1 = new File(filePath1);
    private static final File file2 = new File(filePath2);
    private static final File file3 = new File(filePath3);
    private static Storage storage1 = new Storage(file1);
    private static Storage storage2 = new Storage(file2);
    private static Storage storage3 = new Storage(file3);

    /**
     * Function to store the current state.
     *
     */
    public static void storeVersion(ArrayList<Task> listToStore, int version) throws ChronologerException {
        switch (version) {
        case 1:
            storage1.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 1");
            break;
        case 2:
            storage2.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 2");
            break;
        case 3:
            storage3.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 3");
            break;
        default:
            UiMessageHandler.outputMessage("Please pick a valid state from 1 - 3");
        }
    }

    /**
     * Function to restore from the given state.
     *
     */
    public static ArrayList<Task> restoreVersion(ArrayList<Task> currentVersion, int version)
        throws ChronologerException {
        switch (version) {
        case 1:
            if (storage1.loadFile(file1).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 1");
                return storage1.loadFile(file1).getTasks();
            }
            break;
        case 2:
            if (storage2.loadFile(file2).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 2");
                return storage2.loadFile(file2).getTasks();
            }
            break;
        case 3:
            if (storage3.loadFile(file3).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 3");
                return storage3.loadFile(file3).getTasks();
            }
            break;
        default:
            UiMessageHandler.outputMessage("Please pick a valid state from 1 - 3");
        }
        return currentVersion;
    }

    /**
     * Function to store the current state.
     */
    public static void addState(ArrayList<Task> listToStore) {
        chronologerUndoStack.push(SerializationUtils.clone(listToStore));
    }

    /**
     * Function to undo to previous state.
     */
    public static ArrayList<Task> undo() throws ChronologerException {
        ArrayList<Task> toReturn;
        if (chronologerUndoStack.size() <= 1) {
            throw new ChronologerException(ChronologerException.undoLimitHit());
        } else {
            chronologerRedoStack.push(chronologerUndoStack.pop());
            toReturn = (ArrayList<Task>) chronologerUndoStack.peek();
        }
        return (SerializationUtils.clone(toReturn));
    }

    /**
     * Function to redo to a previous state that was undone.
     */
    public static ArrayList<Task> redo() throws ChronologerException {
        ArrayList<Task> toReturn;
        if (chronologerRedoStack.size() == 0) {
            throw new ChronologerException(ChronologerException.redoLimitHit());
        } else {
            toReturn = (ArrayList<Task>) chronologerRedoStack.pop();
            chronologerUndoStack.push(SerializationUtils.clone(toReturn));
        }
        return (SerializationUtils.clone(toReturn));
    }
}
