package les.projects.consultation_scheduling_program.Views;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.Division;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Enums.Styles;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Customers view.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class CustomersView extends BorderPane {
    private final ComboBox<Country> countryComboBox = new ComboBox<>(Country.allCountries);
    private final ComboBox<Division> divisionComboBox = new ComboBox<>(Division.allDivisions);
    private final RadioButton countryRadio = new RadioButton(lrb.getString("country"));
    private final RadioButton allCustomersRadio = new RadioButton(lrb.getString("all_customers"));
    private final TableView<Customer> customerTable;

    /**
     * This constructor instantiates the Customers view.
     */
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

    /**
     * This method opens the Add/Update Customer modal in add mode.
     * @param e Mouse event.
     */
    private void addCustomer(Event e) {
        AddUpdateCustomer modal = new AddUpdateCustomer();
        modal.showAndWait();
        this.customerTable.setItems(Customer.allCustomers);
    }

    /**
     * This method opens the Add/Update Customer modal in update mode selecting the object selected in the TableView.
     * @param e Mouse event.
     */
    private void updateCustomer(Event e) {
        if(this.customerTable.getSelectionModel().getSelectedItems().size() > 0) {
            AddUpdateCustomer modal = new AddUpdateCustomer(this.customerTable.getSelectionModel().getSelectedItem());
            modal.showAndWait();
            this.customerTable.setItems(Customer.allCustomers);
        } else {
            DialogMessage dialog = new DialogMessage(Message.NoSelectedCustomer);
            dialog.showAndWait();
        }
    }

    /**
     * This method calls the delete() method of the Customer object selected in the TableView.
     * @param e Mouse event.
     */
    private void deleteCustomer(Event e) {
        if(this.customerTable.getSelectionModel().isEmpty()){
            //There is no customer selected.
            DialogMessage dialog = new DialogMessage(Message.NoSelectedCustomer);
            dialog.showAndWait();
            return;
        }
        if(this.customerTable.getSelectionModel().getSelectedItem().getAppointments().size() > 0) {
            //The selected customer has appointments in the database.
            String[] args = new String[]{this.customerTable.getSelectionModel().getSelectedItem().getName()};
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmCustomerDelete, args);
            dialog.showAndWait();
            if(dialog.getResult()) {
                //The user wishes to delete the customer and all appointments.
                Appointment[] customerAppointments = this.customerTable.getSelectionModel().getSelectedItem().getAppointments().toArray(Appointment[]::new);
                for(Appointment appointment: customerAppointments) {
                    appointment.delete();
                }
                this.customerTable.getSelectionModel().getSelectedItem().delete();
            }
        } else {
            //There are no appointments, but we need to verify that the user wants to delete the customer.
            String[] args = new String[]{this.customerTable.getSelectionModel().getSelectedItem().getName()};
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmCustomerDelete, args);
            dialog.showAndWait();
            if(dialog.getResult()) {
                this.customerTable.getSelectionModel().getSelectedItem().delete();
            }
        }
        this.customerTable.setItems(Customer.allCustomers);
    }

    /**
     * This method builds the TableView.
     * @return The Customers Table.
     */
    private TableView<Customer> buildCustomerTable() {
        TableView<Customer> table = new TableView<>(Customer.allCustomers);
        TableColumn<Customer, Integer> idCol = new TableColumn<>(lrb.getString("id"));
        TableColumn<Customer, String> nameCol = new TableColumn<>(lrb.getString("customer_name"));
        TableColumn<Customer, String> addressCol = new TableColumn<>(lrb.getString("customer_address"));
        TableColumn<Customer, Country> countryCol = new TableColumn<>(lrb.getString("country"));
        TableColumn<Customer, Division> divisionCol = new TableColumn<>(lrb.getString("division"));
        TableColumn<Customer, String> postalCol = new TableColumn<>(lrb.getString("zip_code"));
        TableColumn<Customer, String> phoneCol = new TableColumn<>(lrb.getString("phone_number"));

        //Editable Properties
        table.setEditable(true);
        nameCol.setEditable(true);
        addressCol.setEditable(true);
        countryCol.setEditable(true);
        divisionCol.setEditable(true);
        postalCol.setEditable(true);
        phoneCol.setEditable(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        countryCol.setCellValueFactory(cellData -> cellData.getValue().getCountryProperty());
        divisionCol.setCellValueFactory(cellData -> cellData.getValue().getDivisionProperty());
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(e-> e.getRowValue().setName(e.getNewValue()));
        addressCol.setOnEditCommit(e->e.getRowValue().setAddress(e.getNewValue()));
        postalCol.setOnEditCommit(e->e.getRowValue().setPostalCode(e.getNewValue()));
        phoneCol.setOnEditCommit(e->e.getRowValue().setPhone(e.getNewValue()));

        countryCol.setCellFactory(cellData -> {
            TableCell<Customer, Country> cell = new TableCell<>();
            ComboBox<Country> combo = new ComboBox<>(Country.allCountries);
            combo.valueProperty().bindBidirectional(cell.itemProperty());
            combo.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
                try {
                    cell.getTableRow().getItem().setCountry(newValue);
                } catch (Exception e) {
                    //We are ignoring the error, doesn't seem to affect the program's functionality.
                }

            }));
            combo.focusedProperty().addListener(e -> cell.getTableView().getSelectionModel().select(cell.getTableRow().getItem()));
            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(combo));
            return cell;
        });

        divisionCol.setCellFactory(cellData -> {
            TableCell<Customer, Division> cell = new TableCell<>();
            ComboBox<Division> combo = new ComboBox<>(Division.allDivisions);
            combo.valueProperty().bindBidirectional(cell.itemProperty());
            combo.focusedProperty().addListener(e->{
                cell.getTableView().getSelectionModel().select(cell.getTableRow().getItem());
                Country country = cell.getTableRow().getItem().getCountry();
                combo.setItems(Division.allDivisions.filtered(i->i.getCountry().equals(country)));
            });
            combo.valueProperty().addListener((observable, oldValue, newValue)->{
                try {
                    cell.getTableRow().getItem().setDivision(newValue);
                } catch (Exception e) {
                    //We are ignoring the error, doesn't seem to affect the program's functionality.
                }
            });
            cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(combo));
            return cell;
        });

        table.getColumns().add(idCol);
        table.getColumns().add(nameCol);
        table.getColumns().add(addressCol);
        table.getColumns().add(countryCol);
        table.getColumns().add(divisionCol);
        table.getColumns().add(postalCol);
        table.getColumns().add(phoneCol);

        table.setEditable(true);
        nameCol.setEditable(true);

        return table;
    }

    /**
     * This method removes the filter from the TableView.
     */
    private void selectAll() {
        this.customerTable.setItems(Customer.allCustomers);
    }

    /**
     * This method manages the selection of Country and Division and filters the TableView based on the selections.
     */
    private void selectByCountryAndDivision() {
        if(!this.divisionComboBox.getSelectionModel().isEmpty()) {
            //Assign filteredCustomers to customerTable
            this.customerTable.setItems(this.divisionComboBox.getValue().getCustomers());
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
                    if(customer.getDivision().equals(division)) filteredCustomers.add(customer);
                }
            }

            //Set the customerTable to the filteredList
            this.customerTable.setItems(filteredCustomers);
        } else {
            this.selectAll();
        }
    }
}
