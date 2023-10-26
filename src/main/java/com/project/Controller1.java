package com.project;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Controller1 implements initialize {

    @FXML
    private Button button0, button1, button2;
    @FXML
    private Rectangle bar;
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23, img24;
    @FXML
    private AnchorPane container;
    @FXML
    private Label progress;

    private ArrayList<ImageView> imgs= new ArrayList<ImageView>();

    private boolean loadingImage = true;

    // Mètode que inicialitza el Button button2 i l'ArrayList imgs
    
    @FXML
    private void initialize() {
        button2.setDisable(true);
        imgs.add(img1); imgs.add(img2); imgs.add(img3); imgs.add(img4); imgs.add(img5); imgs.add(img6); imgs.add(img7); imgs.add(img8);
        imgs.add(img9); imgs.add(img10); imgs.add(img11); imgs.add(img12); imgs.add(img13); imgs.add(img14); imgs.add(img15); imgs.add(img16);
        imgs.add(img17); imgs.add(img18); imgs.add(img19); imgs.add(img20); imgs.add(img21); imgs.add(img22); imgs.add(img23); imgs.add(img24);
    }

    // Mètode per canviar la vista actual a la vista 0

    @FXML
    private void animateToView0(ActionEvent event) {
        UtilsViews.setViewAnimating("View0");
    }

    // Mètode per establir el valor d'un booleà que s'encarregarà de detenir el procés de càrrega de les imatges

    @FXML
    private void stopImage() {
        button2.setDisable(true);
        loadingImage = false;
    }

    // Mètode per iniciar el mètode loadImageAtIndex, el qual carregarà una imatge en un ImageView en concret

    @FXML
    private void loadImage() {
        button1.setDisable(true);
        loadImageAtIndex(0);
    }
    
    // Mètode per carregar una imatge en un ImageView en concret

    private void loadImageAtIndex(int index) {
        if (!loadingImage) {
            progress.setText("0");
            bar.setWidth(0);
            for (int i=0; i < imgs.size(); i++) {
                imgs.get(i).setImage(null);
            }
            this.loadingImage = true;
            button1.setDisable(false);
        }
        else {
            if (index < imgs.size()) {
                final double barWidth = (index + 1) * (766.0 / 24.0);
                System.out.println("Loading image...");
                loadImageBackground((image) -> {
                    System.out.println("Image " + (index + 1) + " loaded");
                    imgs.get(index).setImage(image); // Estableix una imatge per l'ImageView actual
                    bar.setWidth(barWidth); // Augmenta la mida de la barra de progrés
                    int currentImageIndex = index + 1; // Actualiza l'índex de l'imatge actual
                    // Programa la càrrega de la pròxima imatge després d'un retard
                    Platform.runLater(() -> {
                        progress.setText(String.valueOf(index + 1));
                        loadImageAtIndex(currentImageIndex);
                    });
                }, "image" + (index + 1) + ".png");
            }
        }
    }
    
    public void loadImageBackground(Consumer<Image> callBack, String imageName) {
        Random random = new Random();
        // Utilitza un thread per tal d'evitar bloquejar l'UI
        CompletableFuture<Image> futureImage = CompletableFuture.supplyAsync(() -> {
            try {
                // Espera entre 5 i 50 segons per simular un temps de càrrega llarg
                Thread.sleep(random.nextInt(50000) + 5000);

                // Desbloqueja el botó d'aturar en cas de que es trobi bloquejat
                if (button2.isDisabled() && loadingImage) { button2.setDisable(false); }

                // Carrega l'informació de la carpeta assets
                Image image = new Image(getClass().getResource("/assets/" + imageName).toString());
                return image;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        futureImage.thenAcceptAsync(result -> {
            callBack.accept(result);
        }, Platform::runLater);
    }
}