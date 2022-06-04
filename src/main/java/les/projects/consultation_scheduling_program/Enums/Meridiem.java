package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Meridiem {
    AM("AM"),
    PM("PM");

    public final String s;

    Meridiem(String s) {
        this.s = s;
    }

    public static ObservableList<Meridiem> getMeridiems() {
        return FXCollections.observableArrayList(values());
    }
}
