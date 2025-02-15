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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorMisVehiculos implements Initializable {

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
     * Inicializa la interfaz gráfica y configura las columnas de la tabla.
     * Además, carga los datos de los alquileres del cliente desde la base de datos.
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o null si no se localizó.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar las columnas de la TableView
        colIdAlquiler.setCellValueFactory(new PropertyValueFactory<>("idAlquiler"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAño.setCellValueFactory(new PropertyValueFactory<>("año"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Obtener el ID del cliente logueado (esto debería venir de la sesión)
        int idCliente = 1; // Cambia esto por el ID del cliente logueado

        // Obtener los alquileres del cliente
        AlquilerDAO alquilerDAO = new AlquilerDAO();
        List<AlquilerDetalle> alquileres = alquilerDAO.obtenerAlquileresPorCliente(idCliente);

        // Convertir la lista a ObservableList y cargarla en la TableView
        ObservableList<AlquilerDetalle> observableList = FXCollections.observableArrayList(alquileres);
        tableView.setItems(observableList);
    }

    /**
     * Maneja el evento de clic en el botón "Volver". Cierra la ventana actual
     * y regresa a la pantalla principal.
     * @param event El evento de acción generado por el clic en el botón.
     */
    @FXML
    private void handleVolver(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            CambiarPantallas.switchScene(currentStage, "Mostrar_Vehiculo.fxml", "Pantalla Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}