module com.operatingsystem.operaingsystems {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires eu.iamgio.animated;
    opens com.operatingsystem.operatingsystems to javafx.fxml;

    exports com.operatingsystem.operatingsystems;
}