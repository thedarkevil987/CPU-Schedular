module com.operatingsystem.operaingsystems {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires MaterialFX;
    requires eu.iamgio.animated;
    opens com.operatingsystem.operaingsystems to javafx.fxml;

    exports com.operatingsystem.operaingsystems;
}