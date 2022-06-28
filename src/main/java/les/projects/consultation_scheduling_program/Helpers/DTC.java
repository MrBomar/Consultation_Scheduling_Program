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
        //Create UTC ZonedDateTime
        ZonedDateTime utcZDT = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.of("UTC"));
        //Convert to the system zone and return
        return ZonedDateTime.ofInstant(utcZDT.toInstant(), ZoneId.systemDefault());
    }

    /**
     * This method intakes a ZonedDateTime and converts it to UST time zone and returns a Timestamp.
     * @param zdt Any ZonedDateTime.
     * @return A Timestamp set to UST time zone.
     */
    public static Timestamp zonedDateTimeToTimestamp(ZonedDateTime zdt) {
        //Convert current time zone to UTC
        ZonedDateTime zdtUTC = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("UTC"));
        //Convert ZonedDateTime to TimeStamp
        return Timestamp.from(Instant.from(zdtUTC));
    }

    /**
     * This method take the system date and time, adjusts it to UST, and returns a Timestamp.
     * @return The system date and time, adjusted to UST.
     */
    public static Timestamp currentTimestamp() {
        //Capture the current time
        ZonedDateTime now = ZonedDateTime.now();

        //Convert to UTC time zone
        ZonedDateTime nowUTC = ZonedDateTime.ofInstant(now.toInstant(), ZoneId.of("UTC"));

        //Return as time stamp
        return Timestamp.from(nowUTC.toInstant());
    }
}
