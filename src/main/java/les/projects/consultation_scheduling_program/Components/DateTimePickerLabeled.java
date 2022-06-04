package les.projects.consultation_scheduling_program.Components;

import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Hour;
import les.projects.consultation_scheduling_program.Enums.Meridiem;
import les.projects.consultation_scheduling_program.Enums.Minute;
import les.projects.consultation_scheduling_program.Enums.Styles;

import java.time.*;
import java.util.TimeZone;

public class DateTimePickerLabeled extends BorderPane {
    private final Label label;
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private final ComboBoxSelector hourPicker = new ComboBoxSelector(Hour.getHours());
    private final ComboBoxSelector minutePicker = new ComboBoxSelector(Minute.getMinutes());
    private final ComboBoxSelector meridiemPicker = new ComboBoxSelector(Meridiem.getMeridiems());

    public DateTimePickerLabeled(String labelText) {
        this.label = new Label(labelText);
        this.label.setFont(Styles.DefaultFont18);
        this.setLeft(this.label);

        HBox picker = new HBox();
            Text t1 = new Text("H:");
            Text t2 = new Text(" M:");
            picker.getChildren().addAll(datePicker, t1, hourPicker, t2, minutePicker, meridiemPicker);
        this.setRight(picker);
    }

    public ZonedDateTime getInput() {
        LocalDate date = this.datePicker.getValue();
        LocalTime time = LocalTime.of((int) hourPicker.getValue(),(int) minutePicker.getValue());
        return ZonedDateTime.of(date,time, TimeZone.getDefault().toZoneId());
    }
}
