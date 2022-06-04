package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Hour {
    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Eleven(11),
    Twelve(12);

    public final int number;

    Hour(int number) {
        this.number = number;
    }

    public static ObservableList<Hour> getHours() {
        return FXCollections.observableArrayList(values());
    }
};

