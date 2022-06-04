package les.projects.consultation_scheduling_program.Components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TabConsole extends Pane {
    private boolean active = false;

    public TabConsole(String s, Background background, boolean active) {
        this.active = active;
        Text text = new Text(s);
        text.setFill(Styles.TextColor);
        text.setFont(Styles.BoldFont28);

        this.getChildren().add(text);

        this.setMinSize(235,70);
        this.setMaxSize(235,70);
        this.setPrefSize(235,70);

        double x = ((235 - text.getBoundsInParent().getWidth())/2) + this.getLayoutX();
        double y = ((70 - text.getBoundsInParent().getHeight())/2) + this.getLayoutY() + 25;

        text.setLayoutX(x);
        text.setLayoutY(y);

        //Set hover properties
        this.setOnMouseEntered((EventHandler<? super MouseEvent>) (event) -> {
            this.setBackgroundWhite();
        });
        this.setOnMouseExited((EventHandler<? super MouseEvent>) (event) -> {
            this.setBackgroundGrey();
        });
    }

    public void setActive() {
        this.active = true;
        this.setBackgroundWhite();
    }

    public void setBackgroundWhite() {
        this.setBackground(Styles.BackgroundWhite);
    }

    public void setBackgroundGrey() {
        if(this.active) {
            this.setBackgroundWhite();
        } else {
            this.setBackground(Styles.BackgroundGrey);
        }
    }

    public void setInactive() {
        this.active = false;
        this.setBackgroundGrey();
    }
}
