package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import les.projects.consultation_scheduling_program.Enums.Hour;
import les.projects.consultation_scheduling_program.Enums.Meridiem;
import les.projects.consultation_scheduling_program.Enums.Minute;
import les.projects.consultation_scheduling_program.Enums.Styles;

import java.time.*;
import java.util.TimeZone;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class DateTimePickerLabeled extends BorderPane {
    private final Label label;
    private final DatePickerPane datePicker = new DatePickerPane();
    private final ComboBoxBorderPane hourPicker = new ComboBoxBorderPane(Hour.getHours(), lrb.getString("h"), true);
    private final ComboBoxBorderPane minutePicker = new ComboBoxBorderPane(Minute.getMinutes(), lrb.getString("m"), true);
    private final ComboBoxBorderPane meridiemPicker = new ComboBoxBorderPane(Meridiem.getMeridiems(), lrb.getString("am_pm"), true);
    private ZonedDateTime initValue;
    private boolean changed;

    public DateTimePickerLabeled(String labelText) {
        this.label = new Label(labelText);
        this.label.setFont(Styles.DefaultFont18);
        this.label.setPadding(new Insets(10,0,10,0));
        this.setLeft(this.label);

        HBox picker = new HBox();
            picker.getChildren().addAll(datePicker, hourPicker, minutePicker, meridiemPicker);
        this.setRight(picker);

        //Format the fields
        this.datePicker.setPrefWidth(115);
        this.hourPicker.setComboBoxWidth(60);
        this.minutePicker.setComboBoxWidth(60);
        this.meridiemPicker.setComboBoxWidth(60);
    }

    public ZonedDateTime getInput() {
        LocalDate date = this.datePicker.getValue();
        LocalTime time = LocalTime.of(this.hourPicker.getValue(), this.minutePicker.getValue());
        return ZonedDateTime.of(date,time, TimeZone.getDefault().toZoneId());
    }

    public void setValue(ZonedDateTime zTime) {
        this.datePicker.setValue(zTime.toLocalDate());
        this.hourPicker.setInitialValue(Hour.getObjById(zTime.toLocalTime().getHour()));
        this.minutePicker.setInitialValue(Minute.getObjById(zTime.toLocalTime().getMinute()));
        if(zTime.toLocalTime().compareTo(LocalTime.NOON) > 0) {
            //Time is PM
            this.meridiemPicker.setValue("PM");
        } else {
            this.meridiemPicker.setValue("AM");
        }
    }

    public void setInitialValue(ZonedDateTime zTime) {
        this.initValue = zTime;
        this.datePicker.setValue(zTime.toLocalDate());
        this.hourPicker.setInitialValue(Hour.getObjById(zTime.toLocalTime().getHour()));
        this.minutePicker.setInitialValue(Minute.getObjById(zTime.toLocalTime().getMinute()));
        if(zTime.toLocalTime().compareTo(LocalTime.NOON) > 0) {
            //Time is PM
            this.meridiemPicker.setValue("PM");
        } else {
            this.meridiemPicker.setValue("AM");
        }
    }
}
