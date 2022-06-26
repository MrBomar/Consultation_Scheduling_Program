package les.projects.consultation_scheduling_program.Views;

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
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.DataClasses.ReportTwoItem;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * Customer Appointment Schedule.
 */
public final class ReportsTab2 extends VBox {
    private final ComboBoxStyled<Customer> selectCustomer = new ComboBoxStyled<>(Customer.allCustomers);
    private final TableView<ReportTwoItem> appointments = new TableView<>();
    private final ObservableList<ReportTwoItem> allItems = FXCollections.observableArrayList(new ArrayList<>());

    public ReportsTab2() {
        this.setMinSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setMaxSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setBackground(Styles.BackgroundWhite);

        HBox dropDownGroup = new HBox();
        Label comboBoxLabel = new Label(lrb.getString("select_desired_customer"));
        comboBoxLabel.setFont(Styles.DefaultFont24);
        comboBoxLabel.setPadding(new Insets(0,30,30,0));
        dropDownGroup.getChildren().addAll(comboBoxLabel, selectCustomer);
        this.selectCustomer.setWidth(200);

        this.selectCustomer.valueProperty().addListener(e -> {
            if (this.selectCustomer.getSelectionModel().isEmpty() || this.selectCustomer.getValue() == null) {
                this.appointments.setItems(this.allItems);
            } else {
                this.appointments.setItems(this.allItems.filtered(i -> i.getCustomer().equals(this.selectCustomer.getValue())));
            }
        });

        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM customer_appointment_schedule");
            while(rs.next()) {
                allItems.add(new ReportTwoItem(
                        Customer.getById(rs.getInt("Customer_ID")),
                        Contact.getById(rs.getInt("Contact_ID")),
                        rs.getString("Type"),
                        rs.getString("Title"),
                        DTC.timestampToZonedDateTime(rs.getTimestamp("Start")),
                        DTC.timestampToZonedDateTime(rs.getTimestamp("End"))
                ));
            }
            this.appointments.setItems(this.allItems);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage(
                    "Report Cannot Be Loaded","Cannot load customer_appointment_schedule.");
            dialog.showAndWait();
        }

        TableColumn<ReportTwoItem, Customer> customerCol = new TableColumn<>(lrb.getString("customer"));
        TableColumn<ReportTwoItem, Contact> contactCol = new TableColumn<>(lrb.getString("contact"));
        TableColumn<ReportTwoItem, String> typeCol = new TableColumn<>(lrb.getString("type"));
        TableColumn<ReportTwoItem, String> titleCol = new TableColumn<>(lrb.getString("title"));
        TableColumn<ReportTwoItem, ZonedDateTime> startCol = new TableColumn<>(lrb.getString("start"));
        TableColumn<ReportTwoItem, ZonedDateTime> endCol = new TableColumn<>(lrb.getString("end"));

        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        this.appointments.getColumns().add(customerCol);
        this.appointments.getColumns().add(contactCol);
        this.appointments.getColumns().add(typeCol);
        this.appointments.getColumns().add(titleCol);
        this.appointments.getColumns().add(startCol);
        this.appointments.getColumns().add(endCol);

        ButtonStandard clear = new ButtonStandard("Clear");
        clear.setOnMouseClicked(e-> this.selectCustomer.setValue(null));
        dropDownGroup.getChildren().add(new ButtonGap());
        dropDownGroup.getChildren().add(clear);

        this.setPadding(Styles.Padding30px);
        this.getChildren().addAll(dropDownGroup, appointments);
    }
}
