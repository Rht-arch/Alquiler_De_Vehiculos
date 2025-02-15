module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires jasperreports.pdf;

    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
    opens org.example.alquiler_vehiculos.BD to javafx.base;

}
