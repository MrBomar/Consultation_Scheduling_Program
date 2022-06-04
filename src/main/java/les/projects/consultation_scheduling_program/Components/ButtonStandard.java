package les.projects.consultation_scheduling_program.Components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ButtonStandard extends Button {

    public ButtonStandard(String s, int c, int r) {
        GridPane.setConstraints(this, c, r);
        this.styleIt(s);
    }

    public ButtonStandard(String s) {
        this.styleIt(s);
    }

    private void addMouseEffects() {
        this.setOnMousePressed((EventHandler<? super MouseEvent>) (event)->{
            this.setEffect(Styles.ButtonInnerShadow);
        });
        this.setOnMouseReleased((EventHandler<? super MouseEvent>) (event)->{
            this.setEffect(null);
        });
        this.setOnMouseEntered((EventHandler<? super MouseEvent>) (event)->{
            this.setBackground(Styles.BackgroundButtonHover);
        });
        this.setOnMouseExited((EventHandler<? super MouseEvent>) (event)->{
            this.setBackground(Styles.BackgroundWhite);
        });
    }

    private void styleIt(String s) {
        this.setText(s);
        this.setFont(Styles.DefaultFont20);
        this.setBorder(Styles.ButtonBorder);
        this.setMinSize(120,40);
        this.setMaxSize(120,40);
        this.setPrefSize(120,40);
        this.setBackground(Styles.BackgroundWhite);
        this.addMouseEffects();
    }
}
