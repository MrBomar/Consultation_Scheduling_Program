package les.projects.consultation_scheduling_program.Components;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * Class extends the JavaFX Button and formats it.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonStandard extends Button {
    /**
     * Creates a JavaFX button for use in a GridPane.
     * @param s String to display on the button.
     * @param c If applying to a GridPane, you can specify the column here.
     * @param r If applying to a GridPane, you can specify the row here.
     */
    public ButtonStandard(String s, int c, int r) {
        GridPane.setConstraints(this, c, r);
        this.styleIt(s);
    }

    /**
     * Creates a JavaFX button.
     * @param s String to display on the button.
     */
    public ButtonStandard(String s) {
        this.styleIt(s);
    }

    /**
     * Set the effects to apply to the button during certain mouse events.
     */
    private void addMouseEffects() {
        this.setOnMousePressed((event) -> this.setEffect(Styles.ButtonInnerShadow));
        this.setOnMouseReleased((event)-> this.setEffect(null));
        this.setOnMouseEntered((event) -> this.setBackground(Styles.BackgroundButtonHover));
        this.setOnMouseExited((event) -> this.setBackground(Styles.BackgroundWhite));
    }

    /**
     * Applies the basic styling to the button.
     * @param s String to display on the button.
     */
    private void styleIt(String s) {
        this.setText(s);
        this.setFont(Styles.DefaultFont20);
        this.setBorder(Styles.ButtonBorder);
        this.setMinSize(120,40);
        this.setMaxSize(120,40);
        this.setPrefSize(120,40);
        this.setBackground(Styles.BackgroundWhite);
        this.addMouseEffects();
    }
}
