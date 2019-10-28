package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Priority;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

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
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {

        Priority newPriority = Priority.getPriorityLevel(priorityString);
        if (newPriority == Priority.INVALID) {
            UiTemporary.printOutput(ChronologerException.invalidPriorityLevel());
            throw new ChronologerException(ChronologerException.invalidPriorityLevel());
        }
        if (!isIndexValid(indexOfTask, tasks.getSize())) {
            UiTemporary.printOutput(ChronologerException.taskDoesNotExist());
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }
        Task task = tasks.getTasks().get(indexOfTask);
        task.setPriority(newPriority);
        storage.saveFile(tasks.getTasks());
        UiTemporary.printOutput("Got it! " + task.getDescription() + " priority level is now " + priorityString);
    }
}
