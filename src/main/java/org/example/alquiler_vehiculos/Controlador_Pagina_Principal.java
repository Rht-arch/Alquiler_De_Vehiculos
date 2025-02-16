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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.BD.Vehiculos;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;
import org.example.alquiler_vehiculos.DAO.ClientesDAO;
import org.example.alquiler_vehiculos.DAO.VehiculoDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Clase que controla la Pagina Principal version admin
 * en esta se realiza la busqueda del vehiculo deseado de manera
 * general o mediante los filtros deseados y escogiendo las fechas
 * permite realizar el alquiler / pasar a la pantalla de compra.
 * Tambien permite al admin ver sus vehiculos para pruebas y ir
 * a la ventana de gestión para modificar los vehiculos de la base
 *  @author Rafael Haro (Desarrollador Principal)
 *  @author Alicia Pacheco (Colaborador)
 *  @author Cristian Alejandro (Colaborador)
 */

public class Controlador_Pagina_Principal {

    ClientesDAO clientesDAO = new ClientesDAO();
    /**
     * Campos que almacenan los datos de las fechas
     */
    @FXML
    DatePicker fechaInicio,fechaFin;
    /**
     * Botones que permite abrir y cerrar filtros y ir a la pantalla de compra
     */
    @FXML
    Button busqueda,cerrar,comprar;

    /**
     * Pane de la pantalla filtro
     */
    @FXML
    Pane filtros;

    @FXML
    private Text txtWelcome;
    /**
     * ComboBox para marca, modelo y tipo
     */
    @FXML
    ComboBox<String> marca, modelo , tipo;
    /**
     * ComboBox para el año
     */
    @FXML
    ComboBox<Integer> anio;
    /**
     * ComboBox para el precio
     */
    @FXML
    ComboBox<Float> precio;
    /**
     * Tab que recoge los tipos de vehiculos
     */
    @FXML
    Tab coche,moto,camion;
    /**
     * Combobox que gestiona el idioma de la ventana
     */
    @FXML
    private ComboBox<String> comboBoxIdiomas;

    /**
     * Elementos de la tabla y generación de la tabla
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
     * Lista de vehiculos
     */
    private ObservableList<Vehiculos> vehiculosObservableList = FXCollections.observableArrayList();
    /**
     * Variable para los Vehiculos seleccionados
     */
    private Vehiculos vehiculoSeleccionado;  // Variable para almacenar el vehículo seleccionado

    /**
     * Recoge el nombre
     */
    @FXML
    Text nombre;
    @FXML
    private Label txFecha;

    @FXML
    private Label txFechaF;
    /**
     * Botón que permite cambiar a la pantalla principal
     */
    @FXML
    private Button btprincipal;
    /**
     * Botón que cambia a la venta de gestión de vehiculo
     */
    @FXML
    private Button btgestionar;
    /**
     * Botón que cambia a mis vehiculos
     */
    @FXML
    private Button btvehiculos;


    /**
     * Llamada a la clase VehiculoDAO
     */
    VehiculoDAO vehiculoDAO = new VehiculoDAO();
    /**
     * Mapa de los modelos por Marca
     */
    Map<String,String[]> modelosPorMarca = new HashMap<>();
    /**
     * Mapa de lso modelos pr tipo
     */
    Map<String, String[]> marcasPorTipo = new HashMap<>();

    /**
     * Variables para establecer el idioma
     */
    private Locale locale;
    private ResourceBundle bundle;
    /**
     * Variable que recibe la id del usuario de login
     */
    private int userId;
    /**
     * Metodo que incializa los componentes
     */
    @FXML
    public void initialize() {
        // Idioma por defecto
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.getSelectionModel().select("Español");

        this.userId = SesionUsuario.getInstancia().getUserId();
        System.out.println("ID del usuario recibido en Página Principal: " + userId);
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
            updateTexts();
        });

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

       // Agregar valores de años desde 2010 a 2024
        for (int i = 2010; i <= 2024; i++) {
            anio.getItems().add(i);
        }

        // Agregar valores de precios desde 40 a 145
        for (float p = 40; p <= 145; p += 5) { // Incremento de 5 en 5
            precio.getItems().add(p);
        }

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

    /**
     * Método que permite actualizar todos los textos en función del
     * idioma seleccionado
     */
    private void updateTexts() {

        txtWelcome.setText(bundle.getString("welcome.text"));

        // Actualizar los textos de las pestañas
        coche.setText(bundle.getString("tab.coches"));
        moto.setText(bundle.getString("tab.motos"));
        camion.setText(bundle.getString("tab.furgonetas"));

        comprar.setText(bundle.getString("button.irCompra"));
        cerrar.setText(bundle.getString("button.guardarFiltros"));

        // Actualizar los textos de las etiquetas
        txFecha.setText(bundle.getString("label.fechaInicio"));
        txFechaF.setText(bundle.getString("label.fechaFin"));

        // Actualizar los textos de los ComboBox
        marca.setPromptText(bundle.getString("combobox.marca"));
        modelo.setPromptText(bundle.getString("combobox.modelo"));
        anio.setPromptText(bundle.getString("combobox.anio"));
        tipo.setPromptText(bundle.getString("combobox.tipo"));
        precio.setPromptText(bundle.getString("combobox.precio"));

        // Actualizar los textos de las columnas de la tabla
        ids.setText(bundle.getString("table.id"));
        marcas.setText(bundle.getString("table.marca"));
        modelos.setText(bundle.getString("table.modelo"));
        anios.setText(bundle.getString("table.anio"));
        precios.setText(bundle.getString("table.precio"));
        // Actualizar los textos de los botones
        btprincipal.setText(bundle.getString("button.paginaPrincipal"));
        btvehiculos.setText(bundle.getString("button.misVehiculos"));
        btgestionar.setText(bundle.getString("button.gestionarVehiculos"));





        // Actualizar los textos de las columnas de la tabla
        ids.setText(bundle.getString("table.id"));
        marcas.setText(bundle.getString("table.marca"));
        modelos.setText(bundle.getString("table.modelo"));
        anios.setText(bundle.getString("table.anio"));
        precios.setText(bundle.getString("table.precio"));
    }

    /**
     * Este método abre la pestaña de filtros y muestra los campos para filtrar
     * @param mouseEvent Recoge una evento de ratón
     */

    public void abrirFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            filtros.setVisible(true);
            tipo.getSelectionModel().clearSelection();
            tipo.setPromptText("--Tipo--");
            marca.getSelectionModel().clearSelection();
            modelo.getSelectionModel().clearSelection();
        }

    }

    /**
     * Este método se encarga de realizar la consulta en función de los parametros seleccionados
     * al pulsar el botón este se cierra los filtros y muestra los vehiculos filtrados
     *
     * @param mouseEvent Recoge un evento de ratón
     */
    public void cerrarFiltros(javafx.scene.input.MouseEvent mouseEvent) {
        filtros.setVisible(false);
        String marcaSeleccionada = marca.getValue();
        String tipoSeleccionado = tipo.getValue();
        String modeloSeleccionada = modelo.getValue();
        Integer anioSeleccionado = anio.getValue();
        Float precioSeleccionado = precio.getValue();
        String sql = "SELECT * FROM vehiculos WHERE 1=1 ";
        if (tipoSeleccionado != null) {
             sql += " AND tipo ='" + tipoSeleccionado + "'";
            if(marcaSeleccionada != null) {
                sql += " AND marca = '"+marcaSeleccionada+"'";
                if(modeloSeleccionada != null) {
                    sql+= " AND modelo = '"+modeloSeleccionada+"'";
                }
            }
        } else if (anioSeleccionado != null) {
            sql += " AND año ='" + anioSeleccionado + "'";

        } else if (precioSeleccionado != null) {
            sql += " AND precio_dia <='" + precioSeleccionado + "'";
        }
        List<Vehiculos> vehiculos = vehiculoDAO.obtenerVehiculosConFiltro(sql);
        mostrarenTabla(vehiculos);
    }

    /**
     * Método que muestra los vehiculos en la tablas en función de los filtros seleccionados
     * @param vehiculosList List con los vehiculos de la base de datos
     */
    public void mostrarenTabla(List<Vehiculos> vehiculosList) {
        vehiculosObservableList.clear();
        vehiculosObservableList.addAll(vehiculosList);
        coches.setItems(vehiculosObservableList);
        motos.setItems(vehiculosObservableList);
        camions.setItems(vehiculosObservableList);
    }

    /**
     * Este metodo es el encargado de obtener el vehiculo elegido por el cliente y
     * y sirve para enlazarlo al metodo compra() que genera el vehiculo para la compra
     */
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

    /**
     * Metodo que realiza la compra
     */
    @FXML
    public void compra() {
        if (vehiculoSeleccionado != null) { // Verificamos que haya un vehículo seleccionado
            // Obtenemos las fechas seleccionadas
            LocalDate ini = fechaInicio.getValue();
            LocalDate fin = fechaFin.getValue();

            // Validación de fechas
            if (ini == null || fin == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Fechas requeridas", "Por favor, selecciona una fecha de inicio y una fecha de fin para continuar con la compra.");
                return;
            }

            if (!fin.isAfter(ini)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Fechas incorrectas", "La fecha de fin debe ser posterior a la fecha de inicio.");
                return;
            }

            // Calculamos el total de alquiler
            double total = vehiculoSeleccionado.getPreciodia() * Period.between(ini, fin).getDays();

            // Creamos el detalle de alquiler
            AlquilerDetalle alquilerDetalle = new AlquilerDetalle(
                    vehiculoSeleccionado.getMarca(),
                    vehiculoSeleccionado.getModelo(),
                    vehiculoSeleccionado.getTipo(),
                    userId,
                    ini,
                    fin,
                    total
            );

            // Enviar la información a la vista de detalle de compra
            enviarAVistaDetalle(alquilerDetalle);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Vehículo no seleccionado", "Por favor, selecciona un vehículo antes de continuar con la compra.");
        }

        // Limpiar selección en la tabla y los filtros
        coches.getSelectionModel().clearSelection();
        motos.getSelectionModel().clearSelection();
        camions.getSelectionModel().clearSelection();
        tipo.getSelectionModel().clearSelection();
        marca.getSelectionModel().clearSelection();
        modelo.getSelectionModel().clearSelection();
        anio.getSelectionModel().clearSelection();
        precio.getSelectionModel().clearSelection();
        fechaInicio.setValue(null);
        fechaFin.setValue(null);
    }

    /**
     * Metodo para mostrar una alerta
     * @param tipo Tipo de alerta (ERROR, WARNING, INFO, etc.)
     * @param titulo Título de la ventana de alerta
     * @param mensaje Mensaje a mostrar en la alerta
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    /**
     * Metodo que envia toda la informacion recogida a la siguiente pantalla
     * @param alquilerDetalle Variable para recoger datos
     */
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
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de compra: " + e.getMessage());
        }
    }

    /**
     * Metodo que carga la patalla mis vehiculos
     */
    @FXML
    public void cargarMisVehiculos() {
        if (bundle == null) {
            System.out.println("⚠ Advertencia: bundle es NULL, cargando idioma por defecto...");
            bundle = ResourceBundle.getBundle("org.example.alquiler_vehiculos.idioma", new Locale("es", "ES"));
        }

        try {
            String titulo = bundle.getString("title.misVehiculos"); // Asegúrate de que la clave existe
            System.out.println("Clave encontrada: " + titulo);
        } catch (MissingResourceException e) {
            System.out.println("ERROR: No se encontró la clave 'title.misVehiculos' en el archivo de propiedades.");
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MisVehiculos.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) busqueda.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("title.misVehiculos")); // Usa la clave del idioma
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la pantalla Mis Vehículos: " + e.getMessage());
        }
    }


    /**
     * Método que permite cambiar a la ventana de gestión de vehiculos
     * @param mouseEvent Recoge un evento de teclado
     * @throws IOException
     */
    @FXML
    public void cargarGestion(MouseEvent mouseEvent) throws IOException {
        Stage currentStage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        CambiarPantallas.switchScene(currentStage, "admin.fxml", "Gestionar Vehiculos");
    }
}
