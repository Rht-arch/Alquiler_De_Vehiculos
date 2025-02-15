package org.example.alquiler_vehiculos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que inicia toda la aplicacion
 * @authors Alicia Pacheco, Rafael Haro y Cristian Alejandro
 */
public class HelloApplication extends Application {
    /**
     * Metodo que inicia toda la aplicacion
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Mostrar_Vehiculo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 350);
        stage.setTitle("Alquiler de coches");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Metodo que implementa el metodo start
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}