package com.example.chessgame;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    GridPane board;

    public void initialize(){

        // Themes are Classic, Wood_grain, Marble, Glass, Futuristic

        Game game = new Game(board, "Futuristic");

    }
}