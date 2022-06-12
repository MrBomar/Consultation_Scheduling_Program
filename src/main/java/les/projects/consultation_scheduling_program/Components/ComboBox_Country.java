package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBox_Country extends BorderPane {
    private final ComboBox<Country> comboBox = new ComboBox<>();
    private Object initValue;
    private boolean changed = false;

    public ComboBox_Country(ObservableList<Country> list, boolean padding) {
        this.format(list, padding);
    }

    private void format(ObservableList<Country> list, boolean padding) {
        this.comboBox.setItems(list);
        this.comboBox.setEditable(false);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle(Styles.StyleComboBoxRequired);
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);

        //Listeners
        this.comboBox.focusedProperty().addListener((x,y,z) -> {
            Country selectedCountry = this.comboBox.getValue();
            if(initValue == null & selectedCountry != null) {
                this.changed = true;
            } else if(!initValue.equals(selectedCountry)) {
                this.changed = true;
            }
        });
    }

    public boolean itemIsSelected() {
        return !this.comboBox.getSelectionModel().isEmpty();
    }

    public Country getSelectedItem() {
        return this.comboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj Pass in a Country object so set initial selection.
     */
    public void setInitialValue(Country obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public boolean isChanged() {
        return this.changed;
    }
}
