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

public class Console extends VBox {
    //Instantiate tabs
    TabConsole appointmentsTab = new TabConsole(lrb.getString("appointments"), false);
    TabConsole customersTab = new TabConsole(lrb.getString("customers"), false);
    TabConsole reportsTab = new TabConsole(lrb.getString("reports"), false);
    private static Pane currentView;

    public Console(Pane view) {
        currentView = view;
        this.setTabs();
    }

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

    private HBox tabBar() {
        //Create tab bar HBox and style it
        HBox tabBar = new HBox();
        tabBar.setMaxWidth(Main.appWidth);
        tabBar.setMinWidth(Main.appWidth);
        tabBar.setPrefWidth(Main.appWidth);
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
        tabBar.getChildren().addAll(this.appointmentsTab,this.customersTab,this.reportsTab,region1,logoutButton);
        return tabBar;
    }

    private void openAppointments() {
        currentView = new AppointmentsView();
        this.setTabs();
    }

    private void openCustomers() {
        currentView = new CustomersView();
        this.setTabs();
    }

    private void openReports() {
        currentView = new ReportsView();
        this.setTabs();
    }

    private final EventHandler<MouseEvent> logout = event -> {
        DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmLogout);
        dialog.showAndWait();
        if(dialog.getResult()){
            Main.logoutView();
        }
    };
}
