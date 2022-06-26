package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.NoSuchElementException;

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

    public static Hour getObjById(int id) {
        int value = (id > 12)? id - 12: id;
        try {
            return FXCollections.observableArrayList(values()).stream().filter(h -> h.number == value).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingHourRecord);
            dialog.showAndWait();
            return Hour.One;
        }
    }

    @Override
    public String toString() {
        return "" + this.number;
    }
}

