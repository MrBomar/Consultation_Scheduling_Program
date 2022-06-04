package les.projects.consultation_scheduling_program.Views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Enums.Styles;

import java.time.ZonedDateTime;

public class AppointmentsView extends BorderPane {

    public AppointmentsView() {
        //Appointment header
        HBox header = new HBox();

            //Appointment header objects
            //First label
            Label label1 = new Label("View Appointments by:");
            label1.setFont(Styles.DefaultFont18);

            //Radio Button Group
            final ToggleGroup appointmentsSelector = new ToggleGroup();
            RadioButton currentMonth = new RadioButton("Current Month");
            RadioButton currentWeek = new RadioButton("Current Week");
            RadioButton allAppointments = new RadioButton("All Appointments");
            currentMonth.setFont(Styles.DefaultFont16);
            currentWeek.setFont(Styles.DefaultFont16);
            allAppointments.setFont(Styles.DefaultFont16);
            currentMonth.setToggleGroup(appointmentsSelector);
            currentWeek.setToggleGroup(appointmentsSelector);
            allAppointments.setToggleGroup(appointmentsSelector);

            //Adjust margins
            Insets padding = new Insets(0, 20, 30,20);
            label1.setPadding(padding);
            currentMonth.setPadding(padding);
            currentWeek.setPadding(padding);
            allAppointments.setPadding(padding);

            //Create a padding between radio group and add appointment button
            Region headerRegion = new Region();
            HBox.setHgrow(headerRegion, Priority.ALWAYS);

        //Add children to Appointment header
        header.getChildren().addAll(label1,currentMonth,currentWeek,allAppointments);

        //TableView
        TableView<Appointment> appointmentsTable = this.apptTable();
        HBox.setHgrow(appointmentsTable, Priority.ALWAYS);

        //Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(30,0,0,0));
        Region footerSpacer1 = new Region();
        Region footerSpacer2 = new Region();
        Region footerSpacer3 = new Region();
        footerSpacer1.setMinWidth(555);
        footerSpacer2.setMinWidth(30);
        footerSpacer3.setMinWidth(30);
        ButtonWide addAppointment = new ButtonWide("Add Appointment");
        //Used Lambda Here
        addAppointment.setOnMouseClicked(addAppointmentClick);
        ButtonWide updateAppointment = new ButtonWide("Update Appointment");
        //Used Lambda Here
        updateAppointment.setOnMouseClicked(updateAppointmentClick);
        ButtonWide deleteAppointment = new ButtonWide("Delete Appointment");
        //Used Lambda Here
        deleteAppointment.setOnMouseClicked(deleteAppointmentClick);
        footer.getChildren().addAll(footerSpacer1, addAppointment, footerSpacer3, updateAppointment, footerSpacer2, deleteAppointment);


        //Adjust view properties and add children to parent
        this.setPadding(new Insets(30));
        this.setBackground(Styles.BackgroundWhite);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setTop(header);
        this.setCenter(appointmentsTable);
        this.setBottom(footer);
    }

    private EventHandler<MouseEvent> addAppointmentClick = event -> {
        AddUpdateAppointment modal = new AddUpdateAppointment();
        modal.showAndWait();
    };

    private EventHandler<MouseEvent> updateAppointmentClick = event -> {
        Appointment.add("Coffee Meeting", "Discuss Contract","Java Joes", "Coffee",
                ZonedDateTime.now(), ZonedDateTime.now(),1,1);
        AddUpdateAppointment modal = new AddUpdateAppointment(Appointment.getAppointment(0));
        modal.showAndWait();
    };

    private EventHandler<MouseEvent> deleteAppointmentClick = event -> {
        DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmAppointmentCancellation);
        dialog.showAndWait();

        if(dialog.getResult() == true) {
            System.out.println("Appointment Deleted");
        } else {
            System.out.println("Nothing Deleted");
        }
    };

    private TableView<Appointment> apptTable() {
        TableView<Appointment> table = new TableView();
        table.setItems(Appointment.getAllAppointments());
        TableColumn<Appointment, Integer> idCol = new TableColumn<Appointment, Integer>("ID");
        TableColumn<Appointment, String> titleCol = new TableColumn<Appointment, String>("Title");
        TableColumn<Appointment, String> descCol = new TableColumn<Appointment, String>("Description");
        TableColumn<Appointment, String> locCol = new TableColumn<Appointment, String>("Location");
        TableColumn<Appointment, String> typeCol = new TableColumn<Appointment, String>("Type");
        TableColumn<Appointment, ZonedDateTime> startCol = new TableColumn<Appointment, ZonedDateTime>("Start");
        TableColumn<Appointment, ZonedDateTime> endCol = new TableColumn<Appointment, ZonedDateTime>("End");
        TableColumn<Appointment, Integer> customerCol = new TableColumn<Appointment, Integer>("Customer");
        TableColumn<Appointment, Integer> userCol = new TableColumn<Appointment, Integer>("User");
        TableColumn<Appointment, Integer> contactCol = new TableColumn<Appointment, Integer>("Contact");

        //Bind values to columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        table.getColumns().addAll(idCol,titleCol,descCol,locCol,typeCol,startCol,endCol,customerCol,userCol,contactCol);
        return table;
    }
}
