package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import les.projects.consultation_scheduling_program.Components.*;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.Division;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class AddUpdateCustomer extends DialogBase {
    private Customer currentCustomer;
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("customer_id"), false, true);
    private final TextFieldLabeled name = new TextFieldLabeled(lrb.getString("customer_name"), true, false);
    private final TextFieldLabeled address = new TextFieldLabeled(lrb.getString("customer_address"), true, false);
    private final TextFieldLabeled zip = new TextFieldLabeled(lrb.getString("zip_code"), true, false);
    private final ComboBoxStyled<Division> division = new ComboBoxStyled<>(Division.allDivisions, "");
    private final TextFieldLabeled phone = new TextFieldLabeled(lrb.getString("phone_number"), true, false);
    private final ComboBoxStyled<Country> country = new ComboBoxStyled<>(Country.allCountries, "");
    private final TableView<Customer> customerTableView;

    public AddUpdateCustomer(TableView<Customer> customerTableView) {
        super(lrb.getString("add_new_customer"));
        this.id.setPromptText("(Auto generated)");
        this.customerTableView = customerTableView;
        this.build();
    }

    public AddUpdateCustomer(TableView<Customer> customerTableView, Customer customer) {
        super(lrb.getString("update_customer"));
        this.customerTableView = customerTableView;
        this.build();

        //FIXME - Insert logic to pull customer record and populate fields.
        this.currentCustomer = customer;
        this.id.setInitialValue(this.currentCustomer.getIDString());
        this.name.setInitialValue(this.currentCustomer.getCustomerName());
        this.address.setInitialValue(this.currentCustomer.getAddress());
        this.zip.setInitialValue(this.currentCustomer.getPostalCode());
        this.phone.setInitialValue(this.currentCustomer.getPhone());
        Division div = Division.getDivisionById(this.currentCustomer.getDivisionID());
        this.division.setValue(div);
        this.country.setValue(Country.getCountryByID(div.getCountryId()));
    }

    private void build() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        this.setScene(scene);

        //Adding ComboBoxes and their containers.
        BorderPaneStyled countryPane = new BorderPaneStyled("Country", true);
        countryPane.setRight(this.country);
        BorderPaneStyled divisionPane = new BorderPaneStyled("Division", true);
        divisionPane.setRight(this.division);
        this.country.setWidth(200);
        this.division.setWidth(200);

        //Add listener to country ComboBox
        country.valueProperty().addListener(((observableValue, country1, t1) -> division.setItems(country.getValue().getDivisions())));

        VBox center = new VBox();
        center.setPadding(new Insets(30,20,30,20));
        center.getChildren().addAll(id,name,address,countryPane,divisionPane,zip,phone);
        borderPane.setCenter(center);

        HBox bottom = new HBox();
        ButtonStandard save = new ButtonStandard(lrb.getString("save"));
        save.setOnMouseClicked(this::saveClick);
        Pane gap = new Pane();
        gap.setMinWidth(30);
        gap.setMaxWidth(30);
        gap.setPrefWidth(30);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        cancel.setOnMouseClicked(this::confirmCancel);
        bottom.getChildren().addAll(save, gap, cancel);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        borderPane.setBottom(bottom);
        borderPane.setPadding(new Insets(20,30,30,30));
        cancel.setOnMouseClicked(this::confirmCancel);

        this.setResizable(false);
        this.initOwner(Main.appStage);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setOnCloseRequest(this::confirmCancel);
    }

    private void saveClick(Event e) {
        if(this.changesHaveBeenMade()) {
            if(this.requiredFieldsFilled()) {
                if(currentCustomer == null) {
                    //Add new Customer
                    Customer.add(
                            this.name.getInput(),
                            this.address.getInput(),
                            this.zip.getInput(),
                            this.phone.getInput(),
                            this.division.getValue()
                    );
                } else {
                    this.currentCustomer.update(
                            this.name.getInput(),
                            this.address.getInput(),
                            this.zip.getInput(),
                            this.phone.getInput(),
                            this.division.getValue()
                    );
                }
                DialogMessage dialog = new DialogMessage(Message.RecordSaved);
                dialog.showAndWait();
                this.close();
                this.customerTableView.refresh();
            } else {
                //We have fields that require filling out.
                DialogMessage dialog = new DialogMessage(Message.InvalidInput);
                dialog.showAndWait();
            }
        } else {
            //No changes were detects
            DialogMessage dialog = new DialogMessage(Message.NothingChanged);
            dialog.showAndWait();
        }
    }

    private void confirmCancel(Event event) {
        if(this.changesHaveBeenMade()) {
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
            dialog.showAndWait();

            if (dialog.getResult()) {
                this.close();
            } else {
                event.consume();
                //FIXME - Insert logic to save changes to the database
            }
        } else {
            this.close();
        }
    }

    private boolean changesHaveBeenMade() {
        return this.name.isChanged() || this.address.isChanged() || this.zip.isChanged() || this.phone.isChanged() ||
                this.division.isChanged();
    }

    private boolean requiredFieldsFilled() {
        if(!this.name.isNotBlank()) {
            //Name is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "Customer name cannot be blank.");
            dialog.showAndWait();
            return false;
        } else if (!this.address.isNotBlank()) {
            //Address is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "Address cannot be blank.");
            dialog.showAndWait();
            return false;
        } else if (!this.zip.isNotBlank()) {
            //Zip is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "Zip Code cannot be blank.");
            dialog.showAndWait();
            return false;
        } else if (!this.phone.isNotBlank()) {
            //Phone is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "Phone number cannot be blank.");
            dialog.showAndWait();
            return false;
        } else if (this.division.getSelectionModel().isEmpty()) {
            //Division is not selected
            DialogMessage dialog = new DialogMessage("Invalid Input", "No division is selected. Please select a division.");
            dialog.showAndWait();
            return false;
        } else {
            return true;
        }
    }
}
