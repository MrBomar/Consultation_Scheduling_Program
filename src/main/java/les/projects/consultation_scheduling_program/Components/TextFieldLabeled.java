package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TextFieldLabeled extends BorderPane {
    private Label label;
    private TextField textField;
    private String initValue = "";
    private boolean changed = false;

    public TextFieldLabeled(String labelText, boolean required, boolean disabled) {
        this.label = new Label(labelText);
        this.label.setFont(Styles.DefaultFont18);

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
        this.setLeft(this.label);
        this.setRight(this.textField);

        //Disabled properties
        if(disabled) {
            this.textField.setDisable(true);
            this.textField.setStyle("-fx-prompt-text-fill: rgb(0,0,0)");
        }

        //Required properties
        if(required) {
            this.textField.setPromptText("(Required)");
            this.textField.setStyle(Styles.StyleTextFieldRequired);
        }

        //Event listeners
        this.textField.focusedProperty().addListener(
                (x, y, z) -> changed = !this.initValue.equals(this.textField.getText())
        );
    }

    public String getInput() {
        return this.textField.getText();
    }
    public void setInitialValue(String s) {
        this.initValue = s;
        this.textField.setText(s);
    }

    public void setPromptText(String s) { this.textField.setPromptText(s); }
    public boolean isChanged() { return this.changed; }

    public boolean isNotBlank() {
        switch(this.textField.getText()) {
            case "":
            case " ":
                return false;
            default:
                return true;
        }
    }
}
