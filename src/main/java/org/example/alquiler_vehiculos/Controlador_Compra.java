package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la clase compra
 * @author Cristian Alejandro
 */
public class Controlador_Compra {
    @FXML
    private Label txDetalles;
    /**
     * Tableview  vehiculos
     */
    @FXML private TableView<AlquilerDetalle> tablaCompra;
    @FXML private TableColumn<AlquilerDetalle, String> colMarca;
    @FXML private TableColumn<AlquilerDetalle, String> colModelo;
    @FXML private TableColumn<AlquilerDetalle, String> colTipo;
    @FXML private TableColumn<AlquilerDetalle, Integer> colIdCliente;
    @FXML private TableColumn<AlquilerDetalle, Date> colFechaInicio;
    @FXML private TableColumn<AlquilerDetalle, Date> colFechaFin;
    @FXML private TableColumn<AlquilerDetalle, Float> colTotal;
    /**
     * Button para realizar la acción de compra
     */
    @FXML private Button btnComprar;
    /**
     * Button para realizar la acción de retornar
     */
    @FXML private Button btnVolver;
    /**
     * Variable alquilar detalle
     */
    private AlquilerDetalle vehiculoSeleccionado;

    @FXML
    private ComboBox<String> comboBoxIdiomas;
    /**
     * Variables para establecer el idioma
     */
    private Locale locale;
    private ResourceBundle bundle;
    /**
     * Lista que contiene todos los alquileres
     */
    private final ObservableList<AlquilerDetalle> listaAlquileres = FXCollections.observableArrayList();

    /**
     * Metodo que inicializa los componentes con los datos iniciales
     *
     */
    @FXML
    public void initialize() {
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
        // Configurar las columnas de la tabla con las propiedades del objeto AlquilerDetalle
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
        btnVolver.setText(bundle.getString("button.volver"));
    }

    /**
     * Metodo que selecciona un coche
     * @param alquilerDetalle Variabla del aquiler detallado
     */
    public void setVehiculoSeleccionado(AlquilerDetalle alquilerDetalle) {
        if (alquilerDetalle != null) {
            listaAlquileres.clear(); // Limpiar la tabla antes de agregar el nuevo
            listaAlquileres.add(alquilerDetalle);
        }
    }

    /**
     * Metodo que realiza la compra
     */
    @FXML
    private void comprarVehiculo() {
        if (!listaAlquileres.isEmpty()) {
            AlquilerDetalle alquilerDetalle = listaAlquileres.get(0); // Obtener el alquiler

            AlquilerDAO alquilerDAO = new AlquilerDAO();
            int idVehiculo = alquilerDAO.obtenerIdVehiculo(alquilerDetalle.getMarca(), alquilerDetalle.getModelo());
            int anioVehiculo = alquilerDAO.obtenerAnioVehiculo(alquilerDetalle.getMarca(), alquilerDetalle.getModelo());

            if (idVehiculo == -1 || anioVehiculo == -1) {
                System.out.println("Error: No se encontró el ID o Año del vehículo.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la Compra", "No se pudo completar el alquiler.");
                return;
            }

            int idAlquiler = alquilerDAO.registrarAlquiler(alquilerDetalle, idVehiculo, anioVehiculo);

            if (idAlquiler > 0) { // 🔹 Verificar si el ID es válido
                System.out.println("Alquiler registrado con ID: " + idAlquiler);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Alquiler Exitoso", "El vehículo ha sido alquilado correctamente.\nID del Alquiler: " + idAlquiler);

                abrirFinCompra(idAlquiler); // 🔹 Pasar la ID del alquiler

            } else {
                System.out.println("Error al registrar el alquiler.");
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la Compra", "No se pudo completar el alquiler.");
            }
        } else {
            System.out.println("⚠ No hay vehículos seleccionados para alquilar.");
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No hay vehículos en la lista de compra.");
        }
    }



    private void abrirSplashScreen2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/splash2.fxml"));
            Parent root = loader.load();

            SplashController2 splashController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Cargando...");

            // Iniciar Splash y cerrar la ventana cuando termine
            splashController.startSplash(stage::close);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    /**
     * Metodo que vuelve a la pantalla anterior
     */
    @FXML
    private void volver() {
        System.out.println("Volver a la pantalla anterior");
    }

    private void abrirFinCompra(int idAlquiler) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/FinCompra.fxml"));
            Parent root = loader.load();

            Controlador_Fin_Compra controladorFinCompra = loader.getController();
            controladorFinCompra.setIdAlquiler(idAlquiler); // 🔹 Pasar la ID del alquiler

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Finalización de Compra");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la pantalla de finalización de compra.");
        }
    }


}
