module org.example.alquiler_vehiculos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.alquiler_vehiculos to javafx.fxml;
    exports org.example.alquiler_vehiculos;
    exports Controladores;
    opens Controladores to javafx.fxml;
    exports Informes;
    opens Informes to javafx.fxml;
}