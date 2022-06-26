package les.projects.consultation_scheduling_program.Components;

import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * This class extends the JavaFX Text class and preformats the Text.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Title extends Text {

    /**
     * Initializes the JavaFX Text element.
     * @param s String value to display on the text element.
     */
    public Title(String s) {
        this.setText(s);
        this.setFont(Styles.DefaultFont28);
        this.setFill(Styles.TextColor);
    }
}
