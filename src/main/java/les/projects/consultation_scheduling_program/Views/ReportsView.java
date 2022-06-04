package les.projects.consultation_scheduling_program.Views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import les.projects.consultation_scheduling_program.Components.TabLeftStyled;

public class ReportsView extends Pane {

    public ReportsView() {
        TabPane tabPane = new TabPane();

        Tab tab1 = new TabLeftStyled("Customer Appointments by Type and Month");
        Tab tab2 = new TabLeftStyled("Customer Appointment Schedule");
        Tab tab3 = new TabLeftStyled("Contact Appointment Schedule");

        tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> observableValue, Tab oldTab, Tab newTab) {
                    if(oldTab != null) {
                        TabLeftStyled oldTab1 = (TabLeftStyled) oldTab;
                        oldTab1.deselectTab();
                    }

                    TabLeftStyled newTab1 = (TabLeftStyled) newTab;
                    newTab1.selectTab();
                }
            }
        );

        tab1.setContent(new ReportsTab1());
        tab2.setContent(new ReportsTab2());
        tab3.setContent(new ReportsTab3());

        //Setting TabPane properties
        tabPane.getTabs().addAll(tab1, tab2, tab3);
        tabPane.setSide(Side.LEFT);
        tabPane.setStyle(this.tabPaneStyle());
        this.getChildren().add(tabPane);
    }

    private String tabPaneStyle() {
        return new String (
        "-fx-tab-min-width: 233px;" +
                "-fx-tab-max-width: 233px;" +
                "-fx-tab-min-height: 233px;" +
                "-fx-tab-max-height: 233px;"
        );
    }
}
