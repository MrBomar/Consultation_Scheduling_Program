package les.projects.consultation_scheduling_program.Components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class extends the JavaFX VBox to create a clickable element for logging out of the program.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonUserLogout extends BorderPane {

    /**
     * This constructor instantiates a JavaFX VBox containing an image and is clickable. Two lambda expressions are
     * used to set the actions taken during OnMouse events. Two lambda expressions were used here to prevent us from
     * using the addListener method which is more verbose, it also eliminated the need to call private methods which
     * makes the code more readable.
     */
    public ButtonUserLogout() {
        //Exit button
        HBox logoutButtonItems = new HBox();

        Text userInfo = new Text(lrb.getString("User") + ": \n" + Main.currentUser.getUserName());
        userInfo.setFont(Styles.DefaultFont18);
        userInfo.setFill(Styles.TextColor);
        Region buttonRegion = new Region();
        buttonRegion.setMinWidth(20);
        ImageLogout logoutIcon = new ImageLogout(36,36,10);
        logoutButtonItems.setPadding(new Insets(0,20,0,20));
        HBox.setHgrow(logoutButtonItems, Priority.ALWAYS);
        logoutButtonItems.getChildren().addAll(userInfo,buttonRegion,logoutIcon);

        //Exit button margin
        Region topMargin = new Region();
        topMargin.setMinHeight(5);
        Region bottomMargin = new Region();
        bottomMargin.setMinHeight(5);

        logoutIcon.setBorder(Styles.BorderGrey);
        
        //Hover action
        logoutIcon.setOnMouseEntered(e -> {
            logoutIcon.setBackground(Styles.BackgroundWhite);
            logoutIcon.setBorder(Styles.BorderBlack);
        });
        logoutIcon.setOnMouseExited(e -> {
            logoutIcon.setBackground(Styles.BackgroundGrey);
            logoutIcon.setBorder(Styles.BorderGrey);
        });

        this.setTop(topMargin);
        this.setBottom(bottomMargin);
        this.setCenter(logoutButtonItems);
    }
}
