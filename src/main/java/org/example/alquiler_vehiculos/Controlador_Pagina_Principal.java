package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Controlador_Pagina_Principal {
    @FXML
    Button busqueda,cerrar;

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
    Text nombre;

    Map<String,String[]> modelosPorMarca = new HashMap<>();
    Map<String, String[]> marcasPorTipo = new HashMap<>();

    @FXML
    public void initialize() {
        filtros.setVisible(false);
        tipo.getItems().addAll("Coche","Moto","Furgoneta/Camión");

       //nombre.setText(usuario.getNombre());


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


    public void abrirFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            filtros.setVisible(true);
        }

    }

    public void cerrarFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        filtros.setVisible(false);
        String marcaSeleccionada = marca.getValue();
        String tipoSeleccionado = tipo.getValue();
        String modeloSeleccionada = modelo.getValue();
        if (tipoSeleccionado != null) {
            String sql = "SELECT * FROM vehiculo WHERE tipo = '" + tipoSeleccionado + "'";
            if(marcaSeleccionada != null) {
                String sql2 = "SELECT * FROM vehiculo WHERE tipo = '"+tipoSeleccionado+"' AND marca = '"+marcaSeleccionada+"'";
                if(modeloSeleccionada != null) {
                    String sql3 = "SELECT * FROM vehiculos WHERE tipo = '"+tipoSeleccionado+"' AND marca = '"+marcaSeleccionada+"' AND modelo = '"+modeloSeleccionada+"'";
                }
            }
        }
//        VehiculoDAO vehiculoDAO = new VehiculoDAO();
//        tablaVehiculos.setItems(FXCollections.observableArrayList(
//                vehiculoDAO.vehiculoFiltros(marcaSeleccionada, tipoSeleccionado, modeloSeleccionada)
//        ));
    }
}
