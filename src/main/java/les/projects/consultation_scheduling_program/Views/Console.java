package les.projects.consultation_scheduling_program.Views;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.ButtonUserLogout;
import les.projects.consultation_scheduling_program.Components.TabConsole;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the main console where all views are rendered.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Console extends VBox {
    //Instantiate tabs
    private final TabConsole appointmentsTab = new TabConsole(lrb.getString("Appointments"), false);
    private final TabConsole customersTab = new TabConsole(lrb.getString("Customers"), false);
    private final TabConsole reportsTab = new TabConsole(lrb.getString("Reports"), false);
    private static Pane currentView;

    /**
     * Instantiates the console view.
     * @param view The main view we are rendering over.
     */
    public Console(Pane view) {
        currentView = view;
        this.setTabs();
    }

    /**
     * Method is used to select the active tab control.
     */
    private void setTabs() {
        this.getChildren().setAll(this.tabBar(), currentView);
        if(currentView.getClass() == AppointmentsView.class) {
            appointmentsTab.setActive();
            customersTab.setInactive();
            reportsTab.setInactive();
        } else if (currentView.getClass() == CustomersView.class) {
            appointmentsTab.setInactive();
            customersTab.setActive();
            reportsTab.setInactive();
        } else {
            appointmentsTab.setInactive();
            customersTab.setInactive();
            reportsTab.setActive();
        }
    }

    /**
     * This method renders the bar which hold the tabs.
     * @return Return the tab bar at the top of the console.
     */
    private BorderPane tabBar() {
        //Create tab bar HBox and style it
        BorderPane tabBar = new BorderPane();
        tabBar.setMinWidth(Main.appWidth);
        tabBar.setBackground(Styles.BackgroundGrey);

        //Instantiate a margin between tabs and logout button
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        //Instantiate user log out button
        ButtonUserLogout logoutButton = new ButtonUserLogout();
        logoutButton.setOnMouseClicked(logout);

        //Add listeners
        this.appointmentsTab.setOnMouseClicked(e -> this.openAppointments());
        this.customersTab.setOnMouseClicked(e -> this.openCustomers());
        this.reportsTab.setOnMouseClicked(e -> this.openReports());

        //Compile top bar
        tabBar.setLeft(new HBox(this.appointmentsTab, this.customersTab, this.reportsTab));
        tabBar.setRight(new HBox(logoutButton));
        return tabBar;
    }

    /**
     * This method changes the console's inset view to render the Appointments view and adjusts the rendering of the
     * tabs to show the Appointments tabs is selected.
     */
    private void openAppointments() {
        currentView = new AppointmentsView();
        this.setTabs();
    }

    /**
     * This method changes the console's inset view to render the Customers view and adjusts the rendering of the
     * tabs to show the Customers tabs is selected.
     */
    private void openCustomers() {
        currentView = new CustomersView();
        this.setTabs();
    }

    /**
     * This method changes the console's inset view to render the Reports view and adjusts the rendering of the
     * tabs to show the Reports tabs is selected.
     */
    private void openReports() {
        currentView = new ReportsView();
        this.setTabs();
    }

    /**
     * This method handles the user clicking on the logout button.
     */
    private final EventHandler<MouseEvent> logout = event -> {
        DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmLogout);
        dialog.showAndWait();
        if(dialog.getResult()){
            Main.logoutView();
        }
    };
}
