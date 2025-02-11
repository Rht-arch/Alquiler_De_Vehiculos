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
 */
public class HelloController {
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
     * Variables para establecer el idioma
     */
    private Locale locale;
    private ResourceBundle bundle;

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
     * Metodo para ver que los parametros no esten vacios
     * @param username variable usuario
     * @param password variable contraseña
     * @return Devuelve si los parametros estan vacios o no
     */
    private boolean isInputValid(String username, String password) {
        return username != null && !username.trim().isEmpty() &&
                password != null && !password.trim().isEmpty();
    }

    /**
     * Metodo que inicializa los elementos del idioma
     */
    @FXML
    public void initialize() {
        // Idioma por defecto
        setLocale(new Locale("es"));

        // ComboBox de idiomas
        ComBoBoxIdiomasLogin.getItems().addAll("Español", "English");
        ComBoBoxIdiomasLogin.getSelectionModel().select("Español");

        // Cambio de idioma
        ComBoBoxIdiomasLogin.setOnAction(event -> {
            String selectedLanguage = ComBoBoxIdiomasLogin.getValue();
            switch (selectedLanguage) {
                case "English":
                    setLocale(new Locale("en"));
                    break;
                default:
                    setLocale(new Locale("es"));
                    break;
            }
        });

        HyperLinkRegis.setOnAction(event -> abrirPantallaRegistro());

    }

    /**
     * Metodo para establecer los idiomas
     * @param locale variable para establecer idioma predeterminado
     */
    private void setLocale(Locale locale) {
        this.locale = locale;
        bundle = ResourceBundle.getBundle("idiomas", locale);
        updateTexts();
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
    }

    /**
     * Metodo que maneja el evento de clic en el Hyperlink y abre la pantalla de registro
     */
    @FXML
    public void abrirPantallaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/registro.fxml"));
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
                try {
                    CambiarPantallas.switchScene(splashStage, "/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml", "Alquiler de Coches");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}