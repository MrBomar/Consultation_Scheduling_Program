package les.projects.consultation_scheduling_program.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Components.ComboBoxSelector;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class ReportsTab1 extends VBox {
    private ComboBoxSelector selectContact = new ComboBoxSelector(Customer.getAllCustomers());
    private TableView appointments = new TableView();

    public ReportsTab1() {
        this.setMinSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setMaxSize(Main.appWidth - 313, Main.appHeight - 70);
        this.setBackground(Styles.BackgroundWhite);

        HBox dropDownGroup = new HBox();
            Label comboBoxLabel = new Label(lrb.getString("select_desired_customer"));
            comboBoxLabel.setFont(Styles.DefaultFont24);
            comboBoxLabel.setPadding(new Insets(0,30,30,0));
            dropDownGroup.getChildren().addAll(comboBoxLabel, selectContact);

        this.setPadding(Styles.Padding30px);
        this.getChildren().addAll(dropDownGroup, appointments);
    }
}
