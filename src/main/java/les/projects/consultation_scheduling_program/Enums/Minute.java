package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of possible minute values.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public enum Minute {
    /**
     * The number zero.
     */
    Zero(0),

    /**
     * The number one.
     */
    One(1),

    /**
     * The number two.
     */
    Two(2),

    /**
     * The number three.
     */
    Three(3),

    /**
     * The number four.
     */
    Four(4),

    /**
     * The number five.
     */
    Five(5),

    /**
     * The number six.
     */
    Six(6),

    /**
     * The number seven.
     */
    Seven(7),

    /**
     * The number eight.
     */
    Eight(8),

    /**
     * The number nine.
     */
    Nine(9),

    /**
     * The number ten.
     */
    Ten(10),

    /**
     * The number eleven.
     */
    Eleven(11),

    /**
     * The number twelve.
     */
    Twelve(12),

    /**
     * The number thirteen.
     */
    Thirteen(13),

    /**
     * The number fourteen.
     */
    Fourteen(14),

    /**
     * The number fifteen.
     */
    Fifteen(15),

    /**
     * The number sixteen.
     */
    Sixteen(16),

    /**
     * The number seventeen.
     */
    Seventeen(17),

    /**
     * The number eighteen.
     */
    Eighteen(18),

    /**
     * The number nineteen.
     */
    Nineteen(19),

    /**
     * The number twenty.
     */
    Twenty(20),

    /**
     * The number twenty-one.
     */
    TwentyOne(21),

    /**
     * The number twenty-two.
     */
    TwentyTwo(22),

    /**
     * The number twenty-three.
     */
    TwentyThree(23),

    /**
     * The number twenty-four.
     */
    TwentyFour(24),

    /**
     * The number twenty-five.
     */
    TwentyFive(25),

    /**
     * The number twenty-six.
     */
    TwentySix(26),

    /**
     * The number twenty-seven.
     */
    TwentySeven(27),

    /**
     * The number twenty-seven.
     */
    TwentyEight(28),

    /**
     * The number twenty-nine.
     */
    TwentyNine(29),

    /**
     * The number thirty.
     */
    Thirty(30),

    /**
     * The number thirty-one.
     */
    ThirtyOne(31),

    /**
     * The number thirty-two.
     */
    ThirtyTwo(32),

    /**
     * The number thirty-three.
     */
    ThirtyThree(33),

    /**
     * The number thirty-four.
     */
    ThirtyFour(34),

    /**
     * The number thirty-five.
     */
    ThirtyFive(35),

    /**
     * The number thirty-six.
     */
    ThirtySix(36),

    /**
     * The number thirty-seven.
     */
    ThirtySeven(37),

    /**
     * The number thirty-eight.
     */
    ThirtyEight(38),

    /**
     * The number thirty-nine.
     */
    ThirtyNine(39),

    /**
     * The number forty.
     */
    Forty(40),

    /**
     * The number forty-one.
     */
    FortyOne(41),

    /**
     * The number forty-two.
     */
    FortyTwo(42),

    /**
     * The number forty-three.
     */
    FortyThree(43),

    /**
     * The number forty-four.
     */
    FortyFour(44),

    /**
     * The number forty-five.
     */
    FortyFive(45),

    /**
     * The number forty-six.
     */
    FortySix(46),

    /**
     * The number forty-seven.
     */
    FortySeven(47),

    /**
     * The number forty-eight.
     */
    FortyEight(48),

    /**
     * The number forty-nine.
     */
    FortyNine(49),

    /**
     * The number fifty.
     */
    Fifty(50),

    /**
     * The number fifty-one.
     */
    FiftyOne(51),

    /**
     * The number fifty-two.
     */
    FiftyTwo(52),

    /**
     * The number fifty-three.
     */
    FiftyThree(53),

    /**
     * The number fifty-four.
     */
    FiftyFour(54),

    /**
     * The number fifty-five.
     */
    FiftyFive(55),

    /**
     * The number fifty-six.
     */
    FiftySix(56),

    /**
     * The number fifty-seven.
     */
    FiftySeven(57),

    /**
     * The number fifty-eight.
     */
    FiftyEight(58),

    /**
     * The number fifty-nine.
     */
    FiftyNine(59);

    public final int number;

    /**
     * Instantiates the enum.
     * @param number The number that represents the minute value.
     */
    Minute(int number) { this.number = number; }

    /**
     * This method converts this enum into an ObservableList of Minute values and returns it.
     * @return An observable list of Minute values.
     */
    public static ObservableList<Minute> getMinutes() { return FXCollections.observableArrayList(values()); }

    /**
     * This method override the default toString() method of the Minute enum.
     * @return Returns a double digit string value.
     */
    @Override
    public String toString() { return (this.number < 10)? "0" + this.number : "" + this.number; }
}
