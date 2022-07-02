package les.projects.consultation_scheduling_program.Enums;

import javafx.geometry.Insets;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class contains miscellaneous stying elements to help facilitate the reduction of code redundancy.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public abstract class Styles {
    /**
     * This property stores a JavaFX Background object formatted white.
     */
    public static final Background BackgroundWhite = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * This property stores a JavaFX Background object formatted grey.
     */
    public static final Background BackgroundGrey = new Background(new BackgroundFill(Color.rgb(220,220,220), CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * This property stores a JavaFX Background object formatted grey.
     */
    public static final Background BackgroundButtonHover = new Background(new BackgroundFill(Color.rgb(240,240,240), CornerRadii.EMPTY, Insets.EMPTY));

    /**
     * This property stores a 1px wide black JavaFX Border.
     */
    public static final Border BorderBlack = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));

    /**
     * This property stores a 1px wide blue JavaFX Border.
     */
    public static final Border BorderBlue = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));

    /**
     * This property stores a 1px wide green JavaFX Border.
     */
    public static final Border BorderGreen = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));

    /**
     * This property stores a 1px wide purple JavaFX Border.
     */
    public static final Border BorderPurple = new Border(new BorderStroke(Color.PURPLE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));

    /**
     * This property stores a 1px wide grey JavaFX Border.
     */
    public static final Border BorderGrey = new Border(new BorderStroke(Color.rgb(220,220,220), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));

    /**
     * This property stores a grey JavaFX Background object.
     */
    public static final Background DisabledBackground = new Background(new BackgroundFill(Color.rgb(219,219,219), CornerRadii.EMPTY,Insets.EMPTY));

    /**
     * This property stores a grey JavaFX TextColor object.
     */
    public static final Color TextColor = Color.rgb(112,112,112);

    /**
     * This property stores a "Segoe UI" JavaFX Font object sized 28.
     */
    public static final Font DefaultFont28 = new Font("Segoe UI", 28);

    /**
     * This property stores a "Bold Segoe UI" JavaFX Font object sized 28.
     */
    public static final Font BoldFont28 = Font.font("Segoe UI", FontWeight.BOLD, 28);

    /**
     * This property stores a "Segoe UI" JavaFX Font object sized 24.
     */
    public static final Font DefaultFont24 = new Font("Segoe UI", 24);

    /**
     * This property stores a "Segoe UI" JavaFX Font object sized 20.
     */
    public static final Font DefaultFont20 = new Font("Segoe UI", 20);

    /**
     * This property stores a "Segoe UI" JavaFX Font object sized 18.
     */
    public static final Font DefaultFont18 = new Font("Segoe UI", 18);

    /**
     * This property stores a "Segoe UI" JavaFX Font object sized 16.
     */
    public static final Font DefaultFont16 = new Font("Segoe UI", 16);

    /**
     * This property stores a JavaFX ButtonInnerShadow object.
     */
    public static final InnerShadow ButtonInnerShadow = new InnerShadow(3,3,3, Styles.TextColor);

    /**
     * This property stores a JavaFX Insets object set to 30px.
     */
    public static final Insets Padding30px = new Insets(30);

    /**
     * This property stores a CSS String for formatting a JavaFX TextField.
     */
    public static final String StyleTextField =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;" +
            "-fx-padding: 2px;" +
            "-fx-min-height: 30px;";

    /**
     * This property stores a CSS String for formatting a JavaFX TextField where input is required.
     */
    public static final String StyleTextFieldRequired = StyleTextField + "-fx-prompt-text-fill: rgb(255,0,0);";

    /**
     * This property stores a CSS String for formatting a JavaFX ComboBox.
     */
    public static final String StyleComboBox =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;" +
            "-fx-combo-box-background-color: white;" +
            "-fx-min-height: 30px;";

    /**
     * This property stores a CSS String for formatting a JavaFX ComboBox where input is required.
     */
    public static final String StyleComboBoxRequired = StyleComboBox + "-fx-prompt-text-fill: rgb(255,0,0);";

    /**
     * This property stores a CSS String for formatting a JavaFX TextArea.
     */
    public static final String StyleTextArea =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;";
}
