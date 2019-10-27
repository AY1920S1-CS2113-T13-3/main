package chronologer.task;

import java.time.LocalDateTime;
import chronologer.parser.DateTimeExtractor;

/**
 * This extension of the Event class will allow the student to add Tests to
 * his schedule.
 *
 * @author Varun
 */
public class Test extends Event {

    public Test(String description, LocalDateTime toDate, LocalDateTime atDate) {
        super(description, toDate, atDate);
    }

    /**
     * This override of the toString function of the Event class etches the
     * different portions of the user input into a single string.
     *
     * @return This function returns a string of the required Test in the desired
     *         output format of string type.
     */
    @Override
    public String toString() {

        return "[TEST]" + "[" + super.getStatusIcon() + "]" + this.description + "(at: "
                + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + "-"
                + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }
}