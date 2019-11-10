package chronologer.exception;

import chronologer.ui.UiMessageHandler;

/**
 * This Exception class is used to handle all of the expected exceptions and
 * certain unexpected exceptions to give the user a better understanding of why
 * the program crashed.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ChronologerException extends Exception {
    private static final String WRONG_FORMAT_MSG = "OOPS!!! The date or time of this command is not of the "
        + "correct format.";
    private static final String TASK_CLASH_MSG = "OOPS!!! There is already a task scheduled at the same time, use view "
        + "command to check your schedule for the day";
    private static final String MISSING_DESCRIPTION_MSG = "OOPS!!! The description of the command is missing.";
    private static final String UNKNOWN_COMMAND_MSG = "OOPS!!! The command you have entered is not of a valid type.";
    private static final String MISSING_DATETIME_MSG = "OOPS!!! The date or time of this command is missing.";
    private static final String NONEXISTING_TASK_MSG = "OOPS!!! The task you searched for does not exist.";
    private static final String WRITE_ERROR_MSG = "OOPS!!! Unable to write file.";
    private static final String READ_ERROR_MSG = "OOPS!!! Unable to read from file.";
    private static final String NONEXISTING_CLASS_MSG = "OOPS!!! Unable to extract certain features of the Duke Project"
        + " Please ensure the project was imported properly";
    private static final String MISSING_FILE_MSG = "OOPS!!! Unable to read from previous task list. "
        + "A new file has been created for you";
    private static final String MISSING_PRIORITY_MSG = "OOPS!!! The new priority level is missing. "
        + "Please try typing the command again.";
    private static final String INAVLID_PRIORITY_MSG = "OOPS!!! The new priority level is invalid. "
        + "It must be either high,medium or low.Please try again";
    private static final String INVALID_INDEX_MSG = "OOPS!!! The index given is invalid. "
        + "It must be a existing index in the list. Please try again";
    private static final String INVALID_LOCATION_MSG = "OOPS!!! The location portion is not provided. Please try again";
    private static final String MISSING_COMMENT_MSG = "OOPS!!! The comment section is empty. Please try again";
    private static final String CALENDAR_WRITE_ERROR = " OOPS!!! Unable to write calendar file.";
    private static final String MISSING_MODULE_MSG = " OOPS!!! The needed module code is missing or invalid.";
    private static final String EMPTY_EXPORT_MSG = "OOPS!! There are no tasks to export with!";
    private static final String EMPTY_CALENDAR = "OOPS!! Your list only have dateless tasks and they can't be exported";
    private static final String EARLY_DATE_MSG = "OOPS!! The date you are postponing to is earlier than the old date";
    private static final String END_DATE_EARLIER_MSG = "OOPS!! Your end date is earlier than your start date";
    private static final String INVALID_WEEK = "OOPS!! Your week is invalid!";
    private static final String INVALID_VERSION = "OOPS!! Your version is invalid!";
    private static final String INVALID_DURATION = "OOPS!! The duration given is invalid!";
    private static final String WEBLINK_BROKEN = "OOPS!! We are unable to launch our online guide!";
    private static final String INVALID_INPUT = "OOPS!! The input given is not valid.";


    /**
     * Handles wrong date or time errors.
     *
     * @return message to be displayed
     */
    public static String wrongDateOrTime() {
        return WRONG_FORMAT_MSG;
    }

    /**
     * Handles if a new task clashes with a existing task.
     *
     * @return message to be displayed
     */
    public static String taskClash() {
        return TASK_CLASH_MSG;
    }

    /**
     * Handles empty task description errors.
     *
     * @return message to be displayed
     */
    public static String emptyUserDescription() {
        return MISSING_DESCRIPTION_MSG;
    }

    /**
     * Handles when parser does not understand input.
     *
     * @return message to be displayed
     */
    public static String unknownUserCommand() {
        return UNKNOWN_COMMAND_MSG;
    }

    /**
     * Handles empty date or time errors.
     *
     * @return message to be displayed
     */
    public static String emptyDateOrTime() {
        return MISSING_DATETIME_MSG;
    }

    /**
     * Handles if task searched does not exist.
     *
     * @return message to be displayed
     */
    public static String taskDoesNotExist() {
        return NONEXISTING_TASK_MSG;
    }

    /**
     * Handles if program is unable to save the tasks list to file.
     *
     * @return message to be displayed
     */
    public static String unableToWriteFile() {
        return WRITE_ERROR_MSG;
    }

    /**
     * Handles if program is unable to read an existing file for tasks list.
     *
     * @return message to be displayed
     */
    public static String unableToReadFile() {
        return READ_ERROR_MSG;
    }

    /**
     * Handles if class does not exists.
     *
     * @return message to be displayed
     */
    public static String classDoesNotExist() {
        return NONEXISTING_CLASS_MSG;
    }

    /**
     * Handles if file does not exists.
     *
     * @return message to be displayed
     */
    public static String fileDoesNotExist() {
        return MISSING_FILE_MSG;
    }

    /**
     * Handles if priority level is missing from priority command.
     *
     * @return message to be displayed
     */
    public static String emptyPriorityLevel() {
        return MISSING_PRIORITY_MSG;
    }

    /**
     * Handles if priority level is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidPriorityLevel() {
        return INAVLID_PRIORITY_MSG;
    }

    /**
     * Handles if index is invalid.
     *
     * @return message to be displayed
     */
    public static String invalidIndex() {
        return INVALID_INDEX_MSG;
    }

    /**
     * notifies user if location is not provided.
     *
     * @return message to be displayed
     */
    public static String invalidLocation() {
        return INVALID_LOCATION_MSG;
    }

    /**
     * notifies user if comment is not provided.
     *
     * @return message to be displayed
     */
    public static String emptyComment() {
        return MISSING_COMMENT_MSG;
    }

    public static String errorWriteCalendar() {
        return CALENDAR_WRITE_ERROR;
    }

    public static String missingModuleCode() {
        return MISSING_MODULE_MSG;
    }

    public static String emptyExport() {
        return EMPTY_EXPORT_MSG;
    }

    public static String postponeDateError() {
        return EARLY_DATE_MSG;
    }

    public static String endDateError() {
        return END_DATE_EARLIER_MSG;
    }

    public static String invalidWeek() {
        return INVALID_WEEK;
    }

    public static String invalidVersion() {
        return INVALID_VERSION;
    }

    public static String emptyCalendar() {
        return EMPTY_CALENDAR;
    }

    public static String invalidDuration() {
        return INVALID_DURATION;
    }

    public static String websiteMissing() {
        return WEBLINK_BROKEN;
    }

    public static String invalidInput() {
        return INVALID_INPUT;
    }

    public ChronologerException(String message) {
        super(message);
        UiMessageHandler.outputMessage(message);
    }
}
