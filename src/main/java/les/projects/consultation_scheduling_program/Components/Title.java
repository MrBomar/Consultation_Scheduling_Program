package les.projects.consultation_scheduling_program.Components;

import javafx.scene.text.Text;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class Title extends Text {

    public Title(String s) {
        this.formatIt(s);
    }

    private void formatIt(String s) {
        this.setText(s);
        this.setFont(Styles.DefaultFont28);
        this.setFill(Styles.TextColor);
    }
}
