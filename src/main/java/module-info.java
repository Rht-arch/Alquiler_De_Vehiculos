module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
}