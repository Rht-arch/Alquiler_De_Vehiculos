package org.example.alquiler_vehiculos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Clase que controla la segunda pantalla del splash
 * @author Alicia Pacheco Mena
 */
public class SplashController2 {
    /**
     * ImageView que recoge el gif
     */
    @FXML
    private ImageView gifImageView;
    /**
     * ProgressBar para cargar la barra de progreso
     */
    @FXML
    private ProgressBar progressBar;


    /**
     * Metodo que se gestiona el tiempo de carga de la barra de progreso
     * @param onComplete Devuelve que se ha completado el progreso de carga
     */
    public void startSplash(Runnable onComplete) {
        try {
            String gifPath = getClass().getResource("/imagenes/splash2.gif").toExternalForm();
            Image gifImage = new Image(gifPath);
            gifImageView.setImage(gifImage);

            // Crear un timeline para la animación de la barra de progreso
            Timeline timeline = new Timeline(

                    new KeyFrame(Duration.ZERO, e -> progressBar.setProgress(0.0)),  // Empezamos con el 0%
                    new KeyFrame(Duration.seconds(1), e -> progressBar.setProgress(0.25)), // 1 segundo => 25%
                    new KeyFrame(Duration.seconds(2), e -> progressBar.setProgress(0.5)),  // 2 segundos => 50%
                    new KeyFrame(Duration.seconds(3), e -> progressBar.setProgress(0.75)), // 3 segundos => 75%
                    new KeyFrame(Duration.seconds(4), e -> progressBar.setProgress(1.0))   // Al final de 4 segundos => 100%
            );

            timeline.setOnFinished(e -> onComplete.run());

            timeline.setCycleCount(1);
            timeline.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
