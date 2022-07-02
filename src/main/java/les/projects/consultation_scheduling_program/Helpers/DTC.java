package les.projects.consultation_scheduling_program.Helpers;

import java.sql.Timestamp;
import java.time.*;

/**
 * This class contains helper methods for managing dates, converting them between time zones, and converting types.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public abstract class DTC {

    /**
     * This method takes a ZonedDateTime and converts to the the system default time zone.
     * @param udt A ZonedDateTime object.
     * @return A ZonedDateTime object adjusted to the system default.
     */
    public static ZonedDateTime toLocal(ZonedDateTime udt) {
        return udt.withZoneSameInstant(ZoneId.systemDefault());
    }

    /**
     * This method intakes a ZonedDateTime object and converts it into a string formatted "HH:MM".
     * @param udt Any ZonedDateTime
     * @return String formatted "HH:MM".
     */
    public static String getTimeString(ZonedDateTime udt) {
        ZonedDateTime toLocal = toLocal(udt);
        return toLocal.getHour() + ":" + toLocal.getMinute();
    }

    /**
     * This method intakes a ZonedDateTime and converts it to a LocalDate.
     * @param udt Any ZonedDateTime
     * @return The date portion of the ZonedDateTime.
     */
    public static LocalDate getDate(ZonedDateTime udt) {
        return udt.toLocalDate();
    }

    /**
     * This method returns the system default time zone as a string.
     * @return The system default time zone as a string.
     */
    public static String getLocalTimeZone() {
        return ZoneId.systemDefault().toString();
    }

    /**
     * This method intakes a Timestamp and converts it to the system default time zone and returns a ZonedDateTime.
     * @param timestamp A Timestamp object set to the UST time zone.
     * @return A ZonedDateTime set to the system default time zone.
     */
    public static ZonedDateTime timestampToZonedDateTime(Timestamp timestamp) {
        //The timestamp on the database is the same as the virtual machine.
        //The value converted to UTC should be 7 hours less than system time.

        //We must assume that the timestamp from the database is set to UTC.
        Instant databaseDateTime = timestamp.toInstant();

        //We must take that time and add the UTC timezone.
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(databaseDateTime, ZoneId.of("UTC"));

        //We can now adjust the ZonedDateTime from UTC to the systemDefault() timezone.
        ZonedDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localDateTime;
    }

    /**
     * This method intakes a ZonedDateTime and converts it unadjusted to a Timestamp.
     * We do not need to convert timezones the database does this automatically.
     * @param zdt Any ZonedDateTime.
     * @return A Timestamp set to UST time zone.
     */
    public static Timestamp zonedDateTimeToTimestamp(ZonedDateTime zdt) {
        return Timestamp.valueOf(zdt.toLocalDateTime());
    }

    /**
     * This method takes the system date and time and returns a Timestamp.
     * @return The system date and time.
     */
    public static Timestamp currentTimestamp() {
        //Return as time stamp
        return Timestamp.from(Instant.now());
    }
}
