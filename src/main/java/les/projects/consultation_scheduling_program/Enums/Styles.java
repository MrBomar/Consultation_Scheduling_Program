package les.projects.consultation_scheduling_program.Enums;

import javafx.geometry.Insets;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class Styles {
    public static final Background BackgroundWhite = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background BackgroundGrey = new Background(new BackgroundFill(Color.rgb(220,220,220), CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background BackgroundButtonHover = new Background(new BackgroundFill(Color.rgb(240,240,240), CornerRadii.EMPTY, Insets.EMPTY));
    public static final Border ButtonBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN));
    public static final Background DisabledBackground = new Background(new BackgroundFill(Color.rgb(219,219,219), CornerRadii.EMPTY,Insets.EMPTY));
    public static final Color TextColor = Color.rgb(112,112,112);
    public static final Font DefaultFont28 = new Font("Segoe UI", 28);
    public static final Font BoldFont28 = Font.font("Segoe UI", FontWeight.BOLD, 28);
    public static final Font DefaultFont24 = new Font("Segoe UI", 24);
    public static final Font DefaultFont20 = new Font("Segoe UI", 20);
    public static final Font DefaultFont18 = new Font("Segoe UI", 18);
    public static final Font DefaultFont16 = new Font("Segoe UI", 16);
    public static final InnerShadow ButtonInnerShadow = new InnerShadow(3,3,3, Styles.TextColor);
    public static final Insets Padding30px = new Insets(30);
    public static final String StyleTextField =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;" +
            "-fx-padding: 2px;" +
            "-fx-min-height: 30px;";
    public static final String StyleTextFieldRequired = StyleTextField + "-fx-prompt-text-fill: rgb(255,0,0);";
    public static final String StyleComboBox =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;" +
            "-fx-combo-box-background-color: white;" +
            "-fx-min-height: 30px;";
    public static final String StyleComboBoxRequired = StyleComboBox + "-fx-prompt-text-fill: rgb(255,0,0);";
    public static final String StyleTextArea =
            "-fx-border-style: solid;" +
            "-fx-border-width: 1px;" +
            "-fx-border-color: black;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-size: 14;";
}
