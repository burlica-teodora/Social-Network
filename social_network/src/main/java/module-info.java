module com.example.social_network1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.social_network1 to javafx.fxml,javafx.base;
    opens com.example.social_network1.domain to javafx.fxml,javafx.base;
    exports com.example.social_network1;
    exports com.example.social_network1.domain;
    exports com.example.social_network1.service;
    opens com.example.social_network1.service to javafx.fxml;
    exports com.example.social_network1.controllers;
    opens com.example.social_network1.controllers to javafx.base, javafx.fxml;
}