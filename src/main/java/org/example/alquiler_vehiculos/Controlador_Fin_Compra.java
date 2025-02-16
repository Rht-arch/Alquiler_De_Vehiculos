package org.example.alquiler_vehiculos;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.DAO.AlquilerDAO;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona el fin de compra,generando un informe
 * que simula un recibo de compra para justificar el alquiler
 * @author Rafael Haro (Desarrollador Principal)
 * @author Cristian Alejandro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class Controlador_Fin_Compra {
    /**
     * Link que permite descargar el archivo
     */
    @FXML
    public Hyperlink descarga;
    /**
     * Permite manejar datos de la BBDD
     */
    AlquilerDAO alquilerDAO = new AlquilerDAO();

    /**
     * Variable con el alquiler determinado
     */
    private int idAlquiler;

    /**
     * Configura la id del alquiler para que poder imprimir el informe
     * @param idAlquiler Id del alquiler a justificar
     */
    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    /**
     * Método que permite generar el reporte del recibo de compra
     * en función a la id del alquiler y permite al usuario guardarlo
     * donde el prefiera
     */
    @FXML
    public void generarReporte() {
        if (idAlquiler == 0) {
            System.out.println("Error: No se ha recibido una ID de alquiler válida.");
            return;
        }

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = ConexionBD.getConexion();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("id_alquiler", idAlquiler);

                JasperPrint print = JasperFillManager.fillReport("Informes/Contrato.jasper", parametros, conn);
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