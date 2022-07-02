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
import static les.projects.consultation_scheduling_program.Main.lrb;
import java.time.*;

/**
 * This class renders a BorderPane containing a DatePicker and ComboBoxes for selecting hour, minute, and meridiem.
 * This class is designed to work with ZonedDateTime values set to the user's systemDefault().
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class DateTimePicker extends BorderPane {
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox<Hour> hourPicker = new ComboBox<>(Hour.getHours());
    private final ComboBox<Minute> minutePicker = new ComboBox<>(Minute.getMinutes());
    private final ComboBox<Meridiem> meridiemPicker = new ComboBox<>(Meridiem.getMeridiems());
    private LocalDate ld;
    private LocalTime lt;
    private ZonedDateTime initialValue;

    /**
     * Produces a BorderPane containing a DatePicker and ComboBoxes for building a ZonedDateTime object.
     * @param labelText String to apply to DateTimePicker label.
     */
    public DateTimePicker(String labelText, boolean required) {
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
        this.datePicker.setStyle(Styles.StyleComboBoxRequired);
        this.hourPicker.setStyle(Styles.StyleComboBox);
        this.minutePicker.setStyle(Styles.StyleComboBox);
        this.meridiemPicker.setStyle(Styles.StyleComboBox);

        //Format Border Pane
        this.setPadding(new Insets(10,0,10,0));

        //Required property
        if(required) {
            this.datePicker.setPromptText("(" + lrb.getString("Required") + ")");
        }
    }

    /**
     * This method attempts to create a date instance and a time instance from the user input.
     * @return Successful conversion returns 'true'.
     */
    public boolean validEntry() {
        if(this.datePicker.getValue() == null ||
                this.hourPicker.getValue() == null ||
                this.minutePicker.getValue() == null ||
                this.meridiemPicker.getValue() == null) return false;

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
        return ZonedDateTime.of(this.ld, this.lt, ZoneId.systemDefault());
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

       this.hourPicker.getItems().stream().filter(h -> h.number == hour2).findFirst().ifPresent(this.hourPicker::setValue);
       this.minutePicker.getItems().stream().filter(m -> m.number == this.initialValue.getMinute()).findFirst().ifPresent(this.minutePicker::setValue);

        Meridiem meridiem = (hour1 > 11) ? Meridiem.PM : Meridiem.AM;
        this.meridiemPicker.setValue(meridiem);
    }

    /**
     * Method is used to detect if the value of the DateTimePicker has been changed.
     * @return Returns true of the value has been changed.
     */
    public boolean isChanged() {
        if(this.validEntry() && this.initialValue != null) {
            return (this.getEntry().getMonth() != this.initialValue.getMonth() ||
                    this.getEntry().getDayOfMonth() != this.initialValue.getDayOfMonth() ||
                    this.getEntry().getYear() != this.initialValue.getYear() ||
                    this.getEntry().getHour() != this.initialValue.getHour() ||
                    this.getEntry().getMinute() != this.initialValue.getMinute());
        } else if (this.initialValue == null && this.ld == null && this.lt == null) {
            return false;
        } else {
            return true;
        }
    }
}
