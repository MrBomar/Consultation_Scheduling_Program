package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TextAreaLabeled extends VBox {
    private Label label;
    private TextArea text;
    private String initValue = "";
    private boolean changed = false;

    public TextAreaLabeled(String labelText) {
        this.label = new Label(labelText);
        this.label.setFont(Styles.DefaultFont18);

        this.text = new TextArea();
        this.text.setMinSize(400, 120);
        this.text.setMaxSize(400, 120);
        this.text.setPrefSize(400, 120);
        this.text.setBorder(Styles.ButtonBorder);

        this.setPadding(new Insets(10,0,0,0));
        this.getChildren().addAll(this.label, this.text);

        //Event listeners
        this.text.focusedProperty().addListener(
                (x,y,z) -> changed = !this.initValue.equals(this.text.getText())
        );
    }

    public String getInput() {
        return this.text.getText();
    }

    public void setInitialValue(String s) {
        this.initValue = s;
        this.text.setText(s);
    }

    public boolean isChanged() { return this.changed; }
}
