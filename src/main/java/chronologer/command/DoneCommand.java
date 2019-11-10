package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.task.Task;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Marks a task as complete or done.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class DoneCommand extends Command {
    private int indexOfTask;

    public DoneCommand(int indexOfTaskIndex) {
        this.indexOfTask = indexOfTaskIndex;
    }

    /**
     * Marks a task as complete and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task task = tasks.markAsDone(indexOfTask);
            history.addState((tasks.getTasks()));
            storage.saveFile(tasks.getTasks());
            UiMessageHandler.outputMessage("Nice! I've marked this task as done: " + task.toString());
        }
    }
}
