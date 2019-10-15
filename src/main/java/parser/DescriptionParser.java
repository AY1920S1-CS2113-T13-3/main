package parser;

import command.Command;
import exception.DukeException;

public abstract class DescriptionParser extends Parse {

    String checkType;
    String taskDescription;

    public DescriptionParser(String userInput, String command) {
        super(userInput, command);
    }

    public abstract Command parse() throws DukeException;

    void extract() throws DukeException {
        this.taskFeatures = removeCommandInput(userInput);
        this.taskDescription = parseDetails(taskFeatures, checkType);
    }

    private String parseDetails(String taskFeatures, String checkType) throws DukeException {
        if (checkType == null) {
            return taskFeatures;
        }
        String description = taskFeatures.split(checkType,2)[0].trim();
        if (description.isEmpty()) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return description;
    }
}
