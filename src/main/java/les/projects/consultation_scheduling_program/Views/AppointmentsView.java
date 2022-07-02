package les.projects.consultation_scheduling_program.Views;

import javafx.beans.property.SimpleStringProperty;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Appointments view.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class AppointmentsView extends BorderPane {
    private final TableView<Appointment> appointmentTable = new TableView<>(Appointment.allAppointments);

    /**
     * This constructor instantiates the Appointments view.
     */
    public AppointmentsView() {
        //Appointment header
        HBox header = new HBox();

            //Appointment header objects
            //First label
            Label label1 = new Label(lrb.getString("View_Appointments_by"));
            label1.setFont(Styles.DefaultFont18);

            //Radio Button Group
            final ToggleGroup appointmentsSelector = new ToggleGroup();
            RadioButton currentMonth = new RadioButton(lrb.getString("Current_Month"));
            RadioButton currentWeek = new RadioButton(lrb.getString("Current_Week"));
            RadioButton allAppointments = new RadioButton(lrb.getString("All_Appointments"));
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
        ButtonWide addAppointment = new ButtonWide(lrb.getString("Add_Appointment"));
        //Used Lambda Here
        addAppointment.setOnMouseClicked(this::addAppointmentClick);
        ButtonWide updateAppointment = new ButtonWide(lrb.getString("Update_Appointment"));
        //Used Lambda Here
        updateAppointment.setOnMouseClicked(this::updateAppointmentClick);
        ButtonWide deleteAppointment = new ButtonWide(lrb.getString("Delete_Appointment"));
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

    /**
     * This method opens the Add/Update Appointment modal in add mode.
     * @param event The mouse event.
     */
    private void addAppointmentClick(Event event) {
        AddUpdateAppointment modal = new AddUpdateAppointment();
        modal.showAndWait();
        appointmentTable.setItems(Appointment.allAppointments);
    }

    /**
     * This method opens the Add/Update Appointment modal in update mode, using the object selected in the TableView.
     * @param event The mouse event.
     */
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

    /**
     * This method calls the delete() method on the Appointment object selected in the TableView.
     * @param event The mouse event.
     */
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

    /**
     * This method constructs the table columns. Two lambda expressions are used in the method for setting the
     * CellValueFactory. Using these expressions prevented me from having to create CallBacks which are for more
     * verbose than the lambda expressions. Using the lambda expressions allows the code to be more concise and
     * readable.
     */
    private void buildTable() {
        TableColumn<Appointment, Integer> idCol = new TableColumn<>(lrb.getString("ID"));
        TableColumn<Appointment, String> titleCol = new TableColumn<>(lrb.getString("Title"));
        TableColumn<Appointment, String> descCol = new TableColumn<>(lrb.getString("Description"));
        TableColumn<Appointment, String> locCol = new TableColumn<>(lrb.getString("Location"));
        TableColumn<Appointment, String> typeCol = new TableColumn<>(lrb.getString("Type"));
        TableColumn<Appointment, String> startCol = new TableColumn<>(lrb.getString("Start"));
        TableColumn<Appointment, String> endCol = new TableColumn<>(lrb.getString("End"));
        TableColumn<Appointment, Customer> customerCol = new TableColumn<>(lrb.getString("Customer"));
        TableColumn<Appointment, User> userCol = new TableColumn<>(lrb.getString("User"));
        TableColumn<Appointment, Contact> contactCol = new TableColumn<>(lrb.getString("Contact"));

        //Bind values to columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(
                x -> new SimpleStringProperty(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(x.getValue().getStart()))
        );
        endCol.setCellValueFactory(
                x -> new SimpleStringProperty(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(x.getValue().getEnd()))
        );
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

    /**
     * This method checks the system date and filters all appointments for appointments occurring in the current month.
     */
    private void selectCurrentMonth() {
        Appointment[] filteredAppointments = Appointment.allAppointments.stream()
                .filter(i -> i.getStart().getMonth().equals(LocalDate.now().getMonth())).toArray(Appointment[]::new);
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(filteredAppointments);
        this.appointmentTable.setItems(observableAppointments);
    }

    /**
     * This method checks the system date and filters all appointments for appointments occurring in the current week.
     */
    private void selectCurrentWeek() {
        Appointment[] filteredAppointments = Appointment.allAppointments.stream().filter(i ->
            WeekComparator.isSameWeek(i.getStart(), ZonedDateTime.now())).toArray(Appointment[]::new);
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(filteredAppointments);
        this.appointmentTable.setItems(observableAppointments);
    }

    /**
     * This method removes the filter from the appointments list.
     */
    private void selectAll() {
        this.appointmentTable.setItems(Appointment.allAppointments);
    }
}
