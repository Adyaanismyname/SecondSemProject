module com.example.secondsemproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires ch.qos.logback.core;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires com.fasterxml.jackson.annotation;

    opens com.example.secondsemproject to javafx.fxml;
    exports com.example.secondsemproject;
}