package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

import java.time.LocalDate;

public class DatePickerPane extends BorderPane {
    private DatePicker datePicker = new DatePicker();

    public DatePickerPane(LocalDate lc) {
        this.datePicker.setValue(lc);
        this.datePicker.setBorder(Styles.ButtonBorder);
        this.setPadding(new Insets(10,0,10,0));
        this.setCenter(this.datePicker);
    }

    public LocalDate getValue() {
        return this.datePicker.getValue();
    }

    public void setPrefWidth(int i) {
        this.datePicker.setPrefWidth(i);
    }

    public void setValue(LocalDate date) {
        this.datePicker.setValue(date);
    }
}
