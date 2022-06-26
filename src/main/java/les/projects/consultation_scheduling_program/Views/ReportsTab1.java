package les.projects.consultation_scheduling_program.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.DataClasses.ReportOneItem;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;

import java.sql.ResultSet;
import java.time.Month;
import java.util.ArrayList;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * Customer Appointments by Type and Month.
 */
public class ReportsTab1 extends VBox {

    public ReportsTab1() {
        TableView<ReportOneItem> appointments = new TableView<>();
        this.setMinSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setMaxSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setBackground(Styles.BackgroundWhite);

        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM appointments_by_type_and_month");

            ObservableList<ReportOneItem> items = FXCollections.observableArrayList(new ArrayList<>());
            while(rs.next()) {
                items.add(new ReportOneItem(
                        rs.getString("Type"),
                        Month.of(rs.getInt("Month")).toString(),
                        rs.getInt("Count")
                ));
            }
            appointments.setItems(items);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage(
                    "Report Cannot Be Loaded","Cannot load appointments_by_type_and_month.");
            dialog.showAndWait();
        }

        //Build report columns
        TableColumn<ReportOneItem, String> typeCol = new TableColumn<>(lrb.getString("type"));
        TableColumn<ReportOneItem, Integer> monthCol = new TableColumn<>("Month");
        TableColumn<ReportOneItem, Integer> countCol = new TableColumn<>("Count");

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        appointments.getColumns().add(typeCol);
        appointments.getColumns().add(monthCol);
        appointments.getColumns().add(countCol);

        this.setPadding(Styles.Padding30px);
        this.getChildren().addAll(appointments);
    }
}
