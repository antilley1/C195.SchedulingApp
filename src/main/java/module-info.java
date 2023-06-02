module mantil3.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mantil3.c195 to javafx.fxml;
    exports mantil3.c195;

    opens mantil3.c195.controller to javafx.fxml;
    exports mantil3.c195.controller;

    opens mantil3.c195.model to javafx.fxml;
    exports mantil3.c195.model;

    opens mantil3.c195.helper to javafx.fxml;
    exports mantil3.c195.helper;

    opens mantil3.c195.DBAccess to javafx.fxml;
    exports mantil3.c195.DBAccess;
    exports mantil3.c195.interfaces;
    opens mantil3.c195.interfaces to javafx.fxml;

}