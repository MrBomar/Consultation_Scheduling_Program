package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import les.projects.consultation_scheduling_program.Components.ButtonGap;
import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.DialogBase;
import les.projects.consultation_scheduling_program.Components.DialogText;
import les.projects.consultation_scheduling_program.Enums.Message;
import static les.projects.consultation_scheduling_program.Main.lrb;

public class DialogConfirmation extends DialogBase {
    private Boolean result = false;

    public DialogConfirmation(String title, String message) {
        super(title);
        this.format(message);
    }

    public DialogConfirmation(Message message) {
        super(message.title);
        this.format(message.message);
    }

    private void format(String message) {
        this.center.getChildren().add(new DialogText(message));
        ButtonStandard ok = new ButtonStandard(lrb.getString("ok"));
        ok.setOnMouseClicked(this::okButtonClicked);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        cancel.setOnMouseClicked(this::cancelButtonClicked);
        this.bottom.getChildren().addAll(ok,new ButtonGap(),cancel);
    }

    public Boolean getResult() {
        return this.result;
    }

    private void okButtonClicked(Event e) {
        this.result = true;
        close();
    }

    private void cancelButtonClicked(Event e) {
        this.result = false;
        close();
    }
}
