package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.Clientes;
import org.example.alquiler_vehiculos.DAO.ClientesDAO;

import java.io.IOException;
import java.util.regex.Pattern;


public class Controlador_Registro {
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellido;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private PasswordField textPasswordContraseña;
    @FXML
    private Button buttonUnirse;
    @FXML
    private TextField textFieldDNI;

    private final ClientesDAO clientesDAO = new ClientesDAO();

    /**
     * Metodo que se ejecuta cuando el usuario hace clic en "Registrarse"
     */
    @FXML
    private void registrarUsuario() {
        String nombre = textFieldNombre.getText().trim();
        String apellido = textFieldApellido.getText().trim();
        String correo = textFieldEmail.getText().trim();
        String telefono = textFieldTelefono.getText().trim();
        String contraseña = textPasswordContraseña.getText().trim();
        String dni = textFieldDNI.getText().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contraseña.isEmpty() || dni.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "Todos los campos son obligatorios.");
            return;
        }

        // Validar formato de DNI
        if (!esDNIValido(dni)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "El DNI no es válido. Debe tener 8 números y una letra.");
            return;
        }

        // Crear objeto cliente (sin ID porque es autoincremental)
        Clientes nuevoCliente = new Clientes(0, dni, nombre, apellido, telefono, correo, contraseña);

        // Intentar insertar en la base de datos
        boolean registrado = clientesDAO.insertarCliente(nuevoCliente);

        // Verificar si la inserción fue exitosa
        if (registrado) {
            abrirPantallaPrincipal();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "Hubo un error al registrar el usuario.");
        }
    }

    /**
     * Valida el formato del DNI (8 números seguidos de una letra)
     */
    private boolean esDNIValido(String dni) {
        return Pattern.matches("\\d{8}[A-Za-z]", dni);
    }

    /**
     * Muestra una alerta en pantalla
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Abre la pantalla principal después del registro
     */
    private void abrirPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("UrbanDrive - Pantalla Principal");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
            cerrarVentana(); // Cerrar la ventana de registro
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra la ventana de registro
     */
    private void cerrarVentana() {
        Stage stage = (Stage) buttonUnirse.getScene().getWindow();
        stage.close();
    }

    /**
     * Asocia el evento de clic al botón "Registrarse"
     */
    @FXML
    public void initialize() {
        buttonUnirse.setOnAction(event -> registrarUsuario());
    }

}
