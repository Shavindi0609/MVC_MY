module com.ijse.gdse.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires net.sf.jasperreports.core;

    opens com.ijse.gdse.finalproject.controller to javafx.fxml;
    opens com.ijse.gdse.finalproject.dto.tm to javafx.base;
    exports com.ijse.gdse.finalproject;
}