package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.DataClasses.Division;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBox_Division extends BorderPane {
    private Label label;
    private ComboBox<Division> comboBox = new ComboBox<Division>();
    private Object initValue;
    private boolean changed = false;

    public ComboBox_Division(ObservableList<Division> list, boolean padding) {
        this.format(list, padding);
    }

    public ComboBox_Division(String label, ObservableList<Division> list, boolean padding) {
        this.label = new Label(label);
        this.label.setFont(Styles.DefaultFont18);
        this.setLeft(this.label);
        this.format(list, padding);
    }

    public ComboBox_Division(ObservableList<Division> list, String placeholder, boolean padding) {
        this.comboBox.setPromptText(placeholder);
        this.format(list, padding);
    }

    private void format(ObservableList<Division> list, boolean padding) {
        this.comboBox.setItems(list);
        this.comboBox.setEditable(false);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle(Styles.StyleComboBoxRequired);
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);

        //Listeners
        this.comboBox.focusedProperty().addListener((x,y,z) -> {
            Division selectedDivision = this.comboBox.getValue();
            if(initValue == null & selectedDivision != null) {
                this.changed = true;
            } else if(!initValue.equals(selectedDivision)) {
                this.changed = true;
            }
        });
    }

    public void setLabelWidth(int i) {
        this.label.setMinWidth(i);
        this.label.setMaxWidth(i);
        this.label.setPrefWidth(i);
    }

    public void setComboBoxWidth(int i) {
        this.comboBox.setMinWidth(i);
        this.comboBox.setMaxWidth(i);
        this.comboBox.setPrefWidth(i);
    }

    public boolean itemIsSelected() {
        return !this.comboBox.getSelectionModel().isEmpty();
    }

    public Division getSelectedItem() {
        return this.comboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj
     */
    public void setInitialValue(Division obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public boolean isChanged() {
        return this.changed;
    }
}
