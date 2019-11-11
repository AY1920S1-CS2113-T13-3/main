package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Allows the user to delete a particular task from their task list based on
 * index.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class ManualCommand extends Command {

    private static final String UG_URI = "https://github.com/AY1920S1-CS2113-T13-3/main/blob/master/docs/UserGuide.adoc";

    /**
     * Removes the task from the TaskList and saves the updated TaskList to
     * persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(UG_URI));
        } catch (URISyntaxException | IOException e) {
            logger.writeLog(e.toString(), this.getClass().getName());
            throw new ChronologerException(ChronologerException.websiteMissing());
        }

    }
}