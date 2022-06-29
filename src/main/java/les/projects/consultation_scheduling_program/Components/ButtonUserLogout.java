package les.projects.consultation_scheduling_program.Components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * Class extends the JavaFX VBox to create a clickable element for logging out of the program.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonUserLogout extends VBox {

    /**
     * Produces a JavaFX VBox containing an image and is clickable.
     */
    public ButtonUserLogout() {
        //Exit button
        HBox logoutButtonItems = new HBox();

        Text userInfo = new Text(lrb.getString("user") + Main.currentUser.getUserName());
        userInfo.setFont(Styles.DefaultFont18);
        userInfo.setFill(Styles.TextColor);
        Region buttonRegion = new Region();
        buttonRegion.setMinWidth(20);
        ImageLogout logoutIcon = new ImageLogout(36,36,10);
        logoutButtonItems.setMinWidth(250);
        logoutButtonItems.getChildren().addAll(userInfo,buttonRegion,logoutIcon);

        //Exit button margin
        Region logoutButtonMargin = new Region();
        logoutButtonMargin.setMinHeight(10);
        
        //Hover action
        logoutIcon.setOnMouseEntered((EventHandler<? super MouseEvent>) (event) -> {
            logoutIcon.setBackground(Styles.BackgroundWhite);
            logoutIcon.setBorder(Styles.ButtonBorder);
        });
        logoutIcon.setOnMouseExited((EventHandler<? super MouseEvent>) (event) -> {
            logoutIcon.setBackground(Styles.BackgroundGrey);
            logoutIcon.setBorder(Border.EMPTY);
        });
        
        this.getChildren().addAll(logoutButtonMargin,logoutButtonItems);
    }
}
