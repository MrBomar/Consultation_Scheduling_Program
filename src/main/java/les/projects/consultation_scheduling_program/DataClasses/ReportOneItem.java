package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReportOneItem {
    private final SimpleStringProperty month;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty count;

    public ReportOneItem(String type, String month, int count) {
        this.month = new SimpleStringProperty(month);
        this.type = new SimpleStringProperty(type);
        this.count = new SimpleIntegerProperty(count);
    }

    public final String getMonth() { return month.get(); }
    public final String getType() { return type.get(); }
    public final Integer getCount() { return count.get(); }
}
