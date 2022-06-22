package les.projects.consultation_scheduling_program.Views;

import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.DialogBase;
import les.projects.consultation_scheduling_program.Components.DialogText;
import les.projects.consultation_scheduling_program.Enums.Message;
import static les.projects.consultation_scheduling_program.Main.lrb;

public class DialogMessage extends DialogBase {

    public DialogMessage (String title, String message) {
        super(title);
        this.build(message);
    }

    public DialogMessage(Message message) {
        super(message.title);
        this.build(message.message);
    }

    public DialogMessage(Message message, String[] args) {
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
        this.build(newString.toString());
    }

    private void build(String m) {
        this.center.getChildren().add(new DialogText(m));

        ButtonStandard ok = new ButtonStandard(lrb.getString("ok"));
        ok.setOnMouseClicked(e -> this.close());
        this.bottom.getChildren().add(ok);
    }
}
