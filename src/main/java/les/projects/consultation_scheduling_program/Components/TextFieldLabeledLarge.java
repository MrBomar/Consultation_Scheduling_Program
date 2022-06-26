package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX BorderPane class. The BorderPabe contains a formatted TextField and Label.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class TextFieldLabeledLarge extends BorderPane {
    private final TextField textField = new TextField();
    private final String initValue = "";
    boolean changed = false;

    /**
     * Initializes a JavaFX BorderPane containing a TextField and Label.
     * @param labelText String to display on the Label.
     * @param placeholder String to set as prompt text on the TextField.
     * @param disabled Set to true to disallow user input.
     */
    public TextFieldLabeledLarge(String labelText, String placeholder, Boolean disabled) {
        this.setPadding(new Insets(0,0,20,0));

        Label label = new Label(labelText);
        label.setFont(Styles.DefaultFont20);
        label.setTextFill(Styles.TextColor);
        this.setLeft(label);

        this.textField.setPromptText(placeholder);
        this.textField.setMinSize(276,38);
        this.textField.setMaxSize(276,38);
        this.textField.setPrefSize(276,38);
        this.textField.setBorder(Styles.ButtonBorder);
        this.textField.setFont(Styles.DefaultFont20);
        if(disabled) {
            this.textField.setDisable(true);
            this.textField.setBackground(Styles.DisabledBackground);
            this.textField.setStyle(
                    "-fx-prompt-text-fill: rgb(0,0,0);"
            );
        }
        this.setRight(this.textField);

        //Event listeners
        this.textField.focusedProperty().addListener(
                (x, y, z) -> changed = !this.initValue.equals(this.textField.getText())
        );
    }

    /**
     * This method returns the user input in the TextField.
     * @return String value of the user input.
     */
    public String getInput() {
        return textField.getText();
    }

    /**
     * This method clears the value in the TextField.
     */
    public void resetInput() {
        this.textField.setText("");
    }
}
