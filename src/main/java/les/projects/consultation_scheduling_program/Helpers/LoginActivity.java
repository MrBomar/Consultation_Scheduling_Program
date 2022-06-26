package les.projects.consultation_scheduling_program.Helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class LoginActivity {

    public static void loginAttempt(String userName, Boolean successful) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.now();
            ZonedDateTime dateTimeUTC = dateTime.withZoneSameInstant(ZoneId.of("UTC"));
            String fullLine = new String(new char[122]).replace('\0', ' ');
            StringBuilder newLine = new StringBuilder(fullLine);

            newLine.insert(0, "| " + userName);
            newLine.insert(40, "| " + dateTimeUTC);
            newLine.insert(80, "| " + successful);
            newLine.insert(120, "|\n");

            String data = newLine.substring(0, 122);

            File file = new File("login_activity.txt");
            if(!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getName(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                String border = new String(new char[121]).replace('\0', '-');
                String labels = "| Username                              | Timestamp                             | Successful                            |";
                bw.write(border + "\n");
                bw.write(labels + "\n");
                bw.write(border + "\n");
                bw.close();
            }

            FileWriter fw = new FileWriter(file.getName(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
