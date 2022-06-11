package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import les.projects.consultation_scheduling_program.DataClasses.*;
import les.projects.consultation_scheduling_program.Enums.Hour;
import les.projects.consultation_scheduling_program.Enums.Meridiem;
import les.projects.consultation_scheduling_program.Enums.Minute;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ComboBoxBorderPane extends BorderPane {
    private Label label;
    private ComboBox comboBox = new ComboBox<>();
    private ObservableList list;

    public ComboBoxBorderPane(ObservableList list) {
        this.format(list);
    }

    public ComboBoxBorderPane(String label, ObservableList list) {
        this.label = new Label(label);
        this.label.setFont(Styles.DefaultFont18);
        this.setLeft(this.label);
        this.format(list);
    }

    public ComboBoxBorderPane(ObservableList list, String placeholder) {
        this.comboBox.setPromptText(placeholder);
        this.format(list);
    }

    private void format(ObservableList list) {
        this.list = list;
        this.comboBox.setItems(this.list);
        this.comboBox.setEditable(true);
        this.comboBox.setBorder(Styles.ButtonBorder);
        this.comboBox.setMaxWidth(200);
        this.comboBox.setStyle("--fx-font: 16px \"Segoe UI\";");
        this.comboBox.setCellFactory(i -> listFactory());
        System.out.println("setCellFactory():");
        this.comboBox.setButtonCell(listFactory());
        System.out.println("setButtonCell():");
        this.setPadding(new Insets(10,0,10,0));
        this.setRight(this.comboBox);
    }

    private ListCell<Object> listFactory() {
        return new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else if(item.getClass().equals(Appointment.class)) {
                    Appointment appointment = (Appointment) item;
                    setText(appointment.getTitle());
                } else if(item.getClass().equals((Contact.class))) {
                    Contact contact = (Contact) item;
                    setText(contact.getName());
                } else if(item.getClass().equals(Country.class)) {
                    Country country = (Country) item;
                    setText(country.getName());
                } else if(item.getClass().equals(Customer.class)) {
                    Customer customer = (Customer) item;
                    setText(customer.getCustomerName());
                } else if(item.getClass().equals(FirstLevelDivision.class)) {
                    FirstLevelDivision division = (FirstLevelDivision) item;
                    setText(division.getName());
                } else if(item.getClass().equals(Hour.class)) {
                    Hour hour = (Hour) item;
                    setText("" + hour.number);
                } else if(item.getClass().equals(Minute.class)) {
                    Minute minute = (Minute) item;
                    setText("" + minute.number);
                    setValue(minute.number);
                } else if(item.getClass().equals(Meridiem.class)) {
                    Meridiem meridiem = (Meridiem) item;
                    setText("" + meridiem.s);
                }
            }
        };
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

    public int getValue() {
        return 1;
    }

    public void setValue(int id) {
        for(Object obj: this.list) {
            if(obj.getClass().equals(Appointment.class)){
                Appointment appointment = (Appointment) obj;
                if(appointment.getId() == id){
                    this.comboBox.setValue(appointment);
                    break;
                }
            } else if(obj.getClass().equals(Contact.class)) {
                Contact contact = (Contact) obj;
                if(contact.getID() == id){
                    this.comboBox.setValue(contact);
                    break;
                }
            } else if(obj.getClass().equals(Country.class)) {
                Country country = (Country) obj;
                if(country.getID() == id) {
                    this.comboBox.setValue(country);
                    break;
                }
            } else if(obj.getClass().equals(Customer.class)) {
                Customer customer = (Customer) obj;
                if(customer.getId() == id) {
                    this.comboBox.setValue(customer);
                    break;
                }
            } else if(obj.getClass().equals(FirstLevelDivision.class)) {
                FirstLevelDivision division = (FirstLevelDivision) obj;
                if (division.getID() == id) {
                    this.comboBox.setValue(division);
                    break;
                }
            }
        }

    }
}
