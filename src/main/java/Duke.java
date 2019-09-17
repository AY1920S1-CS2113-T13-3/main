import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

import command.*;
import exception.*;//DukeException;
import ui.*;
import storage.*;//Storage;
import task.*;//TaskList;
import parser.*;//Parser;

/**
 * The main project class. Initializes a simple task list manager 'Duke' which
 * helps users curate and manage a task list.
 *
 * @author Sai Ganesh Suresh
 * @version v3.0
 */

public class Duke {

    private String filePath = System.getProperty("user.dir") + "/src/DukeDatabase/ArrayList";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private File file = new File(filePath);
    private boolean isExit = false;

    public static void main(String[] args) throws ParseException {
        new Duke().run();
    }

    /**
     * This constructor creates a new instance of vital classes and also loads tasks
     * if any from persistent storage.
     */

    public Duke() {
        ui = new Ui();

        try {
            storage = new Storage(this.filePath, file);
            tasks = new TaskList(storage.loadFile(file));
        } catch (DukeException e) {
            tasks = new TaskList(new ArrayList<>());
            Ui.printMessage(e.getMessage());
        }
    }

    /**
     * This method runs the main program.
     * 
     * @throws ParseException
     */

    public void run() throws ParseException {
        Ui.printGreeting();

        do {
            String userInput = Ui.readInput();
            try {
                Command command = Parser.parse(userInput);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            }
            catch (DukeException e)
            {
               Ui.printMessage(e.getMessage());
            }
        } while (!isExit);
    }

}
