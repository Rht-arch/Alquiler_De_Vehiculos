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

public class Controlador_Fin_Compra {
    @FXML
    Hyperlink descarga;

    AlquilerDAO alquilerDAO = new AlquilerDAO();
    private int idAlquiler;

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

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