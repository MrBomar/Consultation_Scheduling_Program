package les.projects.consultation_scheduling_program.Views;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import les.projects.consultation_scheduling_program.Components.ButtonGap;
import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.DialogBase;
import les.projects.consultation_scheduling_program.Components.DialogText;
import les.projects.consultation_scheduling_program.Enums.Message;
import static les.projects.consultation_scheduling_program.Main.lrb;

public class DialogConfirmation extends DialogBase {
    private Boolean result = false;

    public DialogConfirmation(Message message) {
        super(message.title);
        this.center.getChildren().add(new DialogText(message.message));
        ButtonStandard ok = new ButtonStandard(lrb.getString("ok"));
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                result = true;
                close();
            }
        });
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                result = false;
                close();
            }
        });
        this.bottom.getChildren().addAll(ok,new ButtonGap(),cancel);
    }

    public Boolean getResult() {
        return this.result;
    }
}
