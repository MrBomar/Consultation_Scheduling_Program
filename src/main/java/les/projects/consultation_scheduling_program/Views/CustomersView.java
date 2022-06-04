package les.projects.consultation_scheduling_program.Views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
import les.projects.consultation_scheduling_program.Components.ComboBoxSelector;
import les.projects.consultation_scheduling_program.DataClasses.Country;
import les.projects.consultation_scheduling_program.DataClasses.FirstLevelDivision;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class CustomersView extends BorderPane {
    private final ComboBoxSelector countryDropdown = new ComboBoxSelector(Country.getAllCountries());
    private final ComboBoxSelector divisionDropdown = new ComboBoxSelector(FirstLevelDivision.getAllDivisions());
    private final RadioButton countryRadio = new RadioButton("Country");
    private final RadioButton allCustomersRadio = new RadioButton("All Customers");
    private final ToggleGroup radioGroup = new ToggleGroup();

    public CustomersView() {
        //Customer header
        HBox header = new HBox();

            //Customer header objects
            //First label
            Label label1 = new Label("View Customers by:");
            label1.setFont(Styles.DefaultFont18);
            Insets padding = new Insets(0,20,30,20);
            label1.setPadding(padding);

            //Radio Button Group
            VBox selectorGroup = new VBox();

                //Dropdown selectors
                HBox dropDownGroup = new HBox();

                    //Dropdown group items
                    Label divisionLabel = new Label("and First-Level Division");

                    //Set fonts
                    this.countryRadio.setFont(Styles.DefaultFont16);
                    divisionLabel.setFont(Styles.DefaultFont16);

                    //Add padding to dropdowns
                    this.countryRadio.setPadding(padding);
                    divisionLabel.setPadding(padding);

                    //Add items to dropdown group
                    dropDownGroup.getChildren().addAll(this.countryRadio, this.countryDropdown,divisionLabel,this.divisionDropdown);

                //Selector group items
                this.allCustomersRadio.setPadding(padding);
                this.allCustomersRadio.setFont(Styles.DefaultFont16);

            //Add radio buttons to group
            this.countryRadio.setToggleGroup(this.radioGroup);
            this.allCustomersRadio.setToggleGroup(this.radioGroup);

            //Add children to selector group
            selectorGroup.getChildren().addAll(dropDownGroup,this.allCustomersRadio);

            //Add children to header
            header.getChildren().addAll(label1, selectorGroup);

        //TableView
        TableView customers = new TableView();
        HBox.setHgrow(customers, Priority.ALWAYS);

        //Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(30,0,0,0));
        Region footerSpacer1 = new Region();
        Region footerSpacer2 = new Region();
        Region footerSpacer3 = new Region();
        footerSpacer1.setMinWidth(555);
        footerSpacer2.setMinWidth(30);
        footerSpacer3.setMinWidth(30);
        ButtonWide addCustomer = new ButtonWide("Add Customer");
        addCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addCustomer();
            }
        });
        ButtonWide updateCustomer = new ButtonWide("Update Customer");
        updateCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateCustomer();
            }
        });
        ButtonWide deleteCustomer = new ButtonWide("Delete Customer");
        footer.getChildren().addAll(footerSpacer1, addCustomer, footerSpacer3, updateCustomer, footerSpacer2, deleteCustomer);

        //Adjust view properties and add children to parent
        this.setPadding(new Insets(30));
        this.setBackground(Styles.BackgroundWhite);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setTop(header);
        this.setCenter(customers);
        this.setBottom(footer);
    }

    private void addCustomer() {
        AddUpdateCustomer modal = new AddUpdateCustomer();
        modal.showAndWait();
    }

    private void updateCustomer() {
        AddUpdateCustomer modal = new AddUpdateCustomer(1);
        modal.showAndWait();
    }
}
