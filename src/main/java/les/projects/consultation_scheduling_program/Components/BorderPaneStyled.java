package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

/**
 * Creates a pre-styled BorderPane containing a label.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class BorderPaneStyled extends BorderPane {
    /**
     * Creates a BorderPane containing a formatted Label.
     * @param labelText String to display on the label.
     * @param addPadding Boolean to turn padding on or off.
     */
    public BorderPaneStyled(String labelText, boolean addPadding) {
        //Process parameters
        if(labelText.equals("")){
            Label label = new Label(labelText);
            label.setFont(Styles.DefaultFont18);
            this.setLeft(label);
        }

        if(addPadding) this.setPadding(new Insets(10,0,10,0));
    }
}
