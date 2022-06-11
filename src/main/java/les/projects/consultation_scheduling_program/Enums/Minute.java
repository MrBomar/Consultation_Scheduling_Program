package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.NoSuchElementException;

public enum Minute {
    Zero(0),
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
    Twelve(12),
    Thirteen(13),
    Fourteen(14),
    Fifteen(15),
    Sixteen(16),
    Seventeen(17),
    Eighteen(18),
    Nineteen(19),
    Twenty(20),
    TwentyOne(21),
    TwentyTwo(22),
    TwentyThree(23),
    TwentyFour(24),
    TwentyFive(25),
    TwentySix(26),
    TwentySeven(27),
    TwentyEight(28),
    TwentyNine(29),
    Thirty(30),
    ThirtyOne(31),
    ThirtyTwo(32),
    ThirtyThree(33),
    ThirtyFour(34),
    ThirtyFive(35),
    ThirtySix(36),
    ThirtySeven(37),
    ThirtyEight(38),
    ThirtyNine(39),
    Forty(40),
    FortyOne(41),
    FortyTwo(42),
    FortyThree(43),
    FortyFour(44),
    FortyFive(45),
    FortySix(46),
    FortySeven(47),
    FortyEight(48),
    FortyNine(49),
    Fifty(50),
    FiftyOne(51),
    FiftyTwo(52),
    FiftyThree(53),
    FiftyFour(54),
    FiftyFive(55),
    FiftySix(56),
    FiftySeven(57),
    FiftyEight(58),
    FiftyNine(59);

    public final int number;

    Minute(int number) {
        this.number = number;
    }

    public static ObservableList<Minute> getMinutes() {
        return FXCollections.observableArrayList(values());
    }

    @Override
    public String toString() {
        return "" + this.number;
    }

    public static Minute getObjById(int id) {
        try {
            return FXCollections.observableArrayList(values()).stream().filter(m -> m.number == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingMinuteRecord);
            dialog.showAndWait();
            return Minute.One;
        }
    }
}
