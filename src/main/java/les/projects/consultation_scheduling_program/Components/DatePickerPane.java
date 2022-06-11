package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

import java.time.LocalDate;

public class DatePickerPane extends BorderPane {
    private DatePicker datePicker;
    private LocalDate initValue;
    private boolean changed = false;

    public DatePickerPane() {
        this.datePicker = new DatePicker();
        this.datePicker.setBorder(Styles.ButtonBorder);
        this.setPadding(new Insets(10,0,10,0));
        this.setCenter(this.datePicker);
        this.datePicker.setPromptText("DATE");

        //Event listeners
        if(initValue != null) {
            this.datePicker.focusedProperty().addListener(
                    (x, y, z) -> changed = !initValue.equals(datePicker.getValue())
            );
        }
    }

    public LocalDate getValue() {
        return this.datePicker.getValue();
    }

    public void setPrefWidth(int i) {
        this.datePicker.setPrefWidth(i);
    }

    public void setValue(LocalDate date) {
        this.changed = true;
        this.datePicker.setValue(date);
    }

    public void setInitialValue(LocalDate localDate) {
        this.initValue = localDate;
        this.datePicker.setValue(localDate);
    }

    public boolean isChanged() { return changed; }
}
