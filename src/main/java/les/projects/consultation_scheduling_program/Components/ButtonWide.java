package les.projects.consultation_scheduling_program.Components;

import javafx.scene.control.Button;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * Class extends the JavaFX Button and produces a formatted button.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonWide extends Button {

    /**
     * Produces a formatted JavaFX Button.
     * @param s String to display on the button.
     */
    public ButtonWide(String s) {
        //Apply styling.
        this.setText(s);
        this.setFont(Styles.DefaultFont16);
        this.setBorder(Styles.BorderBlack);
        this.setMinSize(205,50);
        this.setMaxSize(205,50);
        this.setPrefSize(205,50);
        this.setBackground(Styles.BackgroundWhite);

        //Apply mouse effects.
        this.setOnMousePressed((event) -> this.setEffect(Styles.ButtonInnerShadow));
        this.setOnMouseReleased((event) -> this.setEffect(null));
        this.setOnMouseEntered((event) -> this.setBackground(Styles.BackgroundButtonHover));
        this.setOnMouseExited((event) -> this.setBackground(Styles.BackgroundWhite));
    }
}
