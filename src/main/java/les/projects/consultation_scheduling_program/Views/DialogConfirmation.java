package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import les.projects.consultation_scheduling_program.Components.ButtonGap;
import les.projects.consultation_scheduling_program.Components.ButtonStandard;
import les.projects.consultation_scheduling_program.Components.DialogBase;
import les.projects.consultation_scheduling_program.Components.DialogText;
import les.projects.consultation_scheduling_program.Enums.Message;
import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders a confirmation modal extending the JavaFX DialogBase class.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class DialogConfirmation extends DialogBase {
    private Boolean result = false;

    /**
     * This constructor instantiates a confirmation dialog that take a Message parameter.
     * @param message A pre written message from the Message enum.
     */
    public DialogConfirmation(Message message) {
        super(message.title);
        this.format(message.message);
    }

    /**
     * This constructor instantiates a confirmation dialog that takes a Message parameter, but also take a
     * String[] of key words to insert in the pre-made message.
     * @param message The pre-scripted message to display.
     * @param args An array of Strings to display in placeholders in the pre-scripted message.
     */
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

    /**
     * This method lays out the dialog and formats it's elements.
     * @param message A string to display in the modal.
     */
    private void format(String message) {
        this.center.getChildren().add(new DialogText(message));
        ButtonStandard ok = new ButtonStandard(lrb.getString("ok"));
        ok.setOnMouseClicked(this::okButtonClicked);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        cancel.setOnMouseClicked(this::cancelButtonClicked);
        this.bottom.getChildren().addAll(ok,new ButtonGap(),cancel);
    }

    /**
     * Used to get the result of the user's response.
     * @return True if 'OK' pressed.
     */
    public Boolean getResult() {
        return this.result;
    }

    /**
     * This method is used to close the dialog and record the user response as true.
     * @param e Mouse event.
     */
    private void okButtonClicked(Event e) {
        this.result = true;
        close();
    }

    /**
     * This method is used to close the dialog and record the user response as false.
     * @param e Mouse Event.
     */
    private void cancelButtonClicked(Event e) {
        this.result = false;
        close();
    }
}
