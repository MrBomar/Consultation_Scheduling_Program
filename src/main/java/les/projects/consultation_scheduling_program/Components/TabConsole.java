package les.projects.consultation_scheduling_program.Components;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX Pane class. Creates a Pane formatted for use as a Tab in a custom control.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class TabConsole extends Pane {
    private boolean active;

    /**
     * Initializes a JavaFX Pane containing a Label and formatted to change color when selected.
     * @param s String to display on the Tab.
     * @param active Set to true if Tab should display as selected.
     */
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

    /**
     * This method changes appearance of the Tab to appear selected. It also set the selected value to true.
     */
    public final void setActive() {
        this.active = true;
        this.setBackgroundWhite();
    }

    /**
     * Method set the background color of the Tab to white.
     */
    private void setBackgroundWhite() {
        this.setBackground(Styles.BackgroundWhite);
    }

    /**
     * This method sets the Tab's background color to grey unless the selected value is set to true.
     */
    private void setBackgroundGrey() {
        if(this.active) {
            this.setBackgroundWhite();
        } else {
            this.setBackground(Styles.BackgroundGrey);
        }
    }

    /**
     * This method changes appearance of the Tab to appear unselected. It also set the selected value to false.
     */
    public final void setInactive() {
        this.active = false;
        this.setBackgroundGrey();
    }
}
