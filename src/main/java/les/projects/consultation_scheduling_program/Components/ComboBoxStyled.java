package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import les.projects.consultation_scheduling_program.Enums.Styles;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * Class extends the JavaFX ComboBox and formats it visually.
 * @param <T> The data type of the object to be displayed in the ComboBox.
 *
 * @author Leslie C. bomar 3rd
 * @version 1.0
 */
public class ComboBoxStyled<T> extends ComboBox<T> {
    private Object initialValue;
    private boolean changed = false;

    /**
     * This constructor instantiates a JavaFX ComboBox for displaying objects.
     * @param observableList An ObservableList of data objects to be displayed in the ComboBox.
     */
    public ComboBoxStyled(ObservableList<T> observableList) {
        super(observableList);
        format("");
    }

    /**
     * This constructor instantiates a JavaFX ComboBox for displaying objects. The ComboBox displays placeholder text
     * if required is set to true.
     * @param observableList And ObservableList of data objects to be displayed in the ComboBox.
     * @param required true = required.
     */
    public ComboBoxStyled(ObservableList<T> observableList, boolean required) {
        super(observableList);
        format("");
        if(required) {
            this.setPromptText("(" + lrb.getString("Required") + ")");
            this.setStyle(Styles.StyleComboBoxRequired);
        }
    }

    /**
     * This method applies formatting to the ComboBox. A lambda expression is used here to eliminate the need to refer
     * to a private method.
     * @param promptText String to display as placeholder text.
     */
    private void format(String promptText) {
        //Apply formatting
        this.setEditable(false);
        this.setBorder(Styles.BorderBlack);
        this.setMaxWidth(200);
        this.setStyle(Styles.StyleComboBoxRequired);

        //Process parameters
        if(!promptText.equals("")) { this.setPromptText(promptText); }

        //This listener detects of the value of the ComboBox has been changed
        //Listeners
        this.focusedProperty().addListener((x,y,z) -> {
            if(this.initialValue == null) {
                this.changed = !this.getSelectionModel().isEmpty();
            } else {
                if(this.getSelectionModel().isEmpty()) {
                    this.changed = true;
                } else {
                    this.changed = !this.initialValue.equals(this.getValue());
                };
            }
        });
    }

    /**
     * This method sets the initial value of the ComboBox and stores a copy for detecting later changes.
     * @param obj The original data object to be selected in the ComboBox.
     */
    public void setInitialValue(T obj) {
        this.initialValue = obj;
        this.setValue(obj);
    }

    /**
     * This method to set the width of the ComboBox.
     * @param i Pixel value.
     */
    public void setWidth(int i) {
        this.setMinWidth(i);
        this.setMaxWidth(i);
        this.setPrefWidth(i);
    }

    /**
     * This method checks to see if the value of the ComboBox has changed.
     * @return Returns true if anything has changed.
     */
    public boolean isChanged() {
        return this.changed;
    }
}
