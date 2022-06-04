package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TextFieldLabeledLarge extends BorderPane {
    private Label label = new Label();
    private TextField textField = new TextField();

    public TextFieldLabeledLarge(String labelText, String placeholder, Boolean disabled) {
        this.setPadding(new Insets(0,0,20,0));

        this.label.setText(labelText);
        this.label.setFont(Styles.DefaultFont20);
        this.label.setTextFill(Styles.TextColor);
        this.setLeft(this.label);

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
    }

    public String getInput() {
        return textField.getText();
    }

    public void resetInput() {
        this.textField.setText("");
    }
}
