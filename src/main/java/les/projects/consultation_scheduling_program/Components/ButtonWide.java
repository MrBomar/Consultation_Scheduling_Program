package les.projects.consultation_scheduling_program.Components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import les.projects.consultation_scheduling_program.Enums.Styles;

public class ButtonWide extends Button {

    public ButtonWide(String s) {
        this.initButtonWide(s);
        this.addMouseEffects();
    }

    public ButtonWide(String s, int c, int r) {
        this.initButtonWide(s);
        this.addMouseEffects();
        GridPane.setConstraints(this, c, r);
    }

    private void initButtonWide(String s) {
        this.setText(s);
        this.setFont(Styles.DefaultFont16);
        this.setBorder(Styles.ButtonBorder);
        this.setMinSize(205,50);
        this.setMaxSize(205,50);
        this.setPrefSize(205,50);
        this.setBackground(Styles.BackgroundWhite);
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
}
