package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Finds a free period of time within the user's schedule for a selected duration value.
 *
 * @author Fauzan Adipratama
 * @version 1.3
 */
public class TaskScheduleCommand extends Command {

    private Long durationToSchedule;
    private final int indexOfTask;
    private final Integer indexOfDeadline;
    private final LocalDateTime deadlineDate;

    /**
     * Initialises the command parameter for a selected task to be done by a selected deadline.
     * @param indexOfTask is the index number of the selected task in the TaskList
     * @param indexDeadline is the index number of the selected deadline in the TaskList
     */
    public TaskScheduleCommand(int indexOfTask, int indexDeadline) {
        this.indexOfTask = indexOfTask;
        this.indexOfDeadline = indexDeadline;
        this.deadlineDate = null;
    }

    /**
     * Initialises the command parameter for a selected task to be done by an inputted date.
     * @param indexOfTask is the index number of the selected task in the TaskList
     * @param deadlineDate is the date to schedule the task by
     */
    public TaskScheduleCommand(int indexOfTask, LocalDateTime deadlineDate) {
        this.indexOfTask = indexOfTask;
        this.deadlineDate = deadlineDate;
        this.indexOfDeadline = -1;
    }

    /**
     * Searches all free periods of time that the user can schedule a given task by a certain deadline.
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws ChronologerException if the selected task is not a compatible type.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        Todo todo;
        Deadline deadline;
        LocalDateTime deadlineDate;

        ArrayList<Task> list = tasks.getTasks();

        try {
            todo = (Todo) list.get(indexOfTask);
        } catch (ClassCastException e) {
            UiTemporary.printOutput("Task selected is not a Todo with a duration");
            throw new ChronologerException("Task selected is not a Todo with a duration");
        }
        durationToSchedule = (long) todo.duration;

        if (this.deadlineDate == null) {
            try {
                deadline = (Deadline) list.get(indexOfDeadline);
            } catch (ClassCastException e) {
                UiTemporary.printOutput("Task selected is not a Deadline");
                throw new ChronologerException("Task selected is not a Deadline");
            }
            deadlineDate = deadline.getStartDate();
        } else {
            deadlineDate = this.deadlineDate;
        }
        if (LocalDateTime.now().isAfter(deadlineDate)) {
            throw new ChronologerException("The selected deadline is overdue!");
        }

        ArrayList<Event> dateList = tasks.obtainEventList(deadlineDate);
        if (dateList.size() == 0) {
            UiTemporary.printOutput("You can schedule this task from now till the deadline.\n");
            return;
        }

        Long duration;
        LocalDateTime nextStartDate = dateList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            UiTemporary.printOutput("You can schedule this task from now till " + nextStartDate);
            return;
        }

        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < dateList.size(); i++) {
            LocalDateTime currentEndDate = dateList.get(i).getEndDate();
            if (i == dateList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = dateList.get(i + 1).getStartDate();
            }

            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                UiTemporary.printOutput("You can schedule this task from " + currentEndDate
                        + " till " + nextStartDate);
                break;
            }
        }

        if (!isFreeBetweenEvents) {
            UiTemporary.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }

    // TODO: Figure a way for GUI to accept subsequent inputs
    private boolean confirmSchedule(Task t, LocalDateTime start, long duration, TaskList tasks, Storage storage)
            throws ChronologerException {
        while (true) {
            String answer = UiTemporary.readInput().toLowerCase();
            if (answer.equals("y")) {
                String description = t.getDescription() + "(Recommended period)";
                LocalDateTime end = start.plusHours(duration);
                Command command = new AddCommand("todo", description, start, end);
                command.execute(tasks, storage);
                return true;
            }
            if (answer.equals("n")) {
                return false;
            }
            UiTemporary.printOutput("Not a valid input. Please answer as y/n\n");
        }
    }
}