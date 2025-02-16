package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controlador_ManualUsuario {

    @FXML
    private Button cerrar;

    @FXML
    private void cerrarManual() {
        Stage stage = (Stage) cerrar.getScene().getWindow();
        stage.close();
    }
}
