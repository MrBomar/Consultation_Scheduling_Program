module les.projects.consultation_scheduling_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens les.projects.consultation_scheduling_program to javafx.fxml;
    opens les.projects.consultation_scheduling_program.DataClasses to javafx.fxml;
    exports les.projects.consultation_scheduling_program;
    exports les.projects.consultation_scheduling_program.DataClasses;
}