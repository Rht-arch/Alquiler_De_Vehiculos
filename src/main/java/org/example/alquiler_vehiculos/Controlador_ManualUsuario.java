package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Gestiona el manual de usuario
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class Controlador_ManualUsuario {

    @FXML
    private Button cerrar;

    @FXML
    private void cerrarManual(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        CambiarPantallas.switchScene(currentStage, "InicioDeSesion.fxml", "Inicio de sesión");
    }
}
