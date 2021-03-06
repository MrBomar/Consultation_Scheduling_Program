package les.projects.consultation_scheduling_program.Components;

import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * Class extends the JavaFX Text class. Creates a styled Text element.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class DialogText extends Text {

    /**
     * This constructor instantiates a formatted JavaFX Text element.
     * @param message String message to display in the Text element.
     */
    public DialogText(String message) {
        this.setFont(Styles.DefaultFont24);
        this.setWrappingWidth(350);
        this.setFill(Styles.TextColor);
        this.setText(message);
    }

}
