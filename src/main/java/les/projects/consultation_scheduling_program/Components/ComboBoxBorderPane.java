package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.DataClasses.*;
import les.projects.consultation_scheduling_program.Enums.*;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

public class ComboBoxBorderPane extends BorderPane {
    private Label label;
    private ComboBox comboBox = new ComboBox<>();
    private ObservableList list;
    private Class listClass;
    private Object initValue;
    private boolean changed = false;

    public ComboBoxBorderPane(ObservableList list, boolean padding) {
        this.format(list, padding);
    }

    public ComboBoxBorderPane(String label, ObservableList list, boolean padding) {
        this.label = new Label(label);
        this.label.setFont(Styles.DefaultFont18);
        this.setLeft(this.label);
        this.format(list, padding);
    }

    public ComboBoxBorderPane(ObservableList list, String placeholder, boolean padding) {
        this.comboBox.setPromptText(placeholder);
        this.format(list, padding);
    }

    private void format(ObservableList list, boolean padding) {
        //Set the list class
        this.listClass = list.stream().findFirst().getClass();

        this.list = list;
        this.comboBox.setItems(this.list);
        this.comboBox.setEditable(true);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle("--fx-font: 16px \"Segoe UI\";");
        if(padding) this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);
    }

    public int getID() {
        Object obj = comboBox.getSelectionModel().getSelectedItem();
        switch(obj.getClass().getName()) {
            case "Appointment":
                Appointment a = (Appointment) obj;
                return a.getId();
            case "Contact":
                Contact contact = (Contact) obj;
                return contact.getID();
            case "Country":
                Country country = (Country) obj;
                return country.getID();
            case "Customer":
                Customer customer = (Customer) obj;
                return customer.getId();
            case "FirstLevelDivision":
                FirstLevelDivision division = (FirstLevelDivision) obj;
                return division.getID();
            case "Hour":
                Hour hr = (Hour) obj;
                return hr.number;
            case "Minute":
                Minute min = (Minute) obj;
                return min.number;
            case "Meridiem":
                assert(false) : "Unable to get an integer value for Meridiem.";
                return 0;
            default:
                assert(false): "An unknown type of " + obj.getClass().getName() + " cannot be handled.";
                return 0;
        }
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

    public Object getSelectedItem() {
        return this.comboBox.getSelectionModel().getSelectedItem();
    }

    public int getValue() {
        //Using a try_catch block just in case the user hasn't made a selection.
        try {
            if(this.list.stream().findFirst().getClass().equals(Hour.class)) {
                Hour hr = (Hour) this.comboBox.getSelectionModel().getSelectedItem();
                return hr.number;
            } else if(this.list.stream().findFirst().getClass().equals(Minute.class)) {
                Minute min = (Minute) this.comboBox.getSelectionModel().getSelectedItem();
                return min.number;
            } else {
                DialogMessage dialog = new DialogMessage(Message.ProgrammingError_MissingClass);
                dialog.showAndWait();
                throw new RuntimeException("Cannot complete this operation.");
            }
        } catch (NullPointerException e) {
            DialogMessage dialog = new DialogMessage(Message.NoValueSelected);
            dialog.showAndWait();
            return 0;
        }
    }

    /**
     * This method takes in an object and sets it as the initial value and sets the current value of the
     * combo box to the object.
     * @param obj
     */
    public void setInitialValue(Object obj) {
        this.initValue = obj;
        this.comboBox.setValue(obj);
    }

    public void setValue(String s) {
        for(Object o: this.list) {
            if(o.getClass().equals(Meridiem.class)){
                this.comboBox.setValue(Meridiem.valueOf(s));
            }
        }
    }
}
