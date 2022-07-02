package les.projects.consultation_scheduling_program.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Components.ButtonGap;
import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.ComboBoxStyled;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.DataClasses.ReportThreeItem;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Contact Appointment Schedule report.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public final class ReportsTab3 extends VBox {
    private final ComboBoxStyled<Contact> selectContact = new ComboBoxStyled<>(Contact.allContacts);
    private final TableView<ReportThreeItem> appointments = new TableView<>();
    private final ObservableList<ReportThreeItem> scheduleItems = FXCollections.observableArrayList(new ArrayList<>());

    /**
     * This constructor instantiates the report.
     *      Two lambda expressions are user to set the Value Factories to formatted date time strings.
     *      One lambda is used to filter the items list instead of instantiating a Predicate.
     *      One lambda is used to eliminate instantiating an EventListener.
     */
    public ReportsTab3() {
        this.setMinSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setMaxSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setBackground(Styles.BackgroundWhite);

        HBox dropDownGroup = new HBox();
        Label comboBoxLabel = new Label(lrb.getString("Select_desired_contact"));
        comboBoxLabel.setFont(Styles.DefaultFont24);
        comboBoxLabel.setPadding(new Insets(0,30,30,0));
        dropDownGroup.getChildren().addAll(comboBoxLabel, selectContact);
        this.selectContact.setWidth(200);

        this.selectContact.valueProperty().addListener(e -> {
            if (this.selectContact.getSelectionModel().isEmpty() || this.selectContact.getValue() == null) {
                this.appointments.setItems(this.scheduleItems);
            } else {
                this.appointments.setItems(this.scheduleItems.filtered(i -> i.getContact().equals(this.selectContact.getValue())));
            }
        });

        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM contact_appointments");
            while(rs.next()) {
                scheduleItems.add(new ReportThreeItem(
                        Contact.getById(rs.getInt("Contact_ID")),
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Type"),
                        rs.getString("Description"),
                        DTC.timestampToZonedDateTime(rs.getObject("Start", Timestamp.class)),
                        DTC.timestampToZonedDateTime(rs.getObject("End", Timestamp.class)),
                        rs.getInt("Customer_ID")
                ));
            }
            appointments.setItems(scheduleItems);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage(
                    "Report Cannot Be Loaded","Cannot load contact_appointments.");
            dialog.showAndWait();
        }

        //Build report columns
        TableColumn<ReportThreeItem, Contact> contactCol = new TableColumn<>(lrb.getString("Contact"));
        TableColumn<ReportThreeItem, Integer> appointmentCol = new TableColumn<>(lrb.getString("Appointment"));
        TableColumn<ReportThreeItem, String> titleCol = new TableColumn<>(lrb.getString("Title"));
        TableColumn<ReportThreeItem, String> typeCol = new TableColumn<>(lrb.getString("Type"));
        TableColumn<ReportThreeItem, String> descriptionCol = new TableColumn<>(lrb.getString("Description"));
        TableColumn<ReportThreeItem, String> startCol = new TableColumn<>(lrb.getString("Start"));
        TableColumn<ReportThreeItem, String> endCol = new TableColumn<>(lrb.getString("End"));
        TableColumn<ReportThreeItem, Integer> customerCol = new TableColumn<>(lrb.getString("Customer"));

        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(x-> new SimpleStringProperty(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(x.getValue().getStart())
        ));
        endCol.setCellValueFactory(x-> new SimpleStringProperty(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(x.getValue().getEnd())
        ));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        this.appointments.getColumns().add(contactCol);
        this.appointments.getColumns().add(appointmentCol);
        this.appointments.getColumns().add(titleCol);
        this.appointments.getColumns().add(typeCol);
        this.appointments.getColumns().add(descriptionCol);
        this.appointments.getColumns().add(startCol);
        this.appointments.getColumns().add(endCol);
        this.appointments.getColumns().add(customerCol);

        ButtonStandard clear = new ButtonStandard(lrb.getString("Clear"));
        clear.setOnMouseClicked(e -> this.selectContact.setValue(null));
        dropDownGroup.getChildren().add(new ButtonGap());
        dropDownGroup.getChildren().add(clear);

        this.setPadding(Styles.Padding30px);
        this.getChildren().addAll(dropDownGroup, appointments);
    }
}
