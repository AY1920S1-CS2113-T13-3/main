package chronologer.command;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

//@@author hanskw4267
/**
 * Adds weekly recurring tasks to taskslist.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class AddRecurringCommand extends AddCommand {

    protected DayOfWeek dayToAdd;

    /**
     * Contructs a new "add recurring command".
     * 
     * @param command         command-type of input
     * @param taskDescription description of task
     * @param startDate       start date of task
     * @param endDate         end date of task
     * @param modCode         module code of task
     */
    public AddRecurringCommand(String command, String taskDescription, LocalDateTime startDate, LocalDateTime endDate,
            String modCode, DayOfWeek day) {
        super(command, taskDescription, startDate, endDate, modCode);
        this.dayToAdd = day;
    }

    @Override
    /**
     * Adds tasks till end of semester at specified timeslots.
     */
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        Task task;
        LocalDateTime timeNow = LocalDateTime.now();
        while (this.formattedStartDate.isAfter(timeNow)) {
            if (formattedStartDate.getDayOfWeek().equals(this.dayToAdd)) {
                task = new Event(taskDescription, formattedStartDate, formattedEndDate, modCode);
                tasks.add(task);
                this.formattedEndDate = this.formattedEndDate.minusWeeks(1);
                this.formattedStartDate = this.formattedStartDate.minusWeeks(1);
            } else {
                this.formattedEndDate = this.formattedEndDate.minusDays(1);
                this.formattedStartDate = this.formattedStartDate.minusDays(1);
            }
        }
        history.addState(tasks.getTasks());
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage(
                "Got it! I've added this task" + "\nNow you have " + tasks.getSize() + " task(s) in the list.");
    }
}