package les.projects.consultation_scheduling_program.Components;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class ImageLogout extends BorderPane {

    public ImageLogout(int w, int h, double margin) {
        Region yMargin1 = new Region(),
                yMargin2 = new Region(),
                xMargin1 = new Region(),
                xMargin2 = new Region();
        yMargin1.setMinWidth(margin);
        xMargin1.setMinHeight(margin);
        yMargin2.setMinWidth(margin);
        xMargin2.setMinHeight(margin);

        //Assign margins
        this.setTop(xMargin1);
        this.setBottom(xMargin2);
        this.setLeft(yMargin1);
        this.setRight(yMargin2);

        //Import image, set size, and set to center
        ImageView icon = new ImageView("logoutButton.png");
        icon.setPreserveRatio(true);
        icon.setFitHeight(h);
        icon.setFitWidth(w);

        this.setCenter(icon);
    }
}
