package com.example.chequers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneManager {
public LoginController login_controller;
public OptionController option_controller;
public EventHandler<MouseEvent> handler;
public Client user;
    public SceneManager(Stage stage) throws IOException {
        FXMLLoader login_loader = new FXMLLoader(getClass().getResource("login_menu.fxml"));
        Scene scene = new Scene (login_loader.load());
        login_controller = login_loader.getController();

        handler = mouseEvent -> {
            if (login_controller.logged_in){
                try {
                    switchToOption(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        login_controller.login.addEventFilter (MouseEvent.MOUSE_CLICKED, handler);

        stage.setTitle("Chequers");
        stage.setScene(scene);
        String imagePath = String.valueOf (getClass().getResource("icon.jpg"));
        stage.getIcons().add (new Image(imagePath));
        stage.show();
    }

    public void switchToOption (Stage stage) throws IOException {
        FXMLLoader option_loader = new FXMLLoader(getClass().getResource("option_window.fxml"));
        Scene scene = new Scene (option_loader.load());

        option_controller = option_loader.getController();
        option_controller.nick.setText (login_controller.nick);

        user = new Client (login_controller.nick);
        option_controller.client = user;

        stage.setScene(scene);
        stage.show();
    }
}
