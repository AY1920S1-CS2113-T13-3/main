package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.Ui;
import chronologer.ui.UiTemporary;

import java.util.ArrayList;

/**
 * Ensures that all the classes of command type have implementations of the method execute.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public abstract class Command {

    /**
     * Defines that isExit is false when instantiated to allow program to continue running.
     */
    protected boolean isExit = false;

    /**
     * Terminates the program by setting isExit to true.
     */
    public void commandOut() {
        isExit = true;
    }

    /**
     * Checks if the index of a Task provided by the user is within the TaskList.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param currentSizeOfTasks  Holds the integer value of the current list size.
     */
    public boolean isIndexValid(Integer indexOfTask, Integer currentSizeOfTasks) throws ChronologerException {
        if (indexOfTask < 0 || indexOfTask > (currentSizeOfTasks - 1)) {
            UiTemporary.printOutput(ChronologerException.invalidIndex());
            throw new ChronologerException(ChronologerException.invalidIndex());
        }
        return true;
    }

    /**
     * Checks if the index of a Task provided by the user is within the TaskList.
     *
     * @param tasks  Holds the list that need to be formatted for UI.
     */
    public void outputRequiredList(ArrayList<Task> tasks) {
        Integer i = 1;
        Integer j = 1;
        for (Task task : tasks) {
            UiTemporary.printMessage(i++ + "." + task.toString());
            UiTemporary.userOutputForUI += j++ + "." + task.toString() + "\n";
        }
        UiTemporary.printDash();
    }



    /**
     * Contracts all Command type classes to have their own respective execute
     * methods.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws ChronologerException Throws the exception according to the user-defined list: DukeException.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws ChronologerException;

    /**
     * This getExit function is called by a program to check the status of exit.
     */
    public boolean getExit() {
        return isExit;
    }
}