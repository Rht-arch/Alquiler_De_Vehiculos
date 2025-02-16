package org.example.alquiler_vehiculos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.alquiler_vehiculos.BD.Vehiculos;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Clase que controla el funcionamiento de la pantalla usuario
 *
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */

public class Controlador_Usuario {
    /**
     * Button al accionar realiza una accion
     */
    @FXML
        Button busqueda,cerrar;
    /**
     * Panel que recoge la pantalla de filtro
     */
        @FXML
        Pane filtros;

    /**
     * ComboBox para recoger marca, modelo y tipo en el filtro
     */
    @FXML
        ComboBox<String> marca, modelo , tipo;

    /**
     * ComboBox que recoge el año
     */
    @FXML
        ComboBox<Integer> anio;
    /**
     * ComboBox que recoge el precio
     */
        @FXML
        ComboBox<Float> precio;

    /**
     * Tab que recoge lso tipso de coches que ofertamos
     */
    @FXML
        Tab coche,moto,camion;

    /**
     * Parametos recogido en la tabla
     */
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

    /**
     * Lista que recoge lso Vehiculos
     */
    private ObservableList<Vehiculos> vehiculosObservableList = FXCollections.observableArrayList();

    /**
     * Recoge el nombre
     */
        @FXML
        Text nombre;

    /**
     * Llama a la clase vehiculoDAO
     */
    VehiculoDAO vehiculoDAO = new VehiculoDAO();

    /**
     * Mapa pra recoger los modelos por Marca
     */
        Map<String,String[]> modelosPorMarca = new HashMap<>();
    /**
     * Mapa que recoge los tipos de Marca
     */
    Map<String, String[]> marcasPorTipo = new HashMap<>();

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("ID del usuario recibido en Página Principal: " + userId);
    }
    /**
     * Metodo que inicializa todos los componentes
     */
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

        }


    /**
     * Metodo que abre el panel de filtros
     * @param mouseEvent Cuando se clique se abre
     */
    public void abrirFiltros(javafx.scene.input.MouseEvent mouseEvent) {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                filtros.setVisible(true);
            }

        }

    /**
     * Metodo que cierra el panel de filtros
     * @param mouseEvent Cuando se clique se cierra
     */
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

    /**
     * Metodo que muestra la tbla
     * @param vehiculosList lista que recoge todos los vehiculos
     */
    public void mostrarenTabla(List<Vehiculos> vehiculosList) {
            vehiculosObservableList.clear();
            vehiculosObservableList.addAll(vehiculosList);
            coches.setItems(vehiculosObservableList);
            motos.setItems(vehiculosObservableList);
            camions.setItems(vehiculosObservableList);
        }

    }


