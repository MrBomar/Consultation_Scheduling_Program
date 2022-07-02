package les.projects.consultation_scheduling_program.Components;

import javafx.scene.control.Button;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX Button and produces a formatted button.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonWide extends Button {

    /**
     * The constructor instantiates a formatted JavaFX Button. Four lambda expressions are used to set the actions
     * taken during the various onMouse events. Four lambda expressions are user here to reduce the amount of code
     * written and eliminate the need for additional private methods.
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
        this.setOnMousePressed(e -> this.setEffect(Styles.ButtonInnerShadow));
        this.setOnMouseReleased(e -> this.setEffect(null));
        this.setOnMouseEntered(e -> this.setBackground(Styles.BackgroundButtonHover));
        this.setOnMouseExited(e -> this.setBackground(Styles.BackgroundWhite));
    }
}
