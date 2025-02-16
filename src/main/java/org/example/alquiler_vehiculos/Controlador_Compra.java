package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la pantalla de compra de un vehículo de alquiler.
 * Permite seleccionar un vehículo, confirmar el alquiler y registrar la transacción en la base de datos.
 * Además, muestra un splash screen después de realizar el alquiler.
 * @author Cristian Alejandro
 */
public class Controlador_Compra {

    // Etiqueta para mostrar detalles del alquiler
    @FXML
    private Label txDetalles;

    // Tabla para mostrar los vehículos seleccionados para alquilar
    @FXML private TableView<AlquilerDetalle> tablaCompra;
    @FXML private TableColumn<AlquilerDetalle, String> colMarca;
    @FXML private TableColumn<AlquilerDetalle, String> colModelo;
    @FXML private TableColumn<AlquilerDetalle, String> colTipo;
    @FXML private TableColumn<AlquilerDetalle, Integer> colIdCliente;
    @FXML private TableColumn<AlquilerDetalle, Date> colFechaInicio;
    @FXML private TableColumn<AlquilerDetalle, Date> colFechaFin;
    @FXML private TableColumn<AlquilerDetalle, Float> colTotal;

    // Botón para confirmar la compra
    @FXML private Button btnComprar;


    // Objeto que almacena la información del vehículo seleccionado
    private AlquilerDetalle vehiculoSeleccionado;

    // ComboBox para seleccionar el idioma de la interfaz
    @FXML
    private ComboBox<String> comboBoxIdiomas;

    // Variables para gestionar el idioma de la interfaz
    private Locale locale;
    private ResourceBundle bundle;

    // Lista observable que contiene los detalles del alquiler
    private final ObservableList<AlquilerDetalle> listaAlquileres = FXCollections.observableArrayList();

    /**
     * Método que inicializa los componentes de la interfaz al cargar la vista.
     */

    @FXML
    public void initialize() {
        // Agregar opciones de idioma al ComboBox
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.getSelectionModel().select("Español"); // Seleccionar español por defecto

        // Configurar el cambio de idioma
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
            updateTexts(); // Actualizar los textos de la interfaz con el nuevo idioma
        });

        // Configurar las columnas de la tabla con las propiedades de los objetos AlquilerDetalle
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Asignar la lista de alquileres a la tabla
        tablaCompra.setItems(listaAlquileres);
    }

    /**
     * Método que actualiza los textos de la interfaz al cambiar de idioma.
     */

    private void updateTexts() {
        // Actualizar el título
        txDetalles.setText(bundle.getString("label.detallesAlquiler"));

        // Actualizar los textos de las columnas de la tabla
        colMarca.setText(bundle.getString("col.marca"));
        colModelo.setText(bundle.getString("col.modelo"));
        colTipo.setText(bundle.getString("col.tipo"));
        colIdCliente.setText(bundle.getString("col.idCliente"));
        colFechaInicio.setText(bundle.getString("col.fechaInicio"));
        colFechaFin.setText(bundle.getString("col.fechaFin"));
        colTotal.setText(bundle.getString("col.total"));

        // Actualizar los textos de los botones
        btnComprar.setText(bundle.getString("button.alquilar"));
    }

    /**
     * Método que recibe un objeto `AlquilerDetalle` y lo añade a la lista de alquileres.
     * @param alquilerDetalle Objeto que contiene la información del vehículo seleccionado.
     */
    public void setVehiculoSeleccionado(AlquilerDetalle alquilerDetalle) {
        if (alquilerDetalle != null) {
            listaAlquileres.clear(); // Limpiar la lista antes de agregar el nuevo vehículo
            listaAlquileres.add(alquilerDetalle);
        }
    }

    /**
     * Método que gestiona el proceso de alquiler de un vehículo.
     * Obtiene el ID del vehículo, lo registra en la base de datos y muestra la pantalla de carga.
     */
    @FXML
    private void comprarVehiculo() {
        if (!listaAlquileres.isEmpty()) {
            AlquilerDetalle alquilerDetalle = listaAlquileres.get(0); // Obtener el vehículo a alquilar

            // Obtener el ID y el año del vehículo desde la base de datos
            AlquilerDAO alquilerDAO = new AlquilerDAO();
            int idVehiculo = alquilerDAO.obtenerIdVehiculo(alquilerDetalle.getMarca(), alquilerDetalle.getModelo());
            int anioVehiculo = alquilerDAO.obtenerAnioVehiculo(alquilerDetalle.getMarca(), alquilerDetalle.getModelo());

            // Si no se encuentra el ID o el año del vehículo, no continuar
            if (idVehiculo == -1 || anioVehiculo == -1) {
                return;
            }

            // Registrar el alquiler en la base de datos
            boolean exito = alquilerDAO.registrarAlquiler(alquilerDetalle, idVehiculo, anioVehiculo);

            // Si el registro es exitoso, abrir la pantalla de carga (SplashScreen2)
            if (exito) {
                abrirSplashScreen2();
            }
        }
    }

    /**
     * Método que abre la pantalla de carga `SplashScreen2` después de confirmar el alquiler.
     */
    private void abrirSplashScreen2() {
        try {
            // Cargar la ventana SplashScreen2 desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/splash2.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la pantalla de carga
            SplashController2 splashController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Cargando...");

            // Iniciar la animación de carga y cerrar la ventana cuando termine
            splashController.startSplash(stage::close);

            stage.show(); // Mostrar la ventana de carga
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir error si la ventana no se carga correctamente
        }
    }
}
