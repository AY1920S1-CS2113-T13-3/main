package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Priority;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Adds a priority level to a certain task.
 *
 * @author Tan Yi Xiang
 * @version v1.3
 */
public class PriorityCommand extends Command {

    private int indexOfTask;
    private String priorityString;

    public PriorityCommand(int indexOfTask, String priorityString) {
        this.indexOfTask = indexOfTask;
        this.priorityString = priorityString.toLowerCase();
    }

    /**
     * Updates the priority level of a task that is not ignorable and saves the
     * updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {

        Priority newPriority = Priority.getPriorityLevel(priorityString);
        if (newPriority == Priority.INVALID) {
            throw new ChronologerException(ChronologerException.invalidPriorityLevel());
        }
        if (!isIndexValid(indexOfTask, tasks.getSize())) {
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }
        Task task = tasks.getTasks().get(indexOfTask);
        task.setPriority(newPriority);
        history.addState((tasks.getTasks()));
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage("Got it! " + task.getDescription() + " priority level is now " + priorityString);
    }
}
