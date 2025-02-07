module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
    exports org.example.alquiler_vehiculos.Controladores;
    opens org.example.alquiler_vehiculos.Controladores to javafx.fxml;
}