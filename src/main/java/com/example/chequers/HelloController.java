package com.example.chequers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    private GridPane board = new GridPane();
    public void initialize(){
        board.setStyle ("-fx-border-color: black; -fx-border-width: 3px;");

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pane square = new Pane();
                square.setStyle("-fx-background-color: " + ((row + col) % 2 == 0 ? "#b5b2ae" : "#7a594d") + ";");
                board.add(square, col, row);
            }
        }

    }
}