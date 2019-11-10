package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.TaskScheduleCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Extract the components required for the schedule command from the user input.
 *
 * @author Fauzan Adipratama
 * @version v1.3
 */
public class ScheduleParser extends IndexParser {

    private static final int INDEX_INPUT = 0;
    private static final int DATE_INPUT = 1;
    private static final int NO_DEADLINE_INPUT = 2;

    ScheduleParser(String userInput, String command) {
        super(userInput, command);
    }


    /**
     * Parses the inputted text into its components and check whether the last inputted component is an index number
     * or a date.
     * @return the command to execute to schedule a task's duration by the selected Deadline or a custom deadline date
     * @throws ChronologerException if the inputted text does not match the expected format
     */
    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        if (isProcessingRawDuration(taskFeatures)) {
            int deadlineType = checkInputType(taskFeatures);
            Long duration = getRawDuration();
            return getTaskScheduleCommandForDuration(deadlineType, duration);
        }

        int deadlineType = checkInputType(taskFeatures);
        return getTaskScheduleCommandForIndex(deadlineType);
    }

    private int extractDeadlineIndex(String taskFeatures) throws ChronologerException {
        String extractedIndex = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        int convertedIndex;
        try {
            convertedIndex = Integer.parseInt(extractedIndex) - 1;
        } catch (NumberFormatException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.invalidInput());
        }

        return convertedIndex;
    }

    private LocalDateTime extractDeadlineDate(String taskFeatures) throws ChronologerException {
        String extractedDate = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        LocalDateTime convertedDate;
        try {
            convertedDate = DateTimeExtractor.extractDateTime(extractedDate);
        } catch (DateTimeParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }

        return convertedDate;
    }

    private int checkInputType(String taskFeatures) throws ChronologerException {
        if (taskFeatures.split("\\s+").length == 1) {
            return NO_DEADLINE_INPUT;
        }
        String stringToCheck = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        if (stringToCheck.isEmpty()) {
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
        if (stringToCheck.contains("/")) {
            return DATE_INPUT;
        }
        return INDEX_INPUT;
    }

    private boolean isProcessingRawDuration(String taskFeatures) {
        String[] tokens = taskFeatures.split(Flag.RAW.getFlag(), 2);
        if (tokens.length == 1) {
            return false;
        }
        this.taskFeatures = taskFeatures.replace(Flag.RAW.getFlag(), "");
        return true;
    }

    private Long getRawDuration() {
        return (long) indexOfTask + 1;
    }

    private Command getTaskScheduleCommandForDuration(int deadlineType, Long duration) throws ChronologerException {
        switch (deadlineType) {
            case INDEX_INPUT:
                int indexOfDeadline = extractDeadlineIndex(taskFeatures);
                return new TaskScheduleCommand(duration, indexOfDeadline);
            case DATE_INPUT:
                LocalDateTime dateOfDeadline = extractDeadlineDate(taskFeatures);
                return new TaskScheduleCommand(duration, dateOfDeadline);
            case NO_DEADLINE_INPUT:
                return new TaskScheduleCommand(duration, null);
            default:
                return null;
        }
    }

    private Command getTaskScheduleCommandForIndex(int deadlineType) throws ChronologerException {
        switch (deadlineType) {
            case INDEX_INPUT:
                int indexOfDeadline = extractDeadlineIndex(taskFeatures);
                return new TaskScheduleCommand(indexOfTask, indexOfDeadline);
            case DATE_INPUT:
                LocalDateTime dateOfDeadline = extractDeadlineDate(taskFeatures);
                return new TaskScheduleCommand(indexOfTask, dateOfDeadline);
            case NO_DEADLINE_INPUT:
                return new TaskScheduleCommand(indexOfTask, null);
            default:
                return null;
        }
    }
}
