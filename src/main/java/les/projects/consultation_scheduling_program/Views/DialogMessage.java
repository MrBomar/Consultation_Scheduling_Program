package les.projects.consultation_scheduling_program.Views;

import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.DialogBase;
import les.projects.consultation_scheduling_program.Components.DialogText;
import les.projects.consultation_scheduling_program.Enums.Message;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders a message only dialog.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class DialogMessage extends DialogBase {

    /**
     * Instantiates a dialog that takes a String title and a String messages.
     * @param title Displayed at the top of the dialog window.
     * @param message Displayed in the body of the dialog.
     */
    public DialogMessage (String title, String message) {
        super(title);
        this.build(message);
    }

    /**
     * Instantiates a dialog that takes a pre-written Message.
     * @param message Selected from the Message enum.
     */
    public DialogMessage(Message message) {
        super(message.title);
        this.build(message.message);
    }

    /**
     * Instantiates a dialog that takes a pre-written message and a String array with values to insert into the
     * pre-written message.
     * @param message Selected from the Message enum.
     * @param args String array with values to be inserted into the Message.
     */
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

    /**
     * This method builds the dialog.
     * @param m The String to display in the body of the dialog.
     */
    private void build(String m) {
        this.center.getChildren().add(new DialogText(m));

        ButtonStandard ok = new ButtonStandard(lrb.getString("OK"));
        ok.setOnMouseClicked(e -> this.close());
        this.bottom.getChildren().add(ok);
    }
}
