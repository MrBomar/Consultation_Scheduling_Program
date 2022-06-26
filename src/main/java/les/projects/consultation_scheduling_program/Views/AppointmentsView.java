package les.projects.consultation_scheduling_program.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonWide;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.User;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Helpers.WeekComparator;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class AppointmentsView extends BorderPane {
    private final TableView<Appointment> appointmentTable = new TableView<>(Appointment.allAppointments);

    public AppointmentsView() {
        //Appointment header
        HBox header = new HBox();

            //Appointment header objects
            //First label
            Label label1 = new Label(lrb.getString("view_appointments_by"));
            label1.setFont(Styles.DefaultFont18);

            //Radio Button Group
            final ToggleGroup appointmentsSelector = new ToggleGroup();
            RadioButton currentMonth = new RadioButton(lrb.getString("current_month"));
            RadioButton currentWeek = new RadioButton(lrb.getString("current_week"));
            RadioButton allAppointments = new RadioButton(lrb.getString("all_appointments"));
            currentMonth.setFont(Styles.DefaultFont16);
            currentWeek.setFont(Styles.DefaultFont16);
            allAppointments.setFont(Styles.DefaultFont16);
            currentMonth.setToggleGroup(appointmentsSelector);
            currentWeek.setToggleGroup(appointmentsSelector);
            allAppointments.setToggleGroup(appointmentsSelector);
            allAppointments.setSelected(true);

            //Set listeners on radio buttons
            appointmentsSelector.selectedToggleProperty().addListener((observableValue, toggle, newVal) -> {
                RadioButton rb = (RadioButton) newVal;
                if(rb.equals(currentMonth)){
                    selectCurrentMonth();
                } else if (rb.equals(currentWeek)) {
                    selectCurrentWeek();
                } else {
                    selectAll();
                }
            });

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
        header.getChildren().addAll(label1,allAppointments,currentMonth,currentWeek);

        //TableView
        this.buildTable();
        HBox.setHgrow(this.appointmentTable, Priority.ALWAYS);

        //Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(30,0,0,0));
        Region footerSpacer1 = new Region();
        Region footerSpacer2 = new Region();
        Region footerSpacer3 = new Region();
        footerSpacer1.setMinWidth(555);
        footerSpacer2.setMinWidth(30);
        footerSpacer3.setMinWidth(30);
        ButtonWide addAppointment = new ButtonWide(lrb.getString("add_appointment"));
        //Used Lambda Here
        addAppointment.setOnMouseClicked(this::addAppointmentClick);
        ButtonWide updateAppointment = new ButtonWide(lrb.getString("update_appointment"));
        //Used Lambda Here
        updateAppointment.setOnMouseClicked(this::updateAppointmentClick);
        ButtonWide deleteAppointment = new ButtonWide(lrb.getString("delete_appointment"));
        //Used Lambda Here
        deleteAppointment.setOnMouseClicked(this::deleteAppointmentClick);
        footer.getChildren().addAll(footerSpacer1, addAppointment, footerSpacer3, updateAppointment, footerSpacer2, deleteAppointment);

        //Adjust view properties and add children to parent
        this.setPadding(new Insets(30));
        this.setBackground(Styles.BackgroundWhite);
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setTop(header);
        this.setCenter(this.appointmentTable);
        this.setBottom(footer);
    }

    private void addAppointmentClick(Event event) {
        AddUpdateAppointment modal = new AddUpdateAppointment();
        modal.showAndWait();
        appointmentTable.setItems(Appointment.allAppointments);
    }

    private void updateAppointmentClick (Event event) {
        if((long) this.appointmentTable.getSelectionModel().getSelectedItems().size() > 0) {
            AddUpdateAppointment modal = new AddUpdateAppointment(this.appointmentTable.getSelectionModel().getSelectedItem());
            modal.showAndWait();
            appointmentTable.setItems(Appointment.allAppointments);
        } else {
            DialogMessage dialog = new DialogMessage(Message.NoAppointmentSelected);
            dialog.showAndWait();
        }
    }

    private void deleteAppointmentClick (Event event) {
        if(!this.appointmentTable.getSelectionModel().isEmpty()) {
            String[] args = new String[] {
                    "" + this.appointmentTable.getSelectionModel().getSelectedItem().getId(),
                    this.appointmentTable.getSelectionModel().getSelectedItem().getType()
            };
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmAppointmentCancellation, args);
            dialog.showAndWait();
            if(dialog.getResult().equals(true)) {
                this.appointmentTable.getSelectionModel().getSelectedItem().delete();
                this.appointmentTable.refresh();
                DialogMessage dm = new DialogMessage(Message.AppointmentCanceled, args);
                dm.showAndWait();
                appointmentTable.setItems(Appointment.allAppointments);
            }
        } else {
            DialogMessage dialog = new DialogMessage(Message.NoAppointmentSelected);
            dialog.showAndWait();
        }
    }

    private void buildTable() {
        TableColumn<Appointment, Integer> idCol = new TableColumn<>(lrb.getString("id"));
        TableColumn<Appointment, String> titleCol = new TableColumn<>(lrb.getString("title"));
        TableColumn<Appointment, String> descCol = new TableColumn<>(lrb.getString("description"));
        TableColumn<Appointment, String> locCol = new TableColumn<>(lrb.getString("location"));
        TableColumn<Appointment, String> typeCol = new TableColumn<>(lrb.getString("type"));
        TableColumn<Appointment, ZonedDateTime> startCol = new TableColumn<>(lrb.getString("start"));
        TableColumn<Appointment, ZonedDateTime> endCol = new TableColumn<>(lrb.getString("end"));
        TableColumn<Appointment, Customer> customerCol = new TableColumn<>(lrb.getString("customer"));
        TableColumn<Appointment, User> userCol = new TableColumn<>(lrb.getString("user"));
        TableColumn<Appointment, Contact> contactCol = new TableColumn<>(lrb.getString("contact"));

        //Bind values to columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        this.appointmentTable.getColumns().add(idCol);
        this.appointmentTable.getColumns().add(titleCol);
        this.appointmentTable.getColumns().add(descCol);
        this.appointmentTable.getColumns().add(locCol);
        this.appointmentTable.getColumns().add(typeCol);
        this.appointmentTable.getColumns().add(startCol);
        this.appointmentTable.getColumns().add(endCol);
        this.appointmentTable.getColumns().add(customerCol);
        this.appointmentTable.getColumns().add(userCol);
        this.appointmentTable.getColumns().add(contactCol);
    }

    private void selectCurrentMonth() {
        Appointment[] filteredAppointments = Appointment.allAppointments.stream().filter(i -> i.getStart().getMonth().equals(LocalDate.now().getMonth())).toArray(Appointment[]::new);
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(filteredAppointments);
        this.appointmentTable.setItems(observableAppointments);
    }

    private void selectCurrentWeek() {
        Appointment[] filteredAppointments = Appointment.allAppointments.stream().filter(i ->
            WeekComparator.isSameWeek(i.getStart(), ZonedDateTime.now())).toArray(Appointment[]::new);
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(filteredAppointments);
        this.appointmentTable.setItems(observableAppointments);
    }

    private void selectAll() {
        this.appointmentTable.setItems(Appointment.allAppointments);
    }
}
