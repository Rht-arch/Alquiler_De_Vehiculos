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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
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
    @FXML
    private Text welcomeText;

    @FXML
    private Tab tabCoches;

    @FXML
    private Tab tabMotos;

    @FXML
    private Tab tabFurgonetas;
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
    private Text txid;

    @FXML
    private Text txmarca;

    @FXML
    private Text txmodelo;

    @FXML
    private Text txaño;

    @FXML
    private Text tectipo;

    @FXML
    private Text txprecio;

    @FXML
    private Text labelId;

    @FXML
    private Text labelMarca;
    @FXML
    private Tab tabcoches;

    @FXML
    private Tab tabmotos;

    @FXML
    private Tab tabfurgonetas;

    @FXML
    private Text labelModelo;

    @FXML
    private Text labelAnio;

    @FXML
    private Text labelTipo;

    @FXML
    private Text labelPrecio;

    @FXML
    private Button gestionarVehiculosButton;

    @FXML
    private Button informeCoches;

    @FXML
    private Button informeVentas;

    @FXML
    private Button paginaPrincipalButton, mostrarVehiculosButton;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ObservableList<Vehiculos> listaVehiculos;
    private Locale locale;
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

        informeVentas.setOnAction(event -> generarInformeVentas());
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

    private void generarInformeVentas() {
        try {
            // Cargar el archivo .jasper directamente
            InputStream reportStream = getClass().getResourceAsStream("/Informes/Informe_Ventas.jasper");
            if (reportStream == null) {
                System.out.println("Error: No se pudo encontrar el archivo .jasper del informe.");
                return;
            }

            // Cargar el informe compilado (.jasper)
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

            // Establecer conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alquiler_vehiculos_db", // URL de conexión
                    "root", // Usuario
                    "" // Contraseña
            );

            // Llenar el informe con los datos (sin parámetros)
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, conexion);

            // Seleccionar ubicación del archivo PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe de Ventas");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("Informe_Ventas.pdf");

            // Crear el diálogo de guardar archivo
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                // Exportar el informe a PDF
                JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());
                System.out.println("Informe de ventas generado en: " + file.getAbsolutePath());
            } else {
                System.out.println("La operación fue cancelada por el usuario.");
            }

            // Cerrar la conexión
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de la base de datos.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (JRException e) {
            System.out.println("Error al generar el informe: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}