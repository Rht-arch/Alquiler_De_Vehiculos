package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;

import java.util.Date;

/**
 * Clase que controla la clase compra
 * @author Cristian Alejandro
 */
public class Controlador_Compra {

    /**
     * TableView para msotrar lso vehiculos
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
     * Button para realizar uan accion
     */
    @FXML private Button btnComprar;
    /**
     * Button para realizar uan accion
     */
    @FXML private Button btnVolver;
    /**
     * Variable alquilar detalle
     */
    private AlquilerDetalle vehiculoSeleccionado;
    /**
     * Lista que contiene todos los alquileres
     */
    private final ObservableList<AlquilerDetalle> listaAlquileres = FXCollections.observableArrayList();

    /**
     * Metodo que inicializa los componentes
     */
    @FXML
    public void initialize() {
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
        System.out.println("Vehículo alquilado: " + listaAlquileres.get(0));
    }

    /**
     * Metodo que vuelve a la pantalla anterior
     */
    @FXML
    private void volver() {
        System.out.println("Volver a la pantalla anterior");
    }
}
