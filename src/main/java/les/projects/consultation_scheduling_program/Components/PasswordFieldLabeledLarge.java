package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX BorderPane. It creates a BorderPane containing a formatted Label and a PasswordField.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class PasswordFieldLabeledLarge extends BorderPane {
    private final PasswordField passwordField = new PasswordField();
    private final String initValue = "";
    boolean changed = false;

    /**
     * Initializes a JavaFX BorderPane containing a formatted Label and a PasswordField.
     * @param labelText String to display on the Label.
     * @param placeholder String to display as prompt in the PasswordField.
     * @param disabled Set to true if the Password field should be disabled.
     */
    public PasswordFieldLabeledLarge(String labelText, String placeholder, Boolean disabled) {
        this.setPadding(new Insets(0,0,20,0));

        Label label = new Label();
        label.setText(labelText);
        label.setFont(Styles.DefaultFont20);
        label.setTextFill(Styles.TextColor);
        this.setLeft(label);

        this.passwordField.setPromptText(placeholder);
        this.passwordField.setMinSize(276,38);
        this.passwordField.setMaxSize(276,38);
        this.passwordField.setPrefSize(276,38);
        this.passwordField.setBorder(Styles.ButtonBorder);
        this.passwordField.setFont(Styles.DefaultFont20);
        if(disabled) {
            this.passwordField.setDisable(true);
            this.passwordField.setBackground(Styles.DisabledBackground);
            this.passwordField.setStyle(
                    "-fx-prompt-text-fill: rgb(0,0,0);"
            );
        }
        this.setRight(this.passwordField);

        //Event listeners
        this.passwordField.focusedProperty().addListener(
                (x, y, z) -> changed = !this.initValue.equals(this.passwordField.getText())
        );
    }

    /**
     * Method returns the user's input in the PasswordField.
     * @return String value entered in the PasswordField.
     */
    public final String getInput() {
        return passwordField.getText();
    }

    /**
     * Method clears the value entered in the PasswordField.
     */
    public final void resetInput() {
        this.passwordField.setText("");
    }
}
