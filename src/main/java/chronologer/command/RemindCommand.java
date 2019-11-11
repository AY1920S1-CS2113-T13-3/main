package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

//@@author fauzt
/**
 * Adds a reminder to a task.
 *
 * @author Fauzan Adipratama
 * @version v1.3
 */
public class RemindCommand extends Command {
    private Integer indexOfTask;
    private Integer days;

    public RemindCommand(Integer indexOfTask, Integer days) {
        this.indexOfTask = indexOfTask;
        this.days = days;
    }

    /**
     * Creates a reminder for a task and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        if (!isIndexValid(indexOfTask,tasks.getSize())) {
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }

        Task task = tasks.getTasks().get(indexOfTask);
        task.setReminder(days);
        history.addState((tasks.getTasks()));
        storage.saveFile(tasks.getTasks());

        UiMessageHandler.outputMessage(String.format("Okay! You'll get a reminder for this task %d "
            + "days beforehand:", days) + "  " + task.toString());
    }
}
