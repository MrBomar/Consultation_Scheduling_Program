package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class BorderPaneStyled extends BorderPane {

    public BorderPaneStyled(String labelText, boolean addPadding) {
        //Process parameters
        if(labelText != ""){
            Label label = new Label(labelText);
            label.setFont(Styles.DefaultFont18);
            this.setLeft(label);
        }

        if(addPadding) this.setPadding(new Insets(10,0,10,0));
    }
}
