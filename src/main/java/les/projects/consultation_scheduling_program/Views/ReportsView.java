package les.projects.consultation_scheduling_program.Views;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import les.projects.consultation_scheduling_program.Components.TabLeftStyled;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Reports view which contains the reports.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ReportsView extends Pane {

    /**
     * This constructor renders the report view and tabs.
     */
    public ReportsView() {
        TabPane tabPane = new TabPane();

        Tab tab1 = new TabLeftStyled(lrb.getString("Customer_Appointments_by_Type_and_Month"));
        Tab tab2 = new TabLeftStyled(lrb.getString("Customer_Appointment_Schedule"));
        Tab tab3 = new TabLeftStyled(lrb.getString("Contact_Appointment_Schedule"));

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    if(oldTab != null) {
                        TabLeftStyled oldTab1 = (TabLeftStyled) oldTab;
                        oldTab1.deselectTab();
                    }

                    TabLeftStyled newTab1 = (TabLeftStyled) newTab;
                    newTab1.selectTab();
                }
        );

        tab1.setContent(new ReportsTab1());
        tab2.setContent(new ReportsTab2());
        tab3.setContent(new ReportsTab3());

        //Setting TabPane properties
        tabPane.getTabs().addAll(tab1, tab2, tab3);
        tabPane.setSide(Side.LEFT);
        tabPane.setStyle(
                "-fx-tab-min-width: 233px;" +
                "-fx-tab-max-width: 233px;" +
                "-fx-tab-min-height: 233px;" +
                "-fx-tab-max-height: 233px;"
        );
        this.getChildren().add(tabPane);
    }
}
