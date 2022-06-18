package les.projects.consultation_scheduling_program.Views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.Division;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Enums.Styles;
import static les.projects.consultation_scheduling_program.Main.lrb;

public class CustomersView extends BorderPane {
    private final ComboBox<Country> countryComboBox = new ComboBox<>(Country.allCountries);
    private final ComboBox<Division> divisionComboBox = new ComboBox<>(Division.allDivisions);
    private final RadioButton countryRadio = new RadioButton(lrb.getString("country"));
    private final RadioButton allCustomersRadio = new RadioButton(lrb.getString("all_customers"));
    private final TableView<Customer> customerTable;

    public CustomersView() {
        //Customer header
        HBox header = new HBox();

            //Customer header objects
            //First label
            Label label1 = new Label(lrb.getString("view_customers_by"));
            label1.setFont(Styles.DefaultFont18);
            Insets padding = new Insets(0,20,30,20);
            label1.setPadding(padding);

            //Radio Button Group
            VBox selectorGroup = new VBox();

                //Dropdown selectors
                HBox dropDownGroup = new HBox();

                    //Dropdown group items
                    Label divisionLabel = new Label(lrb.getString("division"));

                    //Set fonts
                    this.countryRadio.setFont(Styles.DefaultFont16);
                    divisionLabel.setFont(Styles.DefaultFont16);

                    //Add padding to dropdowns
                    this.countryRadio.setPadding(padding);
                    divisionLabel.setPadding(padding);

                    //Add items to dropdown group
                    dropDownGroup.getChildren().addAll(this.countryRadio, this.countryComboBox,divisionLabel,this.divisionComboBox);

                //Selector group items
                this.allCustomersRadio.setPadding(padding);
                this.allCustomersRadio.setFont(Styles.DefaultFont16);

            //Add radio buttons to group
            final ToggleGroup radioGroup = new ToggleGroup();
            this.countryRadio.setToggleGroup(radioGroup);
            this.allCustomersRadio.setToggleGroup(radioGroup);
            this.allCustomersRadio.setSelected(true);

            //Add listener to the ToggleGroup
            radioGroup.selectedToggleProperty().addListener((a,b,newValue) -> {
                if(newValue.equals(allCustomersRadio)) {
                    this.selectAll();
                } else {
                    this.selectByCountryAndDivision();
                }
            });

            //Add listener to ComboBox(s)
            //Used lambda expressions here to prevent myself from having to use and import a ChangeListener,
            // reduced the amount of code needed.
            this.countryComboBox.valueProperty().addListener((observableValue, country, t1) -> {
                countryRadio.setSelected(true);
                divisionComboBox.setItems(countryComboBox.getValue().getDivisions());
                selectByCountryAndDivision();
            });
            //Used lambda expressions here to prevent myself from having to use and import a ChangeListener,
            // reduced the amount of code needed.
            this.divisionComboBox.valueProperty().addListener(((observableValue, division, t1) -> {
                countryRadio.setSelected(true);
                selectByCountryAndDivision();
            }));

            //Let's format the ComboBox(s)
            this.countryComboBox.setEditable(false);
            this.countryComboBox.setBorder(Styles.ButtonBorder);
            this.countryComboBox.setMaxWidth(200);
            this.countryComboBox.setStyle(Styles.StyleComboBoxRequired);
            this.divisionComboBox.setEditable(false);
            this.divisionComboBox.setBorder(Styles.ButtonBorder);
            this.divisionComboBox.setMaxWidth(200);
            this.divisionComboBox.setStyle(Styles.StyleComboBoxRequired);

            //Add children to selector group
            selectorGroup.getChildren().addAll(this.allCustomersRadio, dropDownGroup);

            //Add children to header
            header.getChildren().addAll(label1, selectorGroup);

        //TableView
        this.customerTable = this.buildCustomerTable();
        HBox.setHgrow(customerTable, Priority.ALWAYS);

        //Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(30,0,0,0));
        Region footerSpacer1 = new Region();
        Region footerSpacer2 = new Region();
        Region footerSpacer3 = new Region();
        footerSpacer1.setMinWidth(555);
        footerSpacer2.setMinWidth(30);
        footerSpacer3.setMinWidth(30);
        ButtonWide addCustomer = new ButtonWide(lrb.getString("add_customer"));
        addCustomer.setOnMouseClicked(this::addCustomer);
        ButtonWide updateCustomer = new ButtonWide(lrb.getString("update_customer"));
        updateCustomer.setOnMouseClicked(this::updateCustomer);
        ButtonWide deleteCustomer = new ButtonWide(lrb.getString("delete_customer"));
        deleteCustomer.setOnMouseClicked(this::deleteCustomer);
        footer.getChildren().addAll(footerSpacer1, addCustomer, footerSpacer3, updateCustomer, footerSpacer2, deleteCustomer);

        //Adjust view properties and add children to parent
        this.setPadding(new Insets(30));
        this.setBackground(Styles.BackgroundWhite);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setTop(header);
        this.setCenter(customerTable);
        this.setBottom(footer);
    }

    private void addCustomer(Event e) {
        AddUpdateCustomer modal = new AddUpdateCustomer(this.customerTable);
        modal.showAndWait();
    }

    private void updateCustomer(Event e) {
        if(this.customerTable.getSelectionModel().getSelectedItems().size() > 0) {
            AddUpdateCustomer modal = new AddUpdateCustomer(this.customerTable,this.customerTable.getSelectionModel().getSelectedItem());
            modal.showAndWait();
        } else {
            DialogMessage dialog = new DialogMessage(Message.NoSelectedCustomer);
            dialog.showAndWait();
        }
    }

    private void deleteCustomer(Event e) {
        if(this.customerTable.getSelectionModel().isEmpty()){
            //There is no customer selected.
            DialogMessage dialog = new DialogMessage("No Customer Selected", "There is no customer selected above. You must select a customer before deleting them.");
            dialog.showAndWait();
            return;
        }
        if(this.customerTable.getSelectionModel().getSelectedItem().getAppointments().size() > 0) {
            //The selected customer has appointments in the database.
            DialogConfirmation dialog = new DialogConfirmation("Appointments Detected", "This customer has appointments associated with it. Do you want to delete all appointments associated to this customer?");
            dialog.showAndWait();
            if(dialog.getResult()) {
                //The user wishes to delete the customer and all appointments.
                Appointment[] customerAppointments = this.customerTable.getSelectionModel().getSelectedItem().getAppointments().stream().toArray(Appointment[]::new);
                for(Appointment appointment: customerAppointments) {
                    appointment.delete();
                }
                this.customerTable.getSelectionModel().getSelectedItem().delete();
            }
        } else {
            //There are no appointments, but we need to verify that the user wants to delete the customer.
            DialogConfirmation dialog = new DialogConfirmation("Delete Customer?", "Are you sure you want to delete the selected customer?");
            dialog.showAndWait();
            if(dialog.getResult()) {
                this.customerTable.getSelectionModel().getSelectedItem().delete();
            }
        }
        this.customerTable.refresh();
    }

    private TableView<Customer> buildCustomerTable() {
        TableView<Customer> table = new TableView<>(Customer.allCustomers);
        TableColumn<Customer, Integer> idCol = new TableColumn<>(lrb.getString("id"));
        TableColumn<Customer, Integer> nameCol = new TableColumn<>(lrb.getString("customer_name"));
        TableColumn<Customer, Integer> addressCol = new TableColumn<>(lrb.getString("customer_address"));
        TableColumn<Customer, ObjectProperty<Country>> countryCol = new TableColumn<>(lrb.getString("country"));
        TableColumn<Customer, ObjectProperty<Division>> divisionCol = new TableColumn<>(lrb.getString("division"));
        TableColumn<Customer, Integer> postalCol = new TableColumn<>(lrb.getString("zip_code"));
        TableColumn<Customer, Integer> phoneCol = new TableColumn<>(lrb.getString("phone_number"));

        //Editable Properties
        nameCol.setEditable(true);
        addressCol.setEditable(true);
        countryCol.setEditable(true);
        divisionCol.setEditable(true);
        postalCol.setEditable(true);
        phoneCol.setEditable(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        //FIXME - We need to get his figured out.
        countryCol.setCellValueFactory(i -> {
            final ObjectProperty<Country> value = i.getValue().countryProperty();
            return Bindings.createObjectBinding(() -> value);
        });
        divisionCol.setCellValueFactory(i -> {
            final ObjectProperty<Division> value = i.getValue().divisionProperty();
            return Bindings.createObjectBinding(() -> value);
        });
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        countryCol.setCellFactory(col -> {
            TableCell<Customer, ObjectProperty<Country>> cell = new TableCell<>();
            final ComboBox<Country> comboBox = new ComboBox<>(Country.allCountries);
            comboBox.valueProperty().addListener((observational, oldValue, newValue) -> {
                if (oldValue != null) {
                    comboBox.valueProperty().unbindBidirectional((Property<Country>) oldValue);
                }
                if (newValue != null) {
                    comboBox.valueProperty().bindBidirectional((Property<Country>) newValue);
                }
            });
            cell.setGraphic(comboBox);
            return cell;
        });

        table.getColumns().add(idCol);
        table.getColumns().add(nameCol);
        table.getColumns().add(addressCol);
        table.getColumns().add(countryCol);
        table.getColumns().add(divisionCol);
        table.getColumns().add(postalCol);
        table.getColumns().add(phoneCol);
        return table;
    }

    private void selectAll() {
        this.customerTable.setItems(Customer.allCustomers);
    }

    private void selectByCountryAndDivision() {
        if(!this.divisionComboBox.getSelectionModel().isEmpty()) {
            //We must filter by Division.
            //Capture the division
            Division division = this.divisionComboBox.getValue();

            //Filter Customers by the DivisionID
            ObservableList<Customer> filteredCustomers = division.getCustomers();

            //Assign filteredCustomers to customerTable
            this.customerTable.setItems(filteredCustomers);
        } else if(!this.countryComboBox.getSelectionModel().isEmpty()) {
            //We must filter by Country only.
            //First we need to identify the country.
            Country country = countryComboBox.getValue();

            //Next we filter for all Divisions related to the Country
            ObservableList<Division> filteredDivisions = country.getDivisions();

            //We create an empty list to store the matched Customers.
            ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

            //Loop through all customers and add customers with matching divisionId to filteredCustomers.
            for(Customer customer: Customer.allCustomers) {
                for(Division division: filteredDivisions) {
                    if(customer.getDivisionID() == division.getID()) filteredCustomers.add(customer);
                }
            }

            //Set the customerTable to the filteredList
            this.customerTable.setItems(filteredCustomers);
        } else {
            this.selectAll();
        }
    }
}
