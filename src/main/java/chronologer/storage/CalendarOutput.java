package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.exception.MyLogger;
import chronologer.ui.UiMessageHandler;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Outputs the ICS File created by export command.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class CalendarOutput {
    private static final String CLASS_NAME = "chronologer.storage.CalenderOutput";
    private static final String LOG_NAME = "StorageErrors";
    private static String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/";
    private static MyLogger logger = new MyLogger(CLASS_NAME, LOG_NAME);

    /**
     * Process the calendar into an ics file.
     *
     * @param fileName Name of the file
     * @param calendar Calendar to be processed
     * @throws ChronologerException If there are errors writing the ics file.
     */
    public static void outputCalendar(String fileName, Calendar calendar) throws ChronologerException {
        File icsFile = new File(filePath.concat(fileName).concat(".ics"));
        icsFile.getParentFile().mkdirs();

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(icsFile);
            CalendarOutputter calendarOutputter = new CalendarOutputter();
            calendarOutputter.output(calendar, fileOutputStream);
            UiMessageHandler.outputMessage("Success,ics file written at src/ChronologerDatabase/" + fileName);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            logger.writeLog(e.toString(), CLASS_NAME);
        } catch (IOException e) {
            logger.writeLog(e.toString(), CLASS_NAME);
            throw new ChronologerException(ChronologerException.errorWriteCalendar());
        }
    }

}
