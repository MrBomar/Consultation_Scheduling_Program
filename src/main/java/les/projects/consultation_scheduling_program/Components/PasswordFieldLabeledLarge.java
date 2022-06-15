package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class PasswordFieldLabeledLarge extends BorderPane {
    private Label label = new Label();
    private PasswordField passwordField = new PasswordField();
    private String initValue = "";
    boolean changed = false;

    public PasswordFieldLabeledLarge(String labelText, String placeholder, Boolean disabled) {
        this.setPadding(new Insets(0,0,20,0));

        this.label.setText(labelText);
        this.label.setFont(Styles.DefaultFont20);
        this.label.setTextFill(Styles.TextColor);
        this.setLeft(this.label);

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

    public String getInput() {
        return passwordField.getText();
    }

    public void resetInput() {
        this.passwordField.setText("");
    }

    public void setInitialValue(String s) {
        this.initValue = s;
        this.passwordField.setText(s);
    }

    public boolean isChanged() { return this.changed; }
}
