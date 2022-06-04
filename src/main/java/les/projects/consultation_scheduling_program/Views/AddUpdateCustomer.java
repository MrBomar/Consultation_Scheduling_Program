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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.TextFieldLabeled;
import les.projects.consultation_scheduling_program.Components.Title;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Main;

public class AddUpdateCustomer extends Stage {

    public AddUpdateCustomer() {
        this.build("Add New Customer");
    }

    public AddUpdateCustomer(int appointmentId) {
        this.build("Update Customer");
    }

    private void build(String windowTitle) {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        this.setScene(scene);

        borderPane.setTop(new Title(windowTitle));

        VBox center = new VBox();
        TextFieldLabeled id = new TextFieldLabeled("Customer ID");
        TextFieldLabeled name = new TextFieldLabeled("Customer Name");
        TextFieldLabeled address = new TextFieldLabeled("Address");
        TextFieldLabeled zip = new TextFieldLabeled("ZIP Code");
        TextFieldLabeled country = new TextFieldLabeled("Country");
        TextFieldLabeled division = new TextFieldLabeled("Division");
        TextFieldLabeled phone = new TextFieldLabeled("Phone Number");
        center.setPadding(new Insets(30,20,30,20));
        center.getChildren().addAll(id,name,address,zip,country,division,phone);
        borderPane.setCenter(center);

        HBox bottom = new HBox();
        ButtonStandard save = new ButtonStandard("Save");
        Pane gap = new Pane();
        gap.setMinWidth(30);
        gap.setMaxWidth(30);
        gap.setPrefWidth(30);
        ButtonStandard cancel = new ButtonStandard("Cancel");
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
