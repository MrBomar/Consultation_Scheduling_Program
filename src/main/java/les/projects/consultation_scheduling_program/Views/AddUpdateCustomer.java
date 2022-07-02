package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

/**
 * This class renders the Add/Update Customer dialog.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class AddUpdateCustomer extends DialogBase {
    private Customer currentCustomer;
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("Customer_ID"), false, true);
    private final TextFieldLabeled name = new TextFieldLabeled(lrb.getString("Customer_Name"), true, false);
    private final TextFieldLabeled address = new TextFieldLabeled(lrb.getString("Customer_Address"), true, false);
    private final TextFieldLabeled zip = new TextFieldLabeled(lrb.getString("ZIP_Code"), true, false);
    private final ComboBoxStyled<Division> division = new ComboBoxStyled<>(Division.allDivisions, true);
    private final TextFieldLabeled phone = new TextFieldLabeled(lrb.getString("Phone_Number"), true, false);
    private final ComboBoxStyled<Country> country = new ComboBoxStyled<>(Country.allCountries, true);

    /**
     * This constructor creates an instance of the Add/Update Customer modal in add mode.
     */
    public AddUpdateCustomer() {
        super(lrb.getString("Add_New_Customer"));
        this.id.setPromptText("(" + lrb.getString("Auto-generated") + ")");
        this.build();
    }

    /**
     * This constructor creates an instance of the Add/Update Customer modal in update mode.
     * @param customer The customer object to be edited.
     */
    public AddUpdateCustomer( Customer customer) {
        super(lrb.getString("Update_Customer"));
        this.build();

        this.currentCustomer = customer;
        this.id.setInitialValue(this.currentCustomer.getId().toString());
        this.name.setInitialValue(this.currentCustomer.getName());
        this.address.setInitialValue(this.currentCustomer.getAddress());
        this.zip.setInitialValue(this.currentCustomer.getPostalCode());
        this.phone.setInitialValue(this.currentCustomer.getPhone());
        this.division.setValue(this.currentCustomer.getDivision());
        this.country.setValue(this.currentCustomer.getCountry());
    }

    /**
     * This method builds the form and applies formatting.
     */
    private void build() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        this.setScene(scene);

        //Adding ComboBoxes and their containers.
        BorderPaneStyled countryPane = new BorderPaneStyled(lrb.getString("Country"), true);
        countryPane.setRight(this.country);
        BorderPaneStyled divisionPane = new BorderPaneStyled(lrb.getString("Division"), true);
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
        ButtonStandard save = new ButtonStandard(lrb.getString("Save"));
        save.setOnMouseClicked(this::saveClick);
        Pane gap = new Pane();
        gap.setMinWidth(30);
        gap.setMaxWidth(30);
        gap.setPrefWidth(30);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("Cancel"));
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

    /**
     * This method verifies all fields are filled out and that the data is valid, then calls the save methods
     * in the Customer class.
     * @param e The event generated on click.
     */
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

    /**
     * This method asks the user if they want to discard changes before exiting the form. If they decline to discard
     * said changes then the form stays open.
     * @param event The event generated on close.
     */
    private void confirmCancel(Event event) {
        if(this.changesHaveBeenMade()) {
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
            dialog.showAndWait();

            if (dialog.getResult()) {
                this.close();
            } else {
                event.consume();
            }
        } else {
            this.close();
        }
    }

    /**
     * This method checks each input element on the form to ensure that changes have been made.
     * @return If the data on the form has changed then method returns true.
     */
    private boolean changesHaveBeenMade() {
        return this.name.isChanged() || this.address.isChanged() || this.zip.isChanged() || this.phone.isChanged() ||
                this.division.isChanged();
    }

    /**
     * This method checks each input field to make sure they meet data requirements.
     * @return Returns true if all fields are properly filled out.
     */
    private boolean requiredFieldsFilled() {
        if(!this.name.isNotBlank()) {
            //Name is blank
            DialogMessage dialog = new DialogMessage(
                    Message.InvalidInputEntered,
                    new String[] {
                            lrb.getString("customer_name"),
                            lrb.getString("customer_name")
                    }
            );
            dialog.showAndWait();
            return false;
        } else if (!this.address.isNotBlank()) {
            //Address is blank
            DialogMessage dialog = new DialogMessage(
                    Message.InvalidInputEntered,
                    new String[] {
                            lrb.getString("address"),
                            lrb.getString("address")
                    }
            );
            dialog.showAndWait();
            return false;
        } else if (!this.zip.isNotBlank()) {
            //Zip is blank
            DialogMessage dialog = new DialogMessage(
                    Message.InvalidInputEntered,
                    new String[] {
                            lrb.getString("zip_code"),
                            lrb.getString("zip_code")
                    }
            );
            dialog.showAndWait();
            return false;
        } else if (!this.phone.isNotBlank()) {
            //Phone is blank
            DialogMessage dialog = new DialogMessage(
                    Message.InvalidInputEntered,
                    new String[] {
                            lrb.getString("phone_number"),
                            lrb.getString("phone_number")
                    }
            );
            dialog.showAndWait();
            return false;
        } else if (this.division.getSelectionModel().isEmpty()) {
            //Division is not selected
            DialogMessage dialog = new DialogMessage(
                    Message.InvalidInputSelected,
                    new String[] {
                            lrb.getString("division"),
                            lrb.getString("division")
                    }
            );
            dialog.showAndWait();
            return false;
        } else {
            return true;
        }
    }
}
