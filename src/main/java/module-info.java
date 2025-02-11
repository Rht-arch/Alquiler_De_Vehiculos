module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
}
