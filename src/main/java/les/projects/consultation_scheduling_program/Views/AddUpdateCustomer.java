package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import les.projects.consultation_scheduling_program.Components.*;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.FirstLevelDivision;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class AddUpdateCustomer extends DialogBase {
    private Customer currentCustomer;
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("customer_id"), false, true);
    private final TextFieldLabeled name = new TextFieldLabeled(lrb.getString("customer_name"), true, false);
    private final TextFieldLabeled address = new TextFieldLabeled(lrb.getString("customer_address"), true, false);
    private final TextFieldLabeled zip = new TextFieldLabeled(lrb.getString("zip_code"), true, false);
    private final ComboBoxBorderPane division = new ComboBoxBorderPane(lrb.getString("division"), FirstLevelDivision.getAllDivisions(), true);
    private final TextFieldLabeled phone = new TextFieldLabeled(lrb.getString("phone_number"), true, false);

    public AddUpdateCustomer() {
        super(lrb.getString("add_new_customer"));
        this.id.setPromptText("(Auto generated)");
        this.build();
    }

    public AddUpdateCustomer(Customer customer) {
        super(lrb.getString("update_customer"));
        this.build();

        //FIXME - Insert logic to pull customer record and populate fields.
        this.currentCustomer = customer;
        this.id.setInitialValue(this.currentCustomer.getIDString());
        this.name.setInitialValue(this.currentCustomer.getCustomerName());
        this.address.setInitialValue(this.currentCustomer.getAddress());
        this.zip.setInitialValue(this.currentCustomer.getPostalCode());
        this.phone.setInitialValue(this.currentCustomer.getPhone());
        this.division.setInitialValue(FirstLevelDivision.getObjById(this.currentCustomer.getDivisionID()));
    }

    private void build() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        this.setScene(scene);

        this.division.setComboBoxWidth(200);

        VBox center = new VBox();
        center.setPadding(new Insets(30,20,30,20));
        center.getChildren().addAll(id,name,address,zip,division,phone);
        borderPane.setCenter(center);

        HBox bottom = new HBox();
        ButtonStandard save = new ButtonStandard(lrb.getString("save"));
        Pane gap = new Pane();
        gap.setMinWidth(30);
        gap.setMaxWidth(30);
        gap.setPrefWidth(30);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        bottom.getChildren().addAll(save, gap, cancel);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        borderPane.setBottom(bottom);
        borderPane.setPadding(new Insets(20,30,30,30));
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmCancel(event);
            }
        });

        this.setResizable(false);
        this.initOwner(Main.appStage);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setOnCloseRequest(confirmCancel);
    }

    private EventHandler<WindowEvent> confirmCancel = event -> {
        this.confirmCancel(event);
    };

    private void confirmCancel(Event event) {
        DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
        dialog.showAndWait();

        if(dialog.getResult() == true) {
            this.close();
        } else {
            event.consume();
            //FIXME - Insert logic to save changes to the database
        }
    }
}
