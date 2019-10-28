package chronologer.task;

import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The TaskList class handles all operations performed on the TaskList as well
 * as stores the TaskList.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class TaskList {

    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String TODO_DURATION = "TODO DURATION";
    private static final String TODO_PERIOD = "TODO PERIOD";

    private ArrayList<Task> listOfTasks;
    private ObservableList<Task> observableListOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
        this.observableListOfTasks = FXCollections.observableArrayList(listOfTasks);
    }

    /**
     * This custom comparator allows the sorting of both deadlines and events.
     */
    private static final Comparator<Task> DateComparator = (firstDate, secondDate) -> {
        if (firstDate.startDate == null && secondDate != null) {
            return -1;
        } else if (firstDate.startDate != null && secondDate.startDate == null) {
            return 1;
        }
        assert firstDate.startDate != null;
        if (firstDate.startDate.isBefore(secondDate.startDate)) {
            return -1;
        } else if (firstDate.startDate.isEqual(secondDate.startDate)) {
            return 0;
        } else {
            return 1;
        }
    };

    /**
     * This custom comparator allows the sorting of both deadlines and events.
     *
     * @param task contains the task that needs to be added.
     */
    public static final Comparator<Task> PriorityComparator = (firstPriority, secondPriority) -> {
        if (firstPriority.priority.equals(Priority.HIGH) && secondPriority.priority.equals(Priority.MEDIUM)) {
            return -1;
        } else if (firstPriority.priority.equals(Priority.MEDIUM) && secondPriority.priority.equals(Priority.MEDIUM)) {
            return 1;
        } else {
            return 0;
        }
    };

    /**
     * This function allows the use to add a particular task.
     *
     * @param task contains the task that needs to be added.
     */
    public void add(Task task) {
        listOfTasks.add(task);
        observableListOfTasks.add(task);
    }

    /**
     * This function allows the use to delete a particular task.
     *
     * @param indexOfTask this is the index of the task which needs to be deleted.
     */
    public Task delete(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        listOfTasks.remove(task);
        observableListOfTasks.remove(task);
        return task;
    }

    /**
     * This function allows the user to find tasks with a particular keyword.
     *
     * @param keyWord this string contains the keyword the user is searching for.
     */
    public ArrayList<Task> find(String keyWord) {
        ArrayList<Task> holdFoundTasks = new ArrayList<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            String findMatch = listOfTasks.get(i).toString();
            if (findMatch.contains(keyWord)) {
                holdFoundTasks.add(listOfTasks.get(i));
            }
        }
        return holdFoundTasks;
    }

    /**
     * Performs a check as to determine if the task being added has a clash with
     * another task already scheduled.
     *
     * @param taskToCheck the task trying to be added by the user.
     * @return boolean true if there is a clash, false if there is not clash.
     */
    public boolean isClash(Task taskToCheck) {
        for (Task task : listOfTasks) {
            if (task.isClash(taskToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function allows the user to mark a particular task as done.
     *
     * @param indexOfTask this is the index of the task which needs to be marked as
     *                    done.
     */
    public Task markAsDone(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setDone(true);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * Marks a task to be ignored and have reminders to stop showing up for the
     * task.
     *
     * @param indexOfTask The index of the task to be marked
     * @return The marked task
     */
    public Task markAsIgnorable(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setIgnored(true);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * Marks a task to no longer be ignored and have reminders to show up again.
     *
     * @param indexOfTask The index of the task to be marked
     * @return The marked task
     */
    public Task markAsUnignorable(int indexOfTask) {
        Task task = listOfTasks.get(indexOfTask);
        task.setIgnored(false);
        observableListOfTasks.add(task);
        return task;
    }

    /**
     * This function allows the user to add a location to tasks.
     *
     * @param taskWithLocation is of String type which contains the desired date of
     *                         schedule.
     * @return sortDateList the sorted schedule of all the tasks on a particular
     *         date.
     */
    public Task addLocation(Integer indexOfTask, String taskWithLocation) {
        Task taskHasLocation = listOfTasks.get(indexOfTask);
        taskHasLocation.setLocation("Location of the task is " + taskWithLocation);
        return taskHasLocation;

    }

    /**
     * This function allows the user to obtain the tasks on a particular date.
     *
     * @param dayToFind is of String type which contains the desired date of
     *                  schedule.
     * @return sortDateList the sorted schedule of all the tasks on a particular
     *         ate.
     */
    public ArrayList<Task> schedule(String dayToFind) {
        ArrayList<Task> sortedDateList = new ArrayList<Task>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            if (listOfTasks.get(i).toString().contains(dayToFind)) {
                sortedDateList.add(listOfTasks.get(i));
            }
        }
        Collections.sort(sortedDateList, DateComparator);
        return sortedDateList;
    }

    /**
     * This function allows the user to obtain the tasks on a particular date sorted
     * by priority.
     *
     * @param dayToFind is of String type which contains the desired date of
     *                  schedule.
     * @return priorityList the tasks of the given day sorted by priority
     */
    public ArrayList<String> obtainPriorityList(String dayToFind) {
        ArrayList<Task> priorityList = new ArrayList<>();
        ArrayList<String> stringPriorityList = new ArrayList<>();
        for (Task listOfTask : listOfTasks) {
            if (listOfTask.toString().contains(dayToFind)
                && !listOfTask.priority.equals(Priority.LOW)) {
                priorityList.add(listOfTask);
            }
        }

        priorityList.sort(PriorityComparator);
        for (int i = 0; i < priorityList.size(); i++) {
            stringPriorityList.add(priorityList.get(i).toString());
        }
        return stringPriorityList;
    }

    /**
     * Fetches all tasks without dates.
     *
     * @return tasksWithoutDates tasks with no time constraint.
     */
    public ArrayList<String> obtainTasksWithoutDates() {
        ArrayList<String> tasksWithoutDates = new ArrayList<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            if (listOfTasks.get(i).startDate == null) {
                tasksWithoutDates.add(listOfTasks.get(i).getDescription());
            }
        }
        return tasksWithoutDates;
    }

    //@@author fauzt-reused
    /**
     * Retrieves all Event tasks in the main task list in chronologically-ordered list.
     * @param deadlineDate is the cut-off time to search all prior relevant events
     * @return all the events in the main task list in chronological order
     */
    public ArrayList<Event> obtainEventList(LocalDateTime deadlineDate) {
        ArrayList<Event> eventList = new ArrayList<>();
        for (Task item : listOfTasks) {
            boolean isAnEventBeforeDeadline = item.getClass() == Event.class
                    && item.getStartDate().isBefore(deadlineDate);
            if (isAnEventBeforeDeadline) {
                eventList.add((Event) item);
            }
        }
        Collections.sort(eventList);

        return eventList;
    }

    //@@author
    /**
     * This function allows the user to obtain the tasks on a particular date, but
     * only with description.
     *
     * @param dayToFind is of String type which contains the desired date of
     *                  schedule.
     * @return sortDateList which contains only the descriptions of the tasks.
     */
    public ArrayList<String> scheduleForDay(String dayToFind) {
        ArrayList<Task> obtainDescriptions = schedule(dayToFind);
        ArrayList<String> scheduleDescriptionOnly = new ArrayList<>();
        for (int i = 0; i < obtainDescriptions.size(); i++) {
            if (obtainDescriptions.get(i).toString().contains(dayToFind)) {
                scheduleDescriptionOnly.add(obtainDescriptions.get(i).getDescription().trim());
            }
        }
        return scheduleDescriptionOnly;
    }

    /**
     * This function allows the user to edit the task description.
     *
     * @param indexOfTask    Location of task in the list
     * @param newDescription The new task description to be updated
     * @return taskToBeEdited The task that had its description edited
     */
    public Task editTaskDescription(int indexOfTask, String newDescription) {
        Task taskToBeEdited = listOfTasks.get(indexOfTask);
        taskToBeEdited.setDescription(newDescription);
        observableListOfTasks.add(taskToBeEdited);
        return taskToBeEdited;
    }

    /**
     * Function to allow user to edit/add comments to existing tasks.
     *
     * @param indexOfTask Index of task in list
     * @param comment     commnent to be added/edited
     * @return taskToBeEdited The task that has its comment edited/added
     */
    public Task editTaskComment(int indexOfTask, String comment) {
        Task taskToBeEdited = listOfTasks.get(indexOfTask);
        taskToBeEdited.setComment(comment);
        observableListOfTasks.add(taskToBeEdited);
        return taskToBeEdited;
    }

    public boolean isDeadline(Task task) {
        return (DEADLINE.equals(task.getType()));
    }

    public boolean isEvent(Task task) {
        return (EVENT.equals(task.getType()));
    }

    public boolean isTodoDuration(Task task) {
        return (TODO_DURATION.equals(task.getType()));
    }

    public boolean isTodoPeriod(Task task) {
        return (TODO_PERIOD.equals(task.getType()));
    }


    public void updatePriority(Task task) {
        observableListOfTasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return listOfTasks;
    }

    public ObservableList<Task> getObservableListOfTasks() {
        return observableListOfTasks;
    }

    public int getSize() {
        return listOfTasks.size();
    }

}