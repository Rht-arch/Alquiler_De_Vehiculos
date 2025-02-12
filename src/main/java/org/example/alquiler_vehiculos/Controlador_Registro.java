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

/**
 * Clase que controla la pantalla de Registro
 * @author Alicia Pacheco Mena
 */

public class Controlador_Registro {
    /**
     * Textfield para recoger el nombre
     */
    @FXML
    private TextField textFieldNombre;
    /**
     * TextField para recoger el apellido
     */
    @FXML
    private TextField textFieldApellido;
    /**
     * TextField para recoger el email
     */
    @FXML
    private TextField textFieldEmail;
    /**
     * Textfield para el telefono
     */
    @FXML
    private TextField textFieldTelefono;
    /**
     * PasswordField para la contraseña
     */
    @FXML
    private PasswordField textPasswordContraseña;
    /**
     * Button para el cambio de ventana
     */
    @FXML
    private Button buttonUnirse;
    /**
     * Textfield para recoger el DNI
     */
    @FXML
    private TextField textFieldDNI;
    /**
     * Hyperlink para conectar con la pantalla anterior
     */
    @FXML
    private Hyperlink HlinicioSesion;

    /**
     * Llamada para el ClienteDAO
     */
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

        // Implementacion de las validaciones
        if (!esDNIValido(dni)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "El DNI no es válido. Debe tener 8 números y una letra.");
            return;
        }

        if (!esContraseñaValida(contraseña)){
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "La contraseña debe tener al menos 8 caracteres");
        }

        if(!esEmailValido(correo)) {
            mostrarAlerta(Alert.AlertType.ERROR,"Error en el Registro", "El email es incorrecto");
            return;
        }

        if(!esNumeroValido(telefono)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el Registro", "El numero debe ser de 9 numeros");
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
     * Verificar si el DNI es correcto
     * @param dni parametro DNI
     * @return expresion regular para verificar
     */
    private boolean esDNIValido(String dni) {
        return Pattern.matches("\\d{8}[A-Za-z]", dni);
    }

    /**
     * Valida el telefono
     * @param telefono parametro telefono
     * @return expresion regular para verificar
     */
    private boolean esNumeroValido(String telefono) {
        return Pattern.matches("^/d¨{9}$", telefono);
    }

    /**
     * Valida que el correo sea correcto
     * @param email parametro correo
     * @return expresion regular para verificar
     */
    private boolean esEmailValido(String email) {
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n", email);
    }

    /**
     *Valida si la contraseña tenga al menos 8 caracteres
     * @param password parametro contraseña
     * @return expresion regular para verificar
     */
    private boolean esContraseñaValida(String password) {
        return Pattern.matches("^.{8,}$\n", password);
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
            Stage currentStage = (Stage) buttonUnirse.getScene().getWindow();
            CambiarPantallas.switchScene(currentStage, "/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml",
                    "UrbanDrive - Pantalla Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la pantalla de inicio de sesión
     */
    private void abrirPantallaInicioSesion() {
        try {
            Stage currentStage = (Stage) HlinicioSesion.getScene().getWindow();
            CambiarPantallas.switchScene(currentStage, "/org/example/alquiler_vehiculos/InicioDeSesion.fxml",
                    "UrbanDrive - Iniciar Sesión");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asocia el evento de clic al botón "Registrarse"
     */
    @FXML
    public void initialize() {
        buttonUnirse.setOnAction(event -> registrarUsuario());
        HlinicioSesion.setOnAction(event -> abrirPantallaInicioSesion()); // Vuelve a la pantalla de inicio de sesión

    }

}
