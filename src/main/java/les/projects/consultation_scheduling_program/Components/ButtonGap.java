package les.projects.consultation_scheduling_program.Components;

import javafx.scene.layout.Pane;

/**
 * Extends the JavaFX Pane to produce a Pane 30 pixels wide for spacing elements.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ButtonGap extends Pane {
    /**
     * Creates a JavaFX Pane that is 30 pixels wide for spacing between elements.
     */
    public ButtonGap() {
        this.setMinWidth(30);
        this.setMaxWidth(30);
        this.setPrefWidth(30);
    }
}
