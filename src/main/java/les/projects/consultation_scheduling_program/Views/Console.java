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
    private static boolean lockView = false;

    /**
     * This constructor instantiates the console view.
     * @param view The main view we are rendering over.
     */
    public Console(Pane view) {
        currentView = view;
        this.setTabs();
    }

    /**
     * This method is used to select the active tab control.
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
     * This method renders the bar which hold the tabs. Three lambda expressions are used to assign action to onMouse
     * events instead of instantiating and eventHandler which is more verbose.
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
        logoutButton.setOnMouseClicked(e -> {
            if(!lockView) {
                DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmLogout);
                dialog.showAndWait();
                if(dialog.getResult()) Main.logoutView();
            }
        });

        //Add listeners
        this.appointmentsTab.setOnMouseClicked(e -> {
            if(!lockView) {
                currentView = new AppointmentsView();
                this.setTabs();
            }
        });
        this.customersTab.setOnMouseClicked(e -> {
            if(!lockView) {
                currentView = new CustomersView();
                this.setTabs();
            }

        });
        this.reportsTab.setOnMouseClicked(e -> {
            if(!lockView) {
                currentView = new ReportsView();
                this.setTabs();
            }
        });

        //Compile top bar
        tabBar.setLeft(new HBox(this.appointmentsTab, this.customersTab, this.reportsTab));
        tabBar.setRight(new HBox(logoutButton));
        return tabBar;
    }

    /**
     * This method locks the view so it cannot be changed.
     */
    public static void preventViewChange() { lockView = true; }

    /**
     * This method unlocks the view so that it can be changed.
     */
    public static void allowViewChange() { lockView = false; }
}
