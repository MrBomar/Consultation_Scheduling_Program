package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBox_Customer extends BorderPane {
    private Label label;
    private ComboBox<Customer> comboBox = new ComboBox<Customer>();
    private Object initValue;
    private boolean changed = false;

    public ComboBox_Customer(ObservableList<Customer> list, boolean padding) {
        this.format(list, padding);
    }

    public ComboBox_Customer(String label, ObservableList<Customer> list, boolean padding) {
        this.label = new Label(label);
        this.label.setFont(Styles.DefaultFont18);
        this.setLeft(this.label);
        this.format(list, padding);
    }

    public ComboBox_Customer(ObservableList<Customer> list, String placeholder, boolean padding) {
        this.comboBox.setPromptText(placeholder);
        this.format(list, padding);
    }

    private void format(ObservableList<Customer> list, boolean padding) {
        this.comboBox.setItems(list);
        this.comboBox.setEditable(true);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle(Styles.StyleComboBoxRequired);
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);

        //Listeners
        this.comboBox.focusedProperty().addListener((x,y,z) -> {
            if((this.initValue != null) && (z != null)){
                if(!this.initValue.equals(z)) this.changed = true;
            } else if (this.initValue == null && z != null) {
                this.changed = true;
            } else if (this.initValue != null && z == null) {
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

    public Customer getSelectedItem() {
        return this.comboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj
     */
    public void setInitialValue(Customer obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public boolean isChanged() {
        return this.changed;
    }
}
