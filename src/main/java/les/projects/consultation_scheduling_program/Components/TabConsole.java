package les.projects.consultation_scheduling_program.Components;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TabConsole extends Pane {
    private boolean active;

    public TabConsole(String s, boolean active) {
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
        this.setOnMouseEntered(e -> this.setBackgroundWhite());
        this.setOnMouseExited(e -> this.setBackgroundGrey());
    }

    public final void setActive() {
        this.active = true;
        this.setBackgroundWhite();
    }

    public final void setBackgroundWhite() {
        this.setBackground(Styles.BackgroundWhite);
    }

    public final void setBackgroundGrey() {
        if(this.active) {
            this.setBackgroundWhite();
        } else {
            this.setBackground(Styles.BackgroundGrey);
        }
    }

    public final void setInactive() {
        this.active = false;
        this.setBackgroundGrey();
    }
}
