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

    private void build(String m) {
        this.center.getChildren().add(new DialogText(m));

        ButtonStandard ok = new ButtonStandard(lrb.getString("ok"));
        ok.setOnMouseClicked(e -> this.close());
        this.bottom.getChildren().add(ok);
    }
}
