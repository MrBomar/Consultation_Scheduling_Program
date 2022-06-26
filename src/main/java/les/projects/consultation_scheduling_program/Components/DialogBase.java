package les.projects.consultation_scheduling_program.Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import les.projects.consultation_scheduling_program.Main;

public class DialogBase extends Stage {
    protected final HBox bottom = new HBox();
    protected final VBox center = new VBox();

    public DialogBase(String windowTitle) {
        BorderPane root = new BorderPane();
        this.setScene(new Scene(root));
        this.setTitle(windowTitle);
        root.setTop(new Title(windowTitle));
        root.setCenter(this.center);
        root.setBottom(this.bottom);

        //Window formatting
        root.setPadding(new Insets(20,30,30,30));
        this.center.setPadding(new Insets(30,20,30,20));
        this.bottom.setAlignment(Pos.CENTER_RIGHT);
        this.setResizable(false);
        this.initOwner(Main.appStage);
        this.initModality(Modality.APPLICATION_MODAL);
    }
}
