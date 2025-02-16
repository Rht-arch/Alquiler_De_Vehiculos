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
 * @author Alicia Pacheco(Desarrolladora Principal)
 * @author Rafael Haro (Diseño y colaborador)
 * @author Cristian Alejadnro (Colaborador)
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Controlador_admin {
    /**
     * Texto de bienvenida
     */
    @FXML
    private Text welcomeText;
    /**
     * Botones para gestionar el DAO
     */
    @FXML
    private Button insert, delete, update, create;
    /**
     * Campos para extraer los datos y manejarlos en la BBDD
     */
    @FXML
    private TextField id, marca, modelo, anio, tipo, precio;
    /**
     * Tabla que almacena los datos
     */
    @FXML
    private TableView<Vehiculos> coches;
    /**
     * Columna de la tabla
     */
    @FXML
    private TableColumn<Vehiculos, Integer> ids;
    /**
     * Columna de la tabla
     */
    @FXML
    private TableColumn<Vehiculos, String> marcas;
    /**
     * Columna de la tabla
     */
    @FXML
    private TableColumn<Vehiculos, String> modelos;
    /**
     * Columna de la tabla
     */
    @FXML
    private TableColumn<Vehiculos, Integer> anios;
    /**
     * Columna de la tabla
     */
    @FXML
    private TableColumn<Vehiculos, Float> precios;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text txid;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text txmarca;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text txmodelo;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text txaño;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text tectipo;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text txprecio;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text labelId;
    /**
     * Texto que indica el campo
     */
    @FXML
    private Text labelMarca;
    /**
     * Pestaña de coches del TabPane
     */
    @FXML
    private Tab tabcoches;
    /**
     * Pestaña de motos del TabPane
     */
    @FXML
    private Tab tabmotos;
    /**
     * Pestaña de furgonetas del TabPane
     */
    @FXML
    private Tab tabfurgonetas;
    /**
     * Cambia a la pestaña de gestionar
     */
    @FXML
    private Button gestionarVehiculosButton;
    /**
     * Botón que almacena la lógica de generación de informes de coches
     */
    @FXML
    private Button informeCoches;
    /**
     * Botón que almacena la lógica de generación de informes de ventas
     */
    @FXML
    private Button informeVentas;
    /**
     * Botones que permiten moverse entre las pestañas de pagina principal y mis vehiculos
     */
    @FXML
    private Button paginaPrincipalButton, mostrarVehiculosButton;
    /**
     * Combobox con los idiomas
     */
    @FXML
    private ComboBox<String> comboBoxIdiomas;
    /**
     * Maneja los datos de vehiculo
     */
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    /**
     * Almacena los vehiculos en una lista
     */
    private ObservableList<Vehiculos> listaVehiculos;
    /**
     * Gestiona idiomas
     */
    private Locale locale;
    /**
     * Gestiona idiomas
     */
    private ResourceBundle bundle;


    /**
     * Inicializa la interfaz gráfica y configura las columnas de la tabla
     * y los listeners para los eventos de los botones.
     */
    @FXML
    public void initialize() {
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
            updateText();
        });
        configurarColumnas();
        configurarListeners();

    }
    /**
     * Metodo que cambia los idiomas
     */
    public void updateText() {
        // Actualizar textos generales
        welcomeText.setText(bundle.getString("welcome.text"));

        // Actualizar textos de las pestañas
        tabcoches.setText(bundle.getString("tab.coches"));
        tabmotos.setText(bundle.getString("tab.motos"));
        tabfurgonetas.setText(bundle.getString("tab.furgonetas"));

        // Actualizar textos de los botones
        paginaPrincipalButton.setText(bundle.getString("button.paginaPrincipal"));
        gestionarVehiculosButton.setText(bundle.getString("button.gestionarVehiculos"));
        mostrarVehiculosButton.setText(bundle.getString("button.mostrarVehiculos"));
        insert.setText(bundle.getString("button.anadir"));
        delete.setText(bundle.getString("button.borrar"));
        update.setText(bundle.getString("button.modificar"));
        create.setText(bundle.getString("button.mostrar"));

        // Actualizar textos de las etiquetas
        txid.setText(bundle.getString("label.id"));
        txmarca.setText(bundle.getString("label.marca"));
        txmodelo.setText(bundle.getString("label.modelo"));
        txaño.setText(bundle.getString("label.anio"));
        tectipo.setText(bundle.getString("label.tipo"));
        txprecio.setText(bundle.getString("label.precio"));

        // Actualizar textos de las columnas de la tabla
        ids.setText(bundle.getString("label.id"));
        marcas.setText(bundle.getString("label.marca"));
        modelos.setText(bundle.getString("label.modelo"));
        anios.setText(bundle.getString("label.anio"));
        precios.setText(bundle.getString("label.precio"));
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

        informeVentas.setOnAction(event -> generarReporteVentas());
        informeCoches.setOnAction(event -> generarReporteCoche());
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
                    vehiculoDAO.cargarVehiculos();
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
                    vehiculoDAO.cargarVehiculos();
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
                vehiculoDAO.cargarVehiculos();
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
     * Método que genera el informe de ventas
     */

    public void generarReporteVentas() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = ConexionBD.getConexion();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                Map<String, Object> parametros = new HashMap<>();
                JasperPrint print = JasperFillManager.fillReport("Informes/Informe_Ventas.jasper", parametros, conn);
                JasperExportManager.exportReportToPdfFile(print, selectedFile.getAbsolutePath());

                System.out.println("Informe generado con éxito en: " + selectedFile.getAbsolutePath());
            } else {
                System.out.println("La selección del archivo fue cancelada.");
            }
        } catch (JRException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Método que genera el informe de ventas
     */
    @FXML
    public void generarReporteCoche() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = ConexionBD.getConexion();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                Map<String, Object> parametros = new HashMap<>();
                JasperPrint print = JasperFillManager.fillReport("Informes/Informe_Coches.jasper", parametros, conn);
                JasperExportManager.exportReportToPdfFile(print, selectedFile.getAbsolutePath());

                System.out.println("Informe generado con éxito en: " + selectedFile.getAbsolutePath());
            } else {
                System.out.println("La selección del archivo fue cancelada.");
            }
        } catch (JRException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}