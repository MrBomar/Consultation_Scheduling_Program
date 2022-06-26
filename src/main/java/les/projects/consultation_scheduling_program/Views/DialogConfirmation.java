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

    public DialogConfirmation(Message message) {
        super(message.title);
        this.format(message.message);
    }

    public DialogConfirmation(Message message, String[] args) {
        super(message.title);

        //We need to get the message and replace the '{}' symbols with the word in args.
        StringBuilder newString = new StringBuilder();
        StringBuilder oldString = new StringBuilder(message.message);
        for(String a: args) {
            int start = oldString.indexOf("{}");
            newString = new StringBuilder(oldString.substring(0,start));
            newString.append(a);
            newString.append(oldString.substring(start + 2));
            oldString = newString;
        }
        this.format(newString.toString());
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
