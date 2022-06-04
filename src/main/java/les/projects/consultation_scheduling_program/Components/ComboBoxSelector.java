package les.projects.consultation_scheduling_program.Components;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import les.projects.consultation_scheduling_program.DataClasses.*;
import les.projects.consultation_scheduling_program.Enums.Hour;
import les.projects.consultation_scheduling_program.Enums.Minute;

public class ComboBoxSelector extends ComboBox {

    public ComboBoxSelector(ObservableList li) {
        super(li);

        this.setPrefWidth(170);
        this.setMinWidth(170);
        this.setMaxWidth(170);
        this.setStyle("--fx-font: 16px \"Segoe UI\";");
        this.setButtonCell(cellFactory.call(null));
        this.setCellFactory(cellFactory);
    }

    Callback<ListView<Object>, ListCell<Object>> cellFactory = new Callback<ListView<Object>, ListCell<Object>>() {

        @Override
        public ListCell<Object> call(ListView<Object> l) {
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
                        setText(customer.getName());
                    } else if(item.getClass().equals(FirstLevelDivision.class)) {
                        FirstLevelDivision division = (FirstLevelDivision) item;
                        setText(division.getName());
                    }
                }
            } ;
        }
    };


    public int getID() {
        Object obj = this.getSelectionModel().getSelectedItem();
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
                return customer.getID();
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
}
