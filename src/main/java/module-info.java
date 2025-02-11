module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
    exports org.example.alquiler_vehiculos.BD;
    opens org.example.alquiler_vehiculos.BD to javafx.fxml;
}
