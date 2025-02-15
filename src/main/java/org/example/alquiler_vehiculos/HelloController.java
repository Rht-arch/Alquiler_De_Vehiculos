package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.DAO.ClientesDAO;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que gestiona y controla la pantalla de inicio de sesion
 * @authors Alicia Pacheco Mena y Rafael Haro
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
        String username = textUsuario.getText();
        String password = textContraseña.getText();

        if (clientesDAO.obtenerClientePorId(username, password) != null) {
            showSplashScreen();
        } else {
            //Alerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validación Fallida");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellena ambos campos.");
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
    private void showSplashScreen() {
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
               if(clientesDAO.obtenerClientePorId(textUsuario.getText(),textContraseña.getText()).getCorreo().matches("admin@gmail.com")) {
                try {
                    CambiarPantallas.switchScene(splashStage, "/org/example/alquiler_vehiculos/admin.fxml", "Alquiler de Coches");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
               }else{
                   try {
                       CambiarPantallas.switchScene(splashStage, "/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml", "Alquiler de Coches");
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }               }
            });

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}