package les.projects.consultation_scheduling_program.Helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * This class is used to help compare dates
 * @author Leslie C. Bomar 3rd
 */
public class WeekComparator {

    /**
     * This method takes in two ZonedDateTime values and assesses if they belong to the same week. Unlike the JavaFX
     * standard methods, this method starts the week on Sunday.
     * @param suspectTime The time we are checking.
     * @param comparisonTime The time we are checking against.
     * @return Returns true if both dates are in the same week.
     */
    public static boolean isSameWeek(ZonedDateTime suspectTime, ZonedDateTime comparisonTime) {
        LocalDate start;
        LocalDate end;

        if(comparisonTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            start = comparisonTime.toLocalDate().minusDays(1);
        } else {
            start = comparisonTime.toLocalDate().minusDays(comparisonTime.getDayOfWeek().getValue() + 1);
        }

        end = start.plusDays(8);
        LocalDate suspect = suspectTime.toLocalDate();

        if(suspect.isAfter(start) && suspect.isBefore(end)) {
            return true;
        } else {
            return false;
        }
    }
}
