package org.example.alquiler_vehiculos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.BD.Alquileres;
import org.example.alquiler_vehiculos.BD.Vehiculos;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Controlador_Compra {

    @FXML
    private TableView<AlquilerDetalle> tablaCompra;

    @FXML
    private TableColumn<AlquilerDetalle, String> colMarca;
    @FXML
    private TableColumn<AlquilerDetalle, String> colModelo;
    @FXML
    private TableColumn<AlquilerDetalle, String> colTipo;
    @FXML
    private TableColumn<AlquilerDetalle, Integer> colIdCliente;
    @FXML
    private TableColumn<AlquilerDetalle, String> colFechaInicio;
    @FXML
    private TableColumn<AlquilerDetalle, String> colFechaFin;
    @FXML
    private TableColumn<AlquilerDetalle, Float> colTotal;

    @FXML
    private DatePicker fechaFinPicker;

    @FXML
    private Button btnComprar;
    @FXML
    private Button btnVolver;

    private ObservableList<AlquilerDetalle> alquileresObservableList = FXCollections.observableArrayList();
    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private Vehiculos vehiculoSeleccionado;

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tablaCompra.setItems(alquileresObservableList);

        // Configurar el selector de fecha para evitar fechas anteriores a hoy
        fechaFinPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }

    /**
     * Recibe el vehículo seleccionado y llena la tabla con sus datos.
     */
    public void setVehiculoSeleccionado(Vehiculos vehiculo) {
        this.vehiculoSeleccionado = vehiculo;
        actualizarTabla();
    }

    /**
     * Actualiza la tabla con la información del vehículo seleccionado.
     */
    private void actualizarTabla() {
        if (vehiculoSeleccionado != null) {
            AlquilerDetalle alquilerDetalle = new AlquilerDetalle(
                    vehiculoSeleccionado.getMarca(),
                    vehiculoSeleccionado.getModelo(),
                    vehiculoSeleccionado.getTipo(),
                    1, // ID del cliente (debería obtenerse dinámicamente)
                    new Date(), // Fecha de inicio = hoy
                    null, // Fecha de fin aún no seleccionada
                    0 // Total inicial
            );

            alquileresObservableList.clear();
            alquileresObservableList.add(alquilerDetalle);
        }
    }

    /**
     * Calcula y muestra el total basado en la fecha de fin seleccionada.
     */
    @FXML
    public void calcularTotal() {
        if (fechaFinPicker.getValue() != null && vehiculoSeleccionado != null) {
            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaFin = fechaFinPicker.getValue();

            long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
            if (dias < 1) {
                mostrarAlerta("Error", "Selecciona una fecha válida de devolución.");
                return;
            }

            float total = dias * (float) vehiculoSeleccionado.getPreciodia();
            alquileresObservableList.get(0).setFechaFin(java.sql.Date.valueOf(fechaFin));
            alquileresObservableList.get(0).setTotal(total);

            tablaCompra.refresh();
        }
    }

    /**
     * Maneja la compra y abre el diálogo para guardar un archivo.
     */
    @FXML
    public void comprarVehiculo() {
        if (fechaFinPicker.getValue() == null) {
            mostrarAlerta("Error", "Selecciona una fecha de devolución.");
            return;
        }

        AlquilerDetalle alquiler = alquileresObservableList.get(0);

        // Simular la inserción en la BD
        alquilerDAO.insertarAlquiler(new Alquileres(
                0, // ID generado automáticamente
                alquiler.getIdCliente(),
                vehiculoSeleccionado.getId(),
                alquiler.getFechaInicio(),
                alquiler.getFechaFin(),
                alquiler.getTotal()
        ));

        // Abrir file chooser para guardar el recibo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Recibo de Alquiler");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File archivo = fileChooser.showSaveDialog(new Stage());
        if (archivo != null) {
            System.out.println("Guardando recibo en: " + archivo.getAbsolutePath());
        }

        mostrarAlerta("Éxito", "Alquiler registrado con éxito.");
    }

    /**
     * Muestra una alerta con un mensaje.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Cierra la ventana de compra.
     */
    @FXML
    public void volver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
