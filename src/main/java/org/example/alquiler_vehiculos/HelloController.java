package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.DAO.ClientesDAO;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que gestiona y controla la pantalla de inicio de sesion
 * @authors Alicia Pacheco Mena y Rafael Haro (Desarrolladores principales)
 * @author Cristian Alejandro (Colaborador)
 */
public class HelloController {
    /**
     * Conexion con la base de datos
     */
    public ClientesDAO clientesDAO = new ClientesDAO();

    /**
     * Textfield para recoger el usuario
     */
    @FXML
    private TextField textUsuario;

    /**
     * PasswordField para recoger la contraseña
     */
    @FXML
    private PasswordField textContraseña;

    /**
     * Button accionar carga la siguiente pantalla
     */
    @FXML
    private Button buttonAcceder;

    /**
     * Label para poner el texto de Iniciar Sesion
     */
    @FXML
    private Label labelIniciarSesion;

    /**
     * Hiperlink para moverse a la pantalla de registro
     */
    @FXML
    private Hyperlink HyperLinkRegis;
    /**
     * Hiperlink para moverse a la pantalla de manual de usuario
     */
    @FXML
    private Hyperlink hyperLinkAyuda;

    /**
     * ComboBox para indicar el idioma
     */
    @FXML
    private ComboBox<String> ComBoBoxIdiomasLogin;

    /**
     * Tooltip
     */
    @FXML
    private Tooltip user, pass;

    /**
     * Variables para establecer el idioma
     */
    private Locale locale;
    private ResourceBundle bundle;

    /**
     * Label para establecer un texto
     */
    @FXML
    private Label labelNoTienesCuenta;

    /**
     * Metodo que valida si los campos para acceder y si no estan validados salta una alerta
     */
    @FXML
    public void handleLogin() {
        String email = textUsuario.getText();
        String password = textContraseña.getText();

        // Obtener el ID del cliente a partir del correo
        Integer userId = clientesDAO.obtenerIdPorCorreo(email, password);

        if (userId != null) {
            showSplashScreen(userId,email); // Pasar la ID a la pantalla principal
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validación Fallida");
            alert.setHeaderText(null);
            alert.setContentText("Correo o contraseña incorrectos.");
            alert.showAndWait();
        }
    }


    /**
     * Metodo que inicializa los elementos del idioma
     */
    @FXML
    public void initialize() {
        // Idioma por defecto
        ComBoBoxIdiomasLogin.getItems().addAll("Español", "English");
        ComBoBoxIdiomasLogin.getSelectionModel().select("Español");

        // Cambio de idioma
        ComBoBoxIdiomasLogin.setOnAction(event -> {
            String selectedLanguage = ComBoBoxIdiomasLogin.getValue();

            switch (selectedLanguage) {
                case "English":
                    Locale.setDefault(Locale.ENGLISH);
                    locale = Locale.ENGLISH;
                    break;
                default:
                    Locale.setDefault(new Locale("es", "ES"));
                    locale = new Locale("es", "ES");
                    break;
            }

            bundle = ResourceBundle.getBundle("org.example.alquiler_vehiculos.idioma", locale);
            updateTexts();
            textUsuario.setTooltip(new Tooltip(bundle.getString("login.tooltip")));
            textContraseña.setTooltip(new Tooltip(bundle.getString("password.tooltip")));
        });

        HyperLinkRegis.setOnAction(event -> abrirPantallaRegistro());
    }

    /**
     * Metodo que cambia los idiomas
     */
    private void updateTexts() {
        labelIniciarSesion.setText(bundle.getString("login.title"));
        textUsuario.setPromptText(bundle.getString("login.username"));
        textContraseña.setPromptText(bundle.getString("login.password"));
        buttonAcceder.setText(bundle.getString("login.button"));
        HyperLinkRegis.setText(bundle.getString("login.register"));
        labelNoTienesCuenta.setText(bundle.getString("ntc"));

        hyperLinkAyuda.setText(bundle.getString("login.help"));
    }

    /**
     * Metodo que maneja el evento de clic en el Hyperlink y abre la pantalla de registro
     */
    @FXML
    public void abrirPantallaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/Registro.fxml"));
            Scene registroScene = new Scene(loader.load());

            Stage registroStage = new Stage();
            registroStage.setScene(registroScene);
            registroStage.setTitle("Registro de Usuario");
            registroStage.setResizable(false);
            registroStage.show();

            Stage currentStage = (Stage) HyperLinkRegis.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo qyue muestra el spalsh y despues la pagina principal
     */
    private void showSplashScreen(int userId, String correo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/splash.fxml"));
            Scene splashScene = new Scene(loader.load());

            SplashController splashController = loader.getController();
            Stage currentStage = (Stage) buttonAcceder.getScene().getWindow();

            Stage splashStage = new Stage();
            splashStage.setScene(splashScene);
            splashStage.setTitle("Cargando...");
            splashStage.setResizable(false);
            splashStage.show();

            splashController.startSplash(() -> {
                try {
                    FXMLLoader loader2;
                    Scene mainScene;

                    if (correo.equals("admin@gmail.com")) {
                        loader2 = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml"));
                    } else {
                        loader2 = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/Usuario.fxml"));
                    }

                    mainScene = new Scene(loader2.load());

                    // Obtener el controlador y enviar la ID
                    Object controller = loader2.getController();
                    if (controller instanceof Controlador_Pagina_Principal) {
                        ((Controlador_Pagina_Principal) controller).setUserId(userId);
                    } else if (controller instanceof Controlador_Usuario) {
                        ((Controlador_Usuario) controller).setUserId(userId);
                    }

                    enviarIdAMisVehiculos(userId);


                    Stage mainStage = new Stage();
                    mainStage.setScene(mainScene);
                    mainStage.setTitle("Alquiler de Coches");
                    mainStage.setResizable(false);
                    mainStage.show();


                    splashStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método que permite abrir la ayuda de usuario
     */
    @FXML
    private void abrirAyuda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/ManualUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ayuda - Manual de Usuario");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que envia la id del usuario a mis vehiculos para que carge sus alquileres
     * @param userId Id usuario para la tabla alquileres
     */
    private void enviarIdAMisVehiculos(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/MisVehiculos.fxml"));
            Parent root = loader.load();

            ControladorMisVehiculos controladorMisVehiculos = loader.getController();
            controladorMisVehiculos.setUserId(userId);
            System.out.println("ID enviada a Mis Vehículos: " + userId);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar Mis Vehículos.");
        }
    }

}