package org.example.alquiler_vehiculos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.BD.Vehiculos;
import org.example.alquiler_vehiculos.DAO.ClientesDAO;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlador_Pagina_Principal {

    ClientesDAO clientesDAO = new ClientesDAO();

    @FXML
    DatePicker fechaInicio,fechaFin;

    @FXML
    Button busqueda,cerrar,comprar;

    @FXML
    Pane filtros;

    @FXML
    ComboBox<String> marca, modelo , tipo;
    @FXML
    ComboBox<Integer> anio;
    @FXML
    ComboBox<Float> precio;

    @FXML
    Tab coche,moto,camion;

    @FXML
    TableView<Vehiculos> coches,motos,camions;
    @FXML
    TableColumn<Vehiculos, Integer> ids ;
    @FXML
    TableColumn<Vehiculos, String> marcas ;
    @FXML
    TableColumn<Vehiculos, String> modelos ;
    @FXML
    TableColumn<Vehiculos, Integer> anios ;
    @FXML
    TableColumn<Vehiculos, Double> precios ;

    private ObservableList<Vehiculos> vehiculosObservableList = FXCollections.observableArrayList();
    private Vehiculos vehiculoSeleccionado;  // Variable para almacenar el vehículo seleccionado

    @FXML
    Text nombre;

    VehiculoDAO vehiculoDAO = new VehiculoDAO();

    Map<String,String[]> modelosPorMarca = new HashMap<>();
    Map<String, String[]> marcasPorTipo = new HashMap<>();

    @FXML
    public void initialize() {

        ids.setCellValueFactory(new PropertyValueFactory<>("id"));
        marcas.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modelos.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        anios.setCellValueFactory(new PropertyValueFactory<>("año"));
        precios.setCellValueFactory(new PropertyValueFactory<>("Preciodia"));

        coches.getColumns().setAll(ids, marcas, modelos, anios, precios);
        motos.getColumns().setAll(ids, marcas, modelos, anios, precios);
        camions.getColumns().setAll(ids, marcas, modelos, anios, precios);

        filtros.setVisible(false);
        tipo.getItems().addAll("Coche","Moto","Furgoneta/Camión");



        marcasPorTipo.put("Coche", new String[]{"Toyota", "Ford", "BMW", "Honda", "Volkswagen", "Audi", "Mercedes-Benz", "Nissan", "Peugeot", "Chevrolet", "Renault", "Fiat"});
        marcasPorTipo.put("Moto", new String[]{"Harley-Davidson", "Yamaha", "Ducati", "Kawasaki", "Suzuki", "Triumph", "Piaggio", "KTM"});
        marcasPorTipo.put("Furgoneta/Camión", new String[]{"Citroën", "Opel", "Mercedes-Benz", "Iveco", "MAN", "Scania"});


        modelosPorMarca.put("Toyota", new String[]{"Corolla", "Camry", "Rav4", "Yaris"});
        modelosPorMarca.put("Ford", new String[]{"Focus", "Mustang", "Explorer", "F-150"});
        modelosPorMarca.put("BMW", new String[]{"Serie 3", "X5", "M4", "i8"});
        modelosPorMarca.put("Honda", new String[]{"Civic", "CR-V", "Accord", "HR-V"});
        modelosPorMarca.put("Volkswagen", new String[]{"Golf", "Passat", "Tiguan", "Polo"});
        modelosPorMarca.put("Audi", new String[]{"A3", "A4", "Q5", "R8"});
        modelosPorMarca.put("Mercedes-Benz", new String[]{"Clase C", "Clase E", "GLA", "GLE"});
        modelosPorMarca.put("Nissan", new String[]{"Altima", "Sentra", "X-Trail", "GT-R"});
        modelosPorMarca.put("Peugeot", new String[]{"208", "3008", "508", "2008"});
        modelosPorMarca.put("Chevrolet", new String[]{"Cruze", "Camaro", "Silverado", "Tracker"});
        modelosPorMarca.put("Renault", new String[]{"Clio", "Megane", "Kangoo", "Duster"});
        modelosPorMarca.put("Fiat", new String[]{"500", "Panda", "Ducato", "Tipo"});

        // Motos
        modelosPorMarca.put("Harley-Davidson", new String[]{"Sportster", "Softail", "Road Glide", "Street Bob"});
        modelosPorMarca.put("Yamaha", new String[]{"R1", "MT-09", "Tenere 700", "XSR900"});
        modelosPorMarca.put("Ducati", new String[]{"Panigale", "Monster", "Multistrada", "Scrambler"});
        modelosPorMarca.put("Kawasaki", new String[]{"Ninja 400", "Z900", "Versys", "H2"});
        modelosPorMarca.put("Suzuki", new String[]{"GSX-R1000", "Hayabusa", "V-Strom", "SV650"});
        modelosPorMarca.put("Triumph", new String[]{"Bonneville", "Tiger 900", "Rocket 3", "Street Triple"});
        modelosPorMarca.put("Piaggio", new String[]{"Vespa Primavera", "Beverly", "Medley", "Liberty"});
        modelosPorMarca.put("KTM", new String[]{"Duke 390", "RC 200", "Super Adventure", "690 SMC R"});

        // Furgonetas y camiones
        modelosPorMarca.put("Citroën", new String[]{"Berlingo", "Jumpy", "Spacetourer", "Jumper"});
        modelosPorMarca.put("Opel", new String[]{"Vivaro", "Combo", "Movano", "Zafira Life"});
        modelosPorMarca.put("Mercedes-Benz", new String[]{"Vito", "Sprinter", "Citan", "eSprinter"});
        modelosPorMarca.put("Iveco", new String[]{"Daily", "Eurocargo", "Stralis", "S-Way"});
        modelosPorMarca.put("MAN", new String[]{"TGL", "TGM", "TGX", "TGS"});
        modelosPorMarca.put("Scania", new String[]{"P-Series", "G-Series", "R-Series", "S-Series"});

        tipo.setOnAction(event -> {
            marca.getItems().clear();
            modelo.getItems().clear();
            String tipoSeleccionado = tipo.getValue();
            if (tipoSeleccionado != null) {
                marca.getItems().addAll(marcasPorTipo.get(tipoSeleccionado));
            }
            if ("Coche".equals(tipoSeleccionado)) {
                coche.setDisable(false);  // Habilitar el tab de coches
                moto.setDisable(true);    // Deshabilitar el tab de motos (si lo deseas)
                camion.setDisable(true);
            } else if ("Moto".equals(tipoSeleccionado)) {
                coche.setDisable(true);
                camion.setDisable(true);
                moto.setDisable(false);
            } else if("Furgoneta/Camión".equals(tipoSeleccionado)) {
                coche.setDisable(true);
                moto.setDisable(true);
                camion.setDisable(false);
            }
        });
        // Agregar las marcas al ComboBox
        marca.getItems().addAll(modelosPorMarca.keySet());


        // Manejar selección de marca y actualizar modelos
        marca.setOnAction(event -> {
            modelo.getItems().clear(); // Limpiar modelos previos
            String marcaSeleccionada = marca.getValue();

            if (marcaSeleccionada != null) {
                modelo.getItems().addAll(modelosPorMarca.get(marcaSeleccionada));
            }
        });
        coches.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        motos.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        camions.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        // Eliminar el código que responde al clic en la tabla
        coches.setRowFactory(tv -> {
            TableRow<Vehiculos> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    vehiculoSeleccionado = row.getItem(); // Guardamos el vehículo seleccionado
                    System.out.println("Vehículo seleccionado: " + vehiculoSeleccionado.getMarca() + " " + vehiculoSeleccionado.getModelo());
                }
            });
            return row;
        });


    }


    public void abrirFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            filtros.setVisible(true);
            tipo.getSelectionModel().clearSelection();
            tipo.setPromptText("--Tipo--");
            marca.getSelectionModel().clearSelection();
            modelo.getSelectionModel().clearSelection();
        }

    }

    public void cerrarFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        filtros.setVisible(false);
        String marcaSeleccionada = marca.getValue();
        String tipoSeleccionado = tipo.getValue();
        String modeloSeleccionada = modelo.getValue();
        String sql = "SELECT * FROM vehiculos WHERE 1=1 ";
        if (tipoSeleccionado != null) {
             sql += " AND tipo ='" + tipoSeleccionado + "'";
            if(marcaSeleccionada != null) {
                sql += " AND marca = '"+marcaSeleccionada+"'";
                if(modeloSeleccionada != null) {
                    sql+= " AND modelo = '"+modeloSeleccionada+"'";
                }
            }
        }
        List<Vehiculos> vehiculos = vehiculoDAO.obtenerVehiculosConFiltro(sql);
        mostrarenTabla(vehiculos);
    }

    public void mostrarenTabla(List<Vehiculos> vehiculosList) {
        vehiculosObservableList.clear();
        vehiculosObservableList.addAll(vehiculosList);
        coches.setItems(vehiculosObservableList);
        motos.setItems(vehiculosObservableList);
        camions.setItems(vehiculosObservableList);
    }

    @FXML
    public void seleccionarVehiculo() {
        Vehiculos seleccionado = null;

        if (coches.getSelectionModel().getSelectedItem() != null) {
            seleccionado = coches.getSelectionModel().getSelectedItem();
        } else if (motos.getSelectionModel().getSelectedItem() != null) {
            seleccionado = motos.getSelectionModel().getSelectedItem();
        } else if (camions.getSelectionModel().getSelectedItem() != null) {
            seleccionado = camions.getSelectionModel().getSelectedItem();
        }

        if (seleccionado != null) {
            vehiculoSeleccionado = seleccionado; // Guardamos el vehículo para la compra
            System.out.println("Vehículo seleccionado: " + vehiculoSeleccionado.getModelo());
        } else {
            System.out.println("No se ha seleccionado ningún vehículo.");
        }
    }
    @FXML
    public void compra() {
        if (vehiculoSeleccionado != null) { // Verificamos que haya un vehículo seleccionado
            // Obtenemos las fechas seleccionadas
            LocalDate ini = fechaInicio.getValue();
            LocalDate fin = fechaFin.getValue();

            if (ini != null && fin != null) {
                // Calculamos el total de alquiler
                double total = vehiculoSeleccionado.getPreciodia() * Period.between(ini, fin).getDays();
                // Creamos el detalle de alquiler
                AlquilerDetalle alquilerDetalle = new AlquilerDetalle(
                        vehiculoSeleccionado.getMarca(),
                        vehiculoSeleccionado.getModelo(),
                        vehiculoSeleccionado.getTipo(),
                        ini,
                        fin,
                        total
                );

                enviarAVistaDetalle(alquilerDetalle);
            } else {
                System.out.println("Por favor, selecciona las fechas.");
            }
        } else {
            System.out.println("Por favor, selecciona un vehículo.");
        }
        coches.getSelectionModel().clearSelection();
        motos.getSelectionModel().clearSelection();
        camions.getSelectionModel().clearSelection();

        // Limpiar los filtros
        tipo.getSelectionModel().clearSelection();
        marca.getSelectionModel().clearSelection();
        modelo.getSelectionModel().clearSelection();
        anio.getSelectionModel().clearSelection();
        precio.getSelectionModel().clearSelection();
        fechaInicio.setValue(null);
        fechaFin.setValue(null);
    }


    private void enviarAVistaDetalle(AlquilerDetalle alquilerDetalle) {
        try {
            System.out.println("Abriendo la ventana de compra...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Compra.fxml"));
            Parent root = loader.load();

            Controlador_Compra controladorCompra = loader.getController();
            if (controladorCompra != null) {
                controladorCompra.setVehiculoSeleccionado(alquilerDetalle);
                System.out.println("Vehículo seleccionado enviado a la ventana de compra.");
            } else {
                System.out.println("Error: controladorCompra es null.");
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de compra: " + e.getMessage());
        }
    }


}
