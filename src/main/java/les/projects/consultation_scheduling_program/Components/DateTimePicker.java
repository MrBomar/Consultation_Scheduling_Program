package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import les.projects.consultation_scheduling_program.Enums.Hour;
import les.projects.consultation_scheduling_program.Enums.Meridiem;
import les.projects.consultation_scheduling_program.Enums.Minute;
import les.projects.consultation_scheduling_program.Enums.Styles;
import java.time.*;

/**
 * This class renders a BorderPane containing a DatePicker and ComboBoxes for selecting hour, minute, and meridiem.
 * This class is designed to work with ZonedDateTime values set to the user's systemDefault().
 * @author Leslie C. Bomar 3rd
 */
public class DateTimePicker extends BorderPane {
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox<Hour> hourPicker = new ComboBox<>(Hour.getHours());
    private final ComboBox<Minute> minutePicker = new ComboBox<>(Minute.getMinutes());
    private final ComboBox<Meridiem> meridiemPicker = new ComboBox<>(Meridiem.getMeridiems());
    private LocalDate ld;
    private LocalTime lt;
    private ZonedDateTime initialValue;

    public DateTimePicker(String labelText) {
        HBox hBox = new HBox();
        Label label = new Label(labelText);
        label.setFont(Styles.DefaultFont18);
        this.setLeft(label);

        hBox.getChildren().addAll(
                this.datePicker,
                this.hourPicker,
                this.minutePicker,
                this.meridiemPicker
        );
        this.setRight(hBox);

        //Set field styles.
        this.datePicker.setPrefWidth(130);
        this.hourPicker.setPrefWidth(75);
        this.minutePicker.setPrefWidth(75);
        this.meridiemPicker.setPrefWidth(80);
        this.datePicker.setStyle(Styles.StyleComboBox);
        this.hourPicker.setStyle(Styles.StyleComboBox);
        this.minutePicker.setStyle(Styles.StyleComboBox);
        this.meridiemPicker.setStyle(Styles.StyleComboBox);

        //Format Border Pane
        this.setPadding(new Insets(10,0,10,0));
    }

    /**
     * This method attempts to create a date instance and a time instance from the user input.
     * @return Successful conversion returns 'true'.
     */
    public boolean validEntry() {
        try {
            this.ld = this.datePicker.getValue();
        } catch (Exception e) {
            return false;
        }

        try {
            int h = (this.meridiemPicker.getValue().equals(Meridiem.PM) && this.hourPicker.getValue().number < 12)? this.hourPicker.getValue().number + 12: this.hourPicker.getValue().number;
            this.lt = LocalTime.of(h, this.minutePicker.getValue().number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * This method takes the LocalDate and LocalTime created in validEntry() and converts it to a ZonedDateTime
     * @return Returns a ZonedDateTime set to the systemDefault zone.
     */
    public ZonedDateTime getEntry() {
        return ZonedDateTime.of(this.ld,this.lt, ZoneId.systemDefault());
    }

    /**
     * This method intakes a ZonedDateTime, converts it to the systemDefault Zone and stores it. The method also takes
     * that date and populates the date, hour, minute, and meridiem fields so that they reflect the current record.
     * @param zdt Requires a ZonedDateTime - can take any Zone and will convert it to the systemDefault Zone.
     */
    public void setInitialValue(ZonedDateTime zdt) {
        this.initialValue = zdt.withZoneSameInstant(ZoneId.systemDefault());
        this.datePicker.setValue(this.initialValue.toLocalDate());
        int hour1 = this.initialValue.getHour();
        int hour2 = (hour1 > 12) ? hour1 - 12 : hour1;
        this.hourPicker.setValue(this.hourPicker.getItems().stream().filter(h -> h.number == hour2).findFirst().get());
        this.minutePicker.setValue(this.minutePicker.getItems().stream().filter(m -> m.number == this.initialValue.getMinute()).findFirst().get());
        Meridiem meridiem = (hour1 > 11) ? Meridiem.PM : Meridiem.AM;
        this.meridiemPicker.setValue(meridiem);
    }

    public boolean isChanged() {
        if(this.validEntry()) {
            if(getEntry().getMonth() == this.initialValue.getMonth() &&
            getEntry().getDayOfMonth() == this.initialValue.getDayOfMonth() &&
            getEntry().getYear() == this.initialValue.getYear() &&
            getEntry().getHour() == this.initialValue.getHour() &&
            getEntry().getMinute() == this.initialValue.getMinute())
            return false;
        } else {
            return true;
        }
        return true;
    }

}
