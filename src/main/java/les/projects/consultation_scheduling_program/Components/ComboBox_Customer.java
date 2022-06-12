package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBox_Customer extends BorderPane {
    private Label label;
    private final ComboBox<Customer> comboBox = new ComboBox<Customer>(Customer.allCustomers);
    private Customer initValue;
    private boolean changed = false;

    public ComboBox_Customer(String labelText, String promptText, boolean padding) {
        if(labelText != ""){
            this.label = new Label(labelText);
            this.label.setFont(Styles.DefaultFont18);
            this.setLeft(this.label);
        }
        if(promptText != "") { this.comboBox.setPromptText(promptText); }

        this.comboBox.setEditable(false);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle(Styles.StyleComboBoxRequired);
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);

        //Listeners
        this.comboBox.focusedProperty().addListener((x,y,z) -> {
            Customer selectedCustomer = this.comboBox.getValue();
            if(initValue == null & selectedCustomer != null) {
                this.changed = true;
            } else if(!initValue.equals(selectedCustomer)) {
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

    public boolean itemIsSelected() { return this.comboBox.getValue() != null; }

    public Customer getSelectedItem() {
        int id = this.comboBox.getSelectionModel().getSelectedItem().getId();
        return Customer.getObjById(id);
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj Pass in a Customer object to pre-select/
     */
    public void setInitialValue(Customer obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public boolean isChanged() {
        return this.changed;
    }
}
