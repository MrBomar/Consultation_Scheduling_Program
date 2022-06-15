package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBoxStyled<T> extends ComboBox<T> {
    private Object initialValue;
    private boolean changed = false;

    public ComboBoxStyled(ObservableList<T> observableList) {
        super(observableList);
        format("");
    }

    public ComboBoxStyled(ObservableList<T> observableList, String promptText) {
        super(observableList);
        format(promptText);
    }

    private void format(String promptText) {
        //Apply formatting
        this.setEditable(false);
        this.setBorder(Styles.ButtonBorder);
        this.setMaxWidth(200);
        this.setStyle(Styles.StyleComboBoxRequired);

        //Process parameters
        if(promptText != "") { this.setPromptText(promptText); }

        //This listener detects of the value of the ComboBox has been changed
        //Listeners
        this.focusedProperty().addListener((x,y,z) -> {
            Object selectedObject = this.getValue();
            if(this.initialValue == null & selectedObject != null) {
                this.changed = true;
            } else if(!this.initialValue.equals(selectedObject)) {
                this.changed = true;
            }
        });
    }

    public void setInitialValue(T obj) {
        this.initialValue = obj;
        this.setValue(obj);
    }

    public void setWidth(int i) {
        this.setMinWidth(i);
        this.setMaxWidth(i);
        this.setPrefWidth(i);
    }

    public boolean isChanged() { return this.changed; }
}
