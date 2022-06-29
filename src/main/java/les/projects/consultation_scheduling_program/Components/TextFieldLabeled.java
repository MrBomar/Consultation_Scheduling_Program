package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * The class extends the JavaFX BorderPane class. The BorderPane contains a TextField and a Label.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class TextFieldLabeled extends BorderPane {
    private final TextField textField;
    private String initValue = "";
    private boolean changed = false;

    /**
     * Initializes a BorderPane containing a TextField and a Label.
     * @param labelText String to display in the TextField.
     * @param required Set to true if input from the user is required.
     * @param disabled Set to true to disallow user input.
     */
    public TextFieldLabeled(String labelText, boolean required, boolean disabled) {
        Label label = new Label(labelText);
        label.setFont(Styles.DefaultFont18);

        this.textField = new TextField();
        this.textField.setMinSize(200,25);
        this.textField.setMaxSize(200,25);
        this.textField.setPrefSize(200,25);
        this.textField.setStyle(Styles.StyleTextField);
        this.textField.setBorder(Styles.ButtonBorder);

        this.setPadding(new Insets(10,0,10,0));
        this.setMinWidth(400);
        this.setMaxWidth(400);
        this.setPrefWidth(400);
        this.setLeft(label);
        this.setRight(this.textField);

        //Disabled properties
        if(disabled) {
            this.textField.setDisable(true);
            this.textField.setStyle("-fx-prompt-text-fill: rgb(0,0,0)");
        }

        //Required properties
        if(required) {
            this.textField.setPromptText("(" + lrb.getString("required") + ")");
            this.textField.setStyle(Styles.StyleTextFieldRequired);
        }

        //Event listeners
        this.textField.focusedProperty().addListener(
                (x, y, z) -> changed = !this.initValue.equals(this.textField.getText())
        );
    }

    /**
     * This method returns the users input in the TextField.
     * @return String value of the user's input.
     */
    public final String getInput() {
        return this.textField.getText();
    }

    /**
     * This method set the initial value of the TextField and stores that value to verify if the value changes.
     * @param s String to set as initial value and display in the TextField.
     */
    public final void setInitialValue(String s) {
        this.initValue = s;
        this.textField.setText(s);
    }

    /**
     * This method sets the prompt text to display in the TextField when TextField is empty.
     * @param s String to set as prompt text.
     */
    public final void setPromptText(String s) { this.textField.setPromptText(s); }

    /**
     * This method detects if the data in the TextField has been changed.
     * @return Returns true if the value have been changed.
     */
    public final boolean isChanged() { return this.changed; }

    /**
     * This method detects if the TextField has any input.
     * @return Returns true if the TextField is not empty.
     */
    public final boolean isNotBlank() {
        return this.textField.getText().equals("") || this.textField.getText().equals(" ");
    }
}
