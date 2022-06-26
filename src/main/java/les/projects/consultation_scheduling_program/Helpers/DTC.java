package les.projects.consultation_scheduling_program.Helpers;

import java.sql.Timestamp;
import java.time.*;

public abstract class DTC {

    public static ZonedDateTime toLocal(ZonedDateTime udt) {
        return udt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static String getTimeString(ZonedDateTime udt) {
        ZonedDateTime toLocal = toLocal(udt);
        return toLocal.getHour() + ":" + toLocal.getMinute();
    }

    public static LocalDate getDate(ZonedDateTime udt) {
        return udt.toLocalDate();
    }

    public static String getLocalTimeZone() {
        return ZoneId.systemDefault().toString();
    }

    public static ZonedDateTime timestampToZonedDateTime(Timestamp timestamp) {
        //Create UTC ZonedDateTime
        ZonedDateTime utcZDT = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.of("UTC"));
        //Convert to the system zone and return
        return ZonedDateTime.ofInstant(utcZDT.toInstant(), ZoneId.systemDefault());
    }

    public static Timestamp zonedDateTimeToTimestamp(ZonedDateTime zdt) {
        //Convert current time zone to UTC
        ZonedDateTime zdtUTC = ZonedDateTime.ofInstant(zdt.toInstant(), ZoneId.of("UTC"));
        //Convert ZonedDateTime to TimeStamp
        return Timestamp.from(Instant.from(zdtUTC));
    }

    public static Timestamp currentTimestamp() {
        //Capture the current time
        ZonedDateTime now = ZonedDateTime.now();

        //Convert to UTC time zone
        ZonedDateTime nowUTC = ZonedDateTime.ofInstant(now.toInstant(), ZoneId.of("UTC"));

        //Return as time stamp
        return Timestamp.from(nowUTC.toInstant());
    }
}
