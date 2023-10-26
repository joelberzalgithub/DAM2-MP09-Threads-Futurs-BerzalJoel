package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Controller0 {

    @FXML
    private Button button0, button1, button2, button3;
    @FXML
    private Rectangle bar1, bar2, bar3;
    @FXML
    private AnchorPane container;
    @FXML
    private Label percentatge1, percentatge2, percentatge3;

    private ExecutorService executor = Executors.newFixedThreadPool(2); // Creem una pool de dos fils

    private boolean runningTask1 = false, runningTask2 = false, runningTask3 = false, completedTask1 = false, completedTask2 = false, completedTask3 = false;

    // Mètode per canviar el valor d'un booleà pel seu contrari (de true a false i de false a true)

    private boolean setRunningTask(boolean runningTask) {
        if (runningTask) { runningTask = false; }
        else { runningTask = true; }
        return runningTask;
    }

    // Mètode per canviar el text d'un botó en funció del valor d'un booleà

    private String setButtonText(boolean runningTask) {
        String buttonText = "";
        if (runningTask) { buttonText = "Aturar"; }
        else { buttonText = "Iniciar"; }
        return buttonText;
    }

    // Mètode per canviar la vista actual a la vista 1

    @FXML
    private void animateToView1(ActionEvent event) {
        UtilsViews.setViewAnimating("View1");
    }

    // Mètode per executar la primera tasca

    @FXML
    private void runTask1() {
        if (!completedTask1) { runningTask1 = setRunningTask(runningTask1); }
        button1.setText(setButtonText(runningTask1));
        backgroundTask(1);
    }

    // Mètode per executar la segona tasca

    @FXML
    private void runTask2() {
        if (!completedTask2) { runningTask2 = setRunningTask(runningTask2); }
        button2.setText(setButtonText(runningTask2));
        backgroundTask(2);
    }

    // Mètode per executar la tercera tasca

    @FXML
    private void runTask3() {
        if (!completedTask3) { runningTask3 = setRunningTask(runningTask3); }
        button3.setText(setButtonText(runningTask3));
        backgroundTask(3);
    }

    // Mètode per executar les tasques en funció d'un Integer

    private void backgroundTask(int index) {
        Random random = new Random();
        // Executa la tasca
        executor.submit(() -> {
            try {

                // Executa la primera tasca
                
                if (index == 1) {
                    double bar1Width = 0;
                    for (int i = 0; i <= 100; i++) {
                        if (!runningTask1) {
                            bar1.setWidth(0);
                            Platform.runLater(() -> { percentatge1.setText("0%"); });
                            break;
                        }
                        else {
                            if (completedTask1) {
                                Platform.runLater(() -> {
                                    percentatge1.setText("0%");
                                    completedTask1 = false;
                                    bar1.setWidth(0);
                                });
                            }
                            final int currentValue = i;
                            Platform.runLater(() -> {
                                percentatge1.setText(String.valueOf(currentValue) + "%");
                                if (currentValue == 100) { button1.setText("Iniciar"); }
                            });
                            Thread.sleep(1000);
                            System.out.println("Updating label: " + index + ", Value: " + currentValue);
                            if (i == 100) {
                                bar1Width = 672.5;
                                completedTask1 = true;
                            }
                            else {
                                bar1Width += 6.725;
                            }
                            bar1.setWidth(bar1Width);
                        }
                    }
                }
                
                // Executa la segona tasca

                else if (index == 2) {
                    int randomNumber = (random.nextInt(3) + 2);
                    double bar2Width = 0;
                    int i = 0;
                    while (true) {
                        final int currentValue = i;
                        if (!runningTask2) {
                            bar2.setWidth(0);
                            Platform.runLater(() -> { percentatge2.setText("0%"); });
                            break;
                        }
                        else {          
                            if (i >= 100) {
                                Platform.runLater(() -> {
                                    percentatge2.setText("100%");
                                    button2.setText("Iniciar");
                                    completedTask2 = true;
                                    bar2.setWidth(672.5);
                                });
                                break;
                            }
                            else {
                                if (completedTask2) {
                                    Platform.runLater(() -> {
                                        percentatge2.setText("0%");
                                        completedTask2 = false;
                                        bar2.setWidth(0);
                                    });
                                }
                                Platform.runLater(() -> { percentatge2.setText(String.valueOf(currentValue) + "%"); });
                                bar2Width += 6.725 * (1 + randomNumber);
                                Thread.sleep(random.nextInt(4900) + 3000);
                                System.out.println("Updating label: " + index + ", Value: " + currentValue);
                                bar2.setWidth(bar2Width);
                                i += 1 + randomNumber;
                            }
                        }
                    }
                }
                
                // Executa la tercera tasca

                else if (index == 3) {
                    int randomNumber = (random.nextInt(3) + 2) + (random.nextInt(5) + 4);
                    double bar3Width = 0;
                    int i = 0;
                    while (true) {
                        final int currentValue = i;
                        if (!runningTask3) {
                            bar3.setWidth(0);
                            Platform.runLater(() -> { percentatge3.setText("0%"); });
                            break;
                        }
                        else {
                            if (i >= 100) {
                                Platform.runLater(() -> {
                                    percentatge3.setText("100%");
                                    button3.setText("Iniciar");
                                    bar3.setWidth(672.5);
                                    completedTask3 = true;
                                });
                                break;
                            }
                            else {
                                if (completedTask3) {
                                    Platform.runLater(() -> {
                                        percentatge3.setText("0%");
                                        completedTask3 = false;
                                        bar3.setWidth(0);
                                    });
                                }
                                Platform.runLater(() -> { percentatge3.setText(String.valueOf(currentValue) + "%"); });
                                bar3Width += 6.725 * (1 + randomNumber);
                                Thread.sleep(random.nextInt(7900) + 3000);
                                System.out.println("Updating label: " + index + ", Value: " + currentValue);
                                bar3.setWidth(bar3Width);
                                i += 1 + randomNumber;
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    
    // Aquesta funció la cridaries quan vulguis tancar l'executor (per exemple, quan tanquis la teva aplicació)

    public void stopExecutor() {
        executor.shutdown();
    }
}