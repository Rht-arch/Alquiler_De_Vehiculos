/**
 * ControladorMisVehiculos es la clase encargada de gestionar la interfaz gráfica de usuario
 * para mostrar los vehículos alquilados por un cliente en un sistema de alquiler de vehículos.
 * Esta clase utiliza JavaFX para mostrar una tabla con los detalles de los alquileres,
 * incluyendo información como la marca, modelo, año, tipo de vehículo, fechas de alquiler
 * y el total pagado.
 *
 * La clase implementa la interfaz Initializable para configurar la interfaz gráfica al cargar
 * la pantalla. Además, se encarga de cargar los datos de los alquileres desde la base de datos
 * a través de la clase AlquilerDAO.
 *
 * @author Alicia Pacheco Mena
 */
package org.example.alquiler_vehiculos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que gestiona los vehiculos del usuario , muestra los alquilers según
 * la id del cliente recibida
 * @author Alicia Pacheco (Desarrolladora Principal)
 * @author Rafael Haro (Colaborador)
 * @author Cristian Alejandro (Colaborador)
 */
public class ControladorMisVehiculos implements Initializable {
    /**
     * TableView que almacena los datos del alquiler y las columnas de la bbdd
     */
    @FXML
    private TableView<AlquilerDetalle> tableView;

    @FXML
    private TableColumn<AlquilerDetalle, Integer> colIdAlquiler;

    @FXML
    private TableColumn<AlquilerDetalle, String> colMarca;

    @FXML
    private TableColumn<AlquilerDetalle, String> colModelo;

    @FXML
    private TableColumn<AlquilerDetalle, Integer> colAño;

    @FXML
    private TableColumn<AlquilerDetalle, String> colTipo;

    @FXML
    private TableColumn<AlquilerDetalle, LocalDate> colFechaInicio;

    @FXML
    private TableColumn<AlquilerDetalle, LocalDate> colFechaFin;

    @FXML
    private TableColumn<AlquilerDetalle, Double> colTotal;
    /**
     * ComboBox que permite seleccionar el idioma de la app
     */
    @FXML
    private ComboBox<String> comboBoxIdiomas;
    /**
     * Botón que permite regresar a la pantalla principal
     */
    @FXML
    private Button volver;
    /**
     * Label con el texto de resumen
     */
    @FXML
    private Label txResumen;

    /**
     * Variables para establecer el idioma
     */
    private Locale locale;
    private ResourceBundle bundle;
    /**
     * Integer que almacena la id del cliente
     */
    private int userId;

    /**
     * Configura la id del usuario con la recibida
     * @param userId Integer con la id
     */
    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("ID del usuario recibido en Mis Vehículos: " + userId);
        cargarMisVehiculos(userId);
    }



    /**
     * Inicializa la interfaz gráfica y configura las columnas de la tabla.
     * Además, carga los datos de los alquileres del cliente desde la base de datos.
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se localizó.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Idioma por defecto
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.getSelectionModel().select("Español");

        // Cambio de idioma
        comboBoxIdiomas.setOnAction(event -> {
            String selectedLanguage = comboBoxIdiomas.getValue();

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
        });
        // Configurar las columnas de la TableView
        colIdAlquiler.setCellValueFactory(new PropertyValueFactory<>("idAlquiler"));  // <-- Usa idAlquiler, NO id
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAño.setCellValueFactory(new PropertyValueFactory<>("año"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Obtener el ID del cliente logueado (esto debería venir de la sesión)
        int idCliente = userId ; // Cambia esto por el ID del cliente logueado

        // Obtener los alquileres del cliente
        AlquilerDAO alquilerDAO = new AlquilerDAO();
        List<AlquilerDetalle> alquileres = alquilerDAO.obtenerAlquileresPorCliente(idCliente);

        // Convertir la lista a ObservableList y cargarla en la TableView
        ObservableList<AlquilerDetalle> observableList = FXCollections.observableArrayList(alquileres);
        tableView.setItems(observableList);
    }

    private void updateTexts() {
        txResumen.setText(bundle.getString("label.resumenVehiculos"));

        // Actualizar los textos de las columnas de la tabla
        colIdAlquiler.setText(bundle.getString("col.idAlquiler"));
        colMarca.setText(bundle.getString("col.marca"));
        colModelo.setText(bundle.getString("col.modelo"));
        colAño.setText(bundle.getString("col.anio"));
        colTipo.setText(bundle.getString("col.tipo"));
        colFechaInicio.setText(bundle.getString("col.fechaInicio"));
        colFechaFin.setText(bundle.getString("col.fechaFin"));
        colTotal.setText(bundle.getString("col.total"));

        // Actualizar el texto del botón
        volver.setText(bundle.getString("button.volverPrincipal"));
    }

    /**
     * Maneja el evento de clic en el botón "Volver". Cierra la ventana actual
     * y regresa a la pantalla principal.
     * @param event El evento de acción generado por el clic en el botón.
     */
    @FXML
    private void handleVolver(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(userId == 5) {
            try {
                CambiarPantallas.switchScene(currentStage, "Mostrar_Vehiculo.fxml", "Pantalla Principal");
                Controlador_Pagina_Principal controladorPrincipal = new Controlador_Pagina_Principal();
                controladorPrincipal.setUserId(userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                CambiarPantallas.switchScene(currentStage, "Usuario.fxml", "Pantalla Principal");
                Controlador_Usuario controladorUsuario = new Controlador_Usuario();
                controladorUsuario.setUserId(userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permite configurar el idioma en el que deben estar los textos
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("org.example.alquiler_vehiculos.idioma", locale);
        updateTexts(); // Actualizar los textos en el nuevo idioma
    }

    public void cargarMisVehiculos(int userId) {
        System.out.println("🔎 Buscando vehículos alquilados para el usuario: " + userId);

        AlquilerDAO alquilerDAO = new AlquilerDAO();
        List<AlquilerDetalle> listaAlquileres = alquilerDAO.obtenerAlquileresPorCliente(userId);

        if (listaAlquileres.isEmpty()) {
            System.out.println("⚠ No se encontraron vehículos para el usuario.");
        } else {
            System.out.println("✅ Vehículos encontrados: " + listaAlquileres.size());
            for (AlquilerDetalle vehiculo : listaAlquileres) {
                System.out.println(vehiculo.getMarca() + " " + vehiculo.getModelo());
            }
        }

        ObservableList<AlquilerDetalle> observableList = FXCollections.observableArrayList(listaAlquileres);
        tableView.setItems(observableList);
    }


}