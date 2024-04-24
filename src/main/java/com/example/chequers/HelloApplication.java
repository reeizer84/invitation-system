package com.example.chequers;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        new SceneManager(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}