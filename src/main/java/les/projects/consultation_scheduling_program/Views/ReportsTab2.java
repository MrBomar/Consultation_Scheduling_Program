package les.projects.consultation_scheduling_program.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Components.ComboBoxStyled;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Main;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * Customer Appointment Schedule.
 */
public class ReportsTab2 extends VBox {
    private ComboBoxStyled<Customer> selectCustomer = new ComboBoxStyled<Customer>(Customer.allCustomers);
    private TableView appointments = new TableView();

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

        this.setPadding(Styles.Padding30px);
        this.getChildren().addAll(dropDownGroup, appointments);
    }
}
