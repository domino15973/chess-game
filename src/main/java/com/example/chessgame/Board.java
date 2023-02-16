package com.example.chessgame;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {

    GridPane board;
    String theme;
    public ArrayList<Square> squares = new ArrayList<>();

    public Board(GridPane board, String theme){
        this.board = board;
        this.theme = theme;

        makeBoard(this.board, theme);
    }

    private void makeBoard(GridPane board, String theme){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Square square = new Square(i, j);
                square.setName("Square" + i + j);
                square.setPrefHeight(100);
                square.setPrefWidth(100);
                square.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                setTheme(square, theme, i, j);
                board.add(square, i, j, 1, 1);
                squares.add(square);
            }
        }
        addPieces();
    }

    private void setTheme(Square square, String theme, int i, int j){
        Color color1 = Color.web("#ffffff00");
        Color color2 = Color.web("#ffffff00");

        switch (theme) {
            case "Classic" -> {
                color1 = Color.web("#FFFFFF"); //(white)
                color2 = Color.web("#000000"); //(black)
            }
            case "Wood_grain" -> {
                color1 = Color.web("#8B4513"); //(saddle brown)
                color2 = Color.web("#DEB887"); //(burlywood)
            }
            case "Marble" -> {
                color1 = Color.web("#FFFFFF"); //(white)
                color2 = Color.web("#363636"); //(dim gray)
            }
            case "Glass" -> {
                color1 = Color.web("#FFFFFF"); //(white)
                color2 = Color.web("#006699"); //(deep sky blue)
            }
            case "Futuristic" -> {
                color1 = Color.web("#FFD700"); //(gold)
                color2 = Color.web("#5F9EA0"); //(cadet blue)
            }
        }

        if((i+j)%2==0){
            square.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            square.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private void addPiece(Square square, Piece piece){
        square.getChildren().add(piece);
        square.occupied = true;
    }

    private void addPieces(){
        for(Square square : squares){
            if(square.occupied) continue;
            if(square.y == 1){
                addPiece(square, new Pawn("black", square.x, square.y));
            }
            else if(square.y == 6){
                addPiece(square, new Pawn("white", square.x, square.y));
            }
            else if(square.y == 0){
                if(square.x == 4){
                    addPiece(square, new King("black", square.x, square.y));
                }
                if(square.x == 3){
                    addPiece(square, new Queen("black", square.x, square.y));
                }
                if(square.x == 2 || square.x == 5){
                    addPiece(square, new Bishop("black", square.x, square.y));
                }
                if(square.x == 1 || square.x == 6){
                    addPiece(square, new Knight("black", square.x, square.y));
                }
                if(square.x == 0 || square.x == 7){
                    addPiece(square, new Rook("black", square.x, square.y));
                }
            }
            else if(square.y == 7){
                if(square.x == 4){
                    addPiece(square, new King("white", square.x, square.y));
                }
                if(square.x == 3){
                    addPiece(square, new Queen("white", square.x, square.y));
                }
                if(square.x == 2 || square.x == 5){
                    addPiece(square, new Bishop("white", square.x, square.y));
                }
                if(square.x == 1 || square.x == 6){
                    addPiece(square, new Knight("white", square.x, square.y));
                }
                if(square.x == 0 || square.x == 7){
                    addPiece(square, new Rook("white", square.x, square.y));
                }
            }
        }
    }
}
