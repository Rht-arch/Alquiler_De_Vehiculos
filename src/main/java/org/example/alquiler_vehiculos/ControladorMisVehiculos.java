package org.example.alquiler_vehiculos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorMisVehiculos {
    @FXML
    private void handleVolver(ActionEvent event) {
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        try {
            CambiarPantallas.switchScene(currentStage, "org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml", "Pantalla Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
