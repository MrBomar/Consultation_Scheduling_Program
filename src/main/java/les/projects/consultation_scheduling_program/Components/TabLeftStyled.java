package les.projects.consultation_scheduling_program.Components;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class TabLeftStyled extends Tab {
    private Label label = new Label();

    public TabLeftStyled(String text) {
        this.label.setText(text);
        this.label.setFont(Styles.DefaultFont24);
        this.label.setWrapText(true);
        this.label.setPrefSize(233,233);
        this.label.setMinSize(233,233);
        this.label.setMaxSize(233,233);

        //Style the button
        this.setGraphic(this.label);
        this.setClosable(false);
        this.deselectTab();
    }

    public void selectTab() {
        this.label.setTextFill(Color.WHITE);
        this.setStyle(
            "-fx-background-color: black;" +
            "-fx-padding: 32 0 32 0;" +
            "-fx-focus-color: transparent;"
        );
    }

    public void deselectTab() {
        this.label.setTextFill(Color.BLACK);
        this.setStyle(
            "-fx-background-color: white;" +
            "-fx-padding: 32 0 32 0;"
        );
    }
}
