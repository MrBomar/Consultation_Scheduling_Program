package les.projects.consultation_scheduling_program.Components;

import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class DialogText extends Text {

    public DialogText(String message) {
        this.setFont(Styles.DefaultFont24);
        this.setWrappingWidth(350);
        this.setFill(Styles.TextColor);
        this.setText(message);
    }

}
