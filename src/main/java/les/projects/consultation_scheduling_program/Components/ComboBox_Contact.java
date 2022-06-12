package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBox_Contact extends BorderPane {
    private final ComboBox<Contact> comboBox = new ComboBox<>();
    private Object initValue;
    private boolean changed = false;

    public ComboBox_Contact(ObservableList<Contact> list, boolean padding) {
        this.format(list, padding);
    }

    public ComboBox_Contact(String labelText, ObservableList<Contact> list, boolean padding) {
        Label label = new Label(labelText);
        label.setFont(Styles.DefaultFont18);
        this.setLeft(label);
        this.format(list, padding);
    }

    private void format(ObservableList<Contact> list, boolean padding) {
        this.comboBox.setItems(list);
        this.comboBox.setEditable(false);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle(Styles.StyleComboBoxRequired);
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);

        //Listeners
        this.comboBox.focusedProperty().addListener((x,y,z) -> {
            Contact selectedContact = this.comboBox.getValue();
            if(initValue == null & selectedContact != null) {
                this.changed = true;
            } else if(!initValue.equals(selectedContact)) {
                this.changed = true;
            }
        });
    }

    public void setComboBoxWidth(int i) {
        this.comboBox.setMinWidth(i);
        this.comboBox.setMaxWidth(i);
        this.comboBox.setPrefWidth(i);
    }

    public boolean itemIsSelected() {
        return this.comboBox.getValue() != null;
    }

    public Contact getSelectedItem() {
        return this.comboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj Takes in a Contact object to set the value of the ComboBox.
     */
    public void setInitialValue(Contact obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public boolean isChanged() {
        return this.changed;
    }
}
