package org.example.alquiler_vehiculos;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;
import org.example.alquiler_vehiculos.BD.Vehiculos;

public class Controlador_admin {

    @FXML
    private Button insert, delete, update, create;

    @FXML
    private TextField id, marca, modelo, anio, tipo, precio;

    @FXML
    private TableView<Vehiculos> coches;

    @FXML
    private TableColumn<Vehiculos, Integer> ids;

    @FXML
    private TableColumn<Vehiculos, String> marcas;

    @FXML
    private TableColumn<Vehiculos, String> modelos;

    @FXML
    private TableColumn<Vehiculos, Integer> anios;

    @FXML
    private TableColumn<Vehiculos, Float> precios;

    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ObservableList<Vehiculos> listaVehiculos;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarVehiculos();

        insert.setOnAction(event -> insertarVehiculo());
        update.setOnAction(event -> modificarVehiculo());
        delete.setOnAction(event -> eliminarVehiculo());
        create.setOnAction(event -> cargarVehiculos());
    }

    private void configurarColumnas() {
        ids.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        marcas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        modelos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        anios.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAño()).asObject());
        precios.setCellValueFactory(cellData -> new SimpleFloatProperty((float) cellData.getValue().getPreciodia()).asObject());
    }

    private void cargarVehiculos() {
        listaVehiculos = FXCollections.observableArrayList(vehiculoDAO.obtenerTodosLosVehiculos());
        coches.setItems(listaVehiculos);
    }

    private void insertarVehiculo() {
        try {
            Vehiculos vehiculo = new Vehiculos(
                    Integer.parseInt(id.getText()),
                    marca.getText(),
                    modelo.getText(),
                    Integer.parseInt(anio.getText()),
                    tipo.getText(),
                    Float.parseFloat(precio.getText())
            );

            if (vehiculoDAO.insertarVehiculo(vehiculo)) {
                listaVehiculos.add(vehiculo);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el vehículo.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Revisa los datos ingresados.");
        }
    }

    private void modificarVehiculo() {
        try {
            Vehiculos vehiculo = new Vehiculos(
                    Integer.parseInt(id.getText()),
                    marca.getText(),
                    modelo.getText(),
                    Integer.parseInt(anio.getText()),
                    tipo.getText(),
                    Float.parseFloat(precio.getText())
            );

            if (vehiculoDAO.actualizarVehiculo(vehiculo)) {
                cargarVehiculos();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo modificar el vehículo.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Revisa los datos ingresados.");
        }
    }

    private void eliminarVehiculo() {
        try {
            int vehiculoId = Integer.parseInt(id.getText());
            if (vehiculoDAO.eliminarVehiculo(vehiculoId)) {
                cargarVehiculos();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el vehículo.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido.");
        }
    }

    private void limpiarCampos() {
        id.clear();
        marca.clear();
        modelo.clear();
        anio.clear();
        tipo.clear();
        precio.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
