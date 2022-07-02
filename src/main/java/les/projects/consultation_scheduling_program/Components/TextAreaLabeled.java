package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX VBox and creates a formatted VBox containing a TextArea and a Label.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class TextAreaLabeled extends VBox {
    private final TextArea text;
    private String initValue;
    private boolean changed = false;

    /**
     * Initializes a VBox containing a TextArea and a Label.
     * @param labelText String to display on the label.
     */
    public TextAreaLabeled(String labelText) {
        Label label = new Label(labelText);
        label.setFont(Styles.DefaultFont18);

        this.text = new TextArea();
        this.text.setMinSize(400, 120);
        this.text.setMaxSize(400, 120);
        this.text.setPrefSize(400, 120);
        this.text.setStyle(Styles.StyleTextArea);

        this.setPadding(new Insets(10,0,0,0));
        this.getChildren().addAll(label, this.text);

        //Event listeners
        this.text.focusedProperty().addListener((x,y,z) -> {

            boolean initialEmpty = this.initValue == null ||
                    this.initValue.equals("") ||
                    this.initValue.equals(" ") ||
                    this.initValue.isEmpty();
            boolean textEmpty = this.text.getText().isEmpty() ||
                    this.text.getText().equals("") ||
                    this.text.getText().equals(" ");

            if(initialEmpty && textEmpty) {
                this.changed = false;
            } else if (initialEmpty && !textEmpty) {
                this.changed = true;
            } else if (this.initValue.equals(this.text.getText())){
                this.changed = false;
            } else {
                this.changed = true;
            }
        });
    }

    /**
     * Method returns the value entered in the TextArea.
     * @return String value of the TextArea.
     */
    public String getInput() {
        return this.text.getText();
    }

    /**
     * This method set the initial value of the TextArea, and stores it for detecting if the value has change.
     * @param s String to display in the TextArea.
     */
    public void setInitialValue(String s) {
        this.initValue = s;
        this.text.setText(s);
    }

    /**
     * The method detects if the value of the TextArea has been changed.
     * @return Returns true if the value of the TextArea has been changed.
     */
    public boolean isChanged() {
        return this.changed;
    }
}
