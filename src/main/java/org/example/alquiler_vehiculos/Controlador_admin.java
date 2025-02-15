/**
 * Controlador_admin es la clase encargada de gestionar la interfaz gráfica de usuario
 * para la administración de vehículos en un sistema de alquiler de vehículos.
 * Esta clase maneja la interacción con la base de datos a través de la clase VehiculoDAO
 * y proporciona funcionalidades para insertar, modificar, eliminar y mostrar vehículos.
 *
 * La clase utiliza JavaFX para la interfaz gráfica y está diseñada para trabajar con
 * una tabla que muestra los vehículos disponibles y campos de texto para ingresar o
 * modificar la información de los vehículos.
 *
 * @author Alicia Pacheco
 */
package org.example.alquiler_vehiculos;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.io.IOException;

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

    @FXML
    private Button paginaPrincipalButton, mostrarVehiculosButton;

    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ObservableList<Vehiculos> listaVehiculos;

    /**
     * Inicializa la interfaz gráfica y configura las columnas de la tabla
     * y los listeners para los eventos de los botones.
     */
    @FXML
    public void initialize() {
        configurarColumnas();
        configurarListeners();
    }

    /**
     * Configura las columnas de la tabla para mostrar los datos de los vehículos.
     */
    private void configurarColumnas() {
        ids.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        marcas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        modelos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        anios.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAño()).asObject());
        precios.setCellValueFactory(cellData -> new SimpleFloatProperty((float) cellData.getValue().getPreciodia()).asObject());
    }

    /**
     * Configura los listeners para los eventos de selección de la tabla y los botones.
     */
    private void configurarListeners() {
        coches.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                id.setText(String.valueOf(newSelection.getId()));
                marca.setText(newSelection.getMarca());
                modelo.setText(newSelection.getModelo());
                anio.setText(String.valueOf(newSelection.getAño()));
                tipo.setText(newSelection.getTipo());
                precio.setText(String.valueOf(newSelection.getPreciodia()));
            }
        });

        insert.setOnAction(event -> insertarVehiculo());
        update.setOnAction(event -> modificarVehiculo());
        delete.setOnAction(event -> eliminarVehiculo());
        create.setOnAction(event -> mostrarVehiculos());
    }

    /**
     * Navega a la pantalla de inicio.
     */
    @FXML
    private void irAPantallaInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/Mostrar_Vehiculo.fxml"));
            Scene inicioScene = new Scene(loader.load());

            Stage inicioStage = new Stage();
            inicioStage.setScene(inicioScene);
            inicioStage.setTitle("Página Principal");
            inicioStage.setResizable(false);
            inicioStage.show();

            Stage currentStage = (Stage) paginaPrincipalButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla de mostrar vehículos.
     */
    @FXML
    private void irAMostrarVehiculos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/alquiler_vehiculos/MisVehiculos.fxml"));
            Scene mostrarVehiculosScene = new Scene(loader.load());

            Stage mostrarVehiculosStage = new Stage();
            mostrarVehiculosStage.setScene(mostrarVehiculosScene);
            mostrarVehiculosStage.setTitle("Mostrar Vehículos");
            mostrarVehiculosStage.setResizable(false);
            mostrarVehiculosStage.show();

            Stage currentStage = (Stage) mostrarVehiculosButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra los vehículos en la tabla según los filtros especificados en los campos de texto.
     */
    private void mostrarVehiculos() {
        String idText = id.getText();
        String marcaText = marca.getText();
        String modeloText = modelo.getText();
        String anioText = anio.getText();
        String tipoText = tipo.getText();
        String precioText = precio.getText();

        listaVehiculos = FXCollections.observableArrayList(vehiculoDAO.filtrarVehiculos(idText, marcaText, modeloText, anioText, tipoText, precioText));
        coches.setItems(listaVehiculos);
    }

    /**
     * Inserta un nuevo vehículo en la base de datos.
     */
    private void insertarVehiculo() {
        if (validarCampos()) {
            try {
                Vehiculos vehiculo = new Vehiculos(
                        0, // El ID se ignora al insertar
                        marca.getText(),
                        modelo.getText(),
                        Integer.parseInt(anio.getText()),
                        tipo.getText(),
                        Float.parseFloat(precio.getText())
                );

                if (vehiculoDAO.insertarVehiculo(vehiculo)) {
                    cargarVehiculos();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo agregar el vehículo.");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Revisa los datos ingresados.");
            }
        } else {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
        }
    }

    /**
     * Modifica un vehículo existente en la base de datos.
     */
    private void modificarVehiculo() {
        if (validarCampos()) {
            try {
                Vehiculos vehiculo = new Vehiculos(
                        Integer.parseInt(id.getText()), // El ID es necesario para modificar
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
        } else {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
        }
    }

    /**
     * Elimina un vehículo de la base de datos.
     */
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

    /**
     * Valida que todos los campos de texto estén completos.
     * @return true si todos los campos están completos, false en caso contrario.
     */
    private boolean validarCampos() {
        return !marca.getText().isEmpty() && !modelo.getText().isEmpty() &&
                !anio.getText().isEmpty() && !tipo.getText().isEmpty() && !precio.getText().isEmpty();
    }

    /**
     * Limpia todos los campos de texto.
     */
    private void limpiarCampos() {
        id.clear();
        marca.clear();
        modelo.clear();
        anio.clear();
        tipo.clear();
        precio.clear();
    }

    /**
     * Muestra una alerta con un mensaje de error.
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Carga los vehículos desde la base de datos y los muestra en la tabla.
     */
    private void cargarVehiculos() {
        listaVehiculos = FXCollections.observableArrayList(vehiculoDAO.cargarVehiculos());
        coches.setItems(listaVehiculos);
    }
}