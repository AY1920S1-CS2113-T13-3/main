package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Marks a task as ignorable or not ignorable.
 *
 * @author Fauzan and Tan Yi Xiang
 * @version v1.3
 */
public class IgnoreCommand extends Command {

    private int indexOfTask;
    private boolean isIgnore;

    private static final String IGNORED = "Noted. This task has been marked as ignored:\n";
    private static final String UNIGNORED = "Noted. This task is no longer ignored:\n";

    /**
     * Initializes the different parameters when adding the location of a task.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param isIgnore Holds a boolean of whether a task isIgnorable.
     */
    public IgnoreCommand(Integer indexOfTask, boolean isIgnore) {
        this.indexOfTask = indexOfTask;
        this.isIgnore = isIgnore;
    }

    /**
     * Marks a task as ignorable and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {

        if (isIndexValid(indexOfTask, tasks.getSize())) {
            if (isIgnore) {
                Task task = tasks.markAsIgnorable(indexOfTask);
                history.addState((tasks.getTasks()));
                storage.saveFile(tasks.getTasks());
                UiMessageHandler.outputMessage(IGNORED + task.toString());
            } else {
                Task task = tasks.markAsUnignorable(indexOfTask);
                history.addState((tasks.getTasks()));
                storage.saveFile(tasks.getTasks());
                UiMessageHandler.outputMessage(UNIGNORED + task.toString());
            }
        }
    }
}
