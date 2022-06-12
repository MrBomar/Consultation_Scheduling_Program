package les.projects.consultation_scheduling_program.Views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
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
    private final ToggleGroup radioGroup = new ToggleGroup();
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
                    Label divisionLabel = new Label(lrb.getString("and_first_level_division"));

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
            this.countryRadio.setToggleGroup(this.radioGroup);
            this.allCustomersRadio.setToggleGroup(this.radioGroup);
            this.allCustomersRadio.setSelected(true);

            //Add listener to the ToggleGroup
            this.radioGroup.selectedToggleProperty().addListener((a,b,newValue) -> {
                if(newValue.equals(allCustomersRadio)) {
                    this.selectAll();
                } else {
                    this.selectByCountryAndDivision();
                }
            });

            //Add listener to ComboBox(s)
            this.countryComboBox.valueProperty().addListener(new ChangeListener<Country>() {
                @Override
                public void changed(ObservableValue<? extends Country> observableValue, Country country, Country t1) {
                    countryRadio.setSelected(true);
                    divisionComboBox.setItems(countryComboBox.getValue().getDivisions());
                    selectByCountryAndDivision();
                }
            });

            this.divisionComboBox.valueProperty().addListener(new ChangeListener<Division>() {
                @Override
                public void changed(ObservableValue<? extends Division> observableValue, Division division, Division t1) {
                    countryRadio.setSelected(true);
                    selectByCountryAndDivision();
                }
            });

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
        addCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addCustomer();
            }
        });
        ButtonWide updateCustomer = new ButtonWide(lrb.getString("update_customer"));
        updateCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateCustomer();
            }
        });
        ButtonWide deleteCustomer = new ButtonWide(lrb.getString("delete_customer"));
        footer.getChildren().addAll(footerSpacer1, addCustomer, footerSpacer3, updateCustomer, footerSpacer2, deleteCustomer);

        //Adjust view properties and add children to parent
        this.setPadding(new Insets(30));
        this.setBackground(Styles.BackgroundWhite);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setTop(header);
        this.setCenter(customerTable);
        this.setBottom(footer);
    }

    private void addCustomer() {
        AddUpdateCustomer modal = new AddUpdateCustomer();
        modal.showAndWait();
    }

    private void updateCustomer() {
        if(this.customerTable.getSelectionModel().getSelectedItems().stream().count() > 0) {
            Customer customer = (Customer) this.customerTable.getSelectionModel().getSelectedItem();
            AddUpdateCustomer modal = new AddUpdateCustomer(customer);
            modal.showAndWait();
        } else {
            DialogMessage dialog = new DialogMessage(Message.NoSelectedCustomer);
            dialog.showAndWait();
        }
    }

    private TableView<Customer> buildCustomerTable() {
        TableView<Customer> table = new TableView<>(Customer.allCustomers);
        TableColumn<Customer, Integer> idCol = new TableColumn<>(lrb.getString("id"));
        TableColumn<Customer, Integer> nameCol = new TableColumn<>(lrb.getString("customer_name"));
        TableColumn<Customer, Integer> addressCol = new TableColumn<>(lrb.getString("customer_address"));
        TableColumn<Customer, Integer> postalCol = new TableColumn<>(lrb.getString("zip_code"));
        TableColumn<Customer, Integer> phoneCol = new TableColumn<>(lrb.getString("phone_number"));
        TableColumn<Customer, Integer> divisionCol = new TableColumn<>(lrb.getString("division"));

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        table.getColumns().addAll(idCol,nameCol,addressCol,postalCol,phoneCol,divisionCol);
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
