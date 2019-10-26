package parser;

import command.Command;
import command.RemindCommand;
import exception.DukeException;

/**
 * Extract the components required for the remind command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class RemindParser extends IndexParser {

    public RemindParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.IN.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        int days = extractDays(taskFeatures);

        return new RemindCommand(indexOfTask, days);
    }

    private int extractDays(String taskFeatures) throws DukeException {
        int days;
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        String daysString = substring.split("\\s+", 2)[0].trim();
        try {
            days = Integer.parseInt(daysString);
        } catch (NumberFormatException e) {
            writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new DukeException(DukeException.unknownUserCommand());
        }

        return days;
    }
}
