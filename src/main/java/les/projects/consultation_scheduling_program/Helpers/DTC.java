package les.projects.consultation_scheduling_program.Helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DTC {
    public static ZonedDateTime toUniversal(ZonedDateTime ldt) {
        return ldt.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static ZonedDateTime toUniversal(LocalDate ld, LocalTime lt) {
        return ZonedDateTime.of(ld,lt,ZoneId.of("UTC"));
    }

    public static ZonedDateTime toLocal(ZonedDateTime udt) {
        return udt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static ZonedDateTime toLocal(LocalDate ld, LocalTime lt) {
        return ZonedDateTime.of(ld,lt,ZoneId.systemDefault());
    }

    public static int[] getTime(ZonedDateTime udt) {
        //Converts universal date time to local date time and returns the time as int[Hour,Minute]
        ZonedDateTime toLocal = toLocal(udt);
        return new int[] { toLocal.getHour(), toLocal.getMinute() };
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
}
