package com.example.chessgame;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Game {

    public static Piece currentPiece;
    public static String currentPlayer;
    public static Board chessBoard;
    private boolean game;

    public Game(GridPane board, String theme){
        chessBoard = new Board(board, theme);
        currentPiece = null;
        currentPlayer = "white";
        this.game = true;
        addEventHandlers(chessBoard.board);
    }

    private void addEventHandlers(GridPane board){
        board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                EventTarget target = mouseEvent.getTarget();

                // Clicked on square
                if(target.toString().equals("Square")){
                    Square square = (Square) target;
                    if(square.occupied){
                        Piece newPiece = (Piece) square.getChildren().get(0);
                        // Selecting a new piece
                        if(currentPiece == null){
                            currentPiece = newPiece;
                            if(!currentPiece.getColor().equals(currentPlayer)){
                                currentPiece = null;
                                return;
                            }
                            selectPiece(game);
                        }else{ // Selecting other piece of same color || Killing a piece
                            if(currentPiece.color.equals(newPiece.color)){
                                deselectPiece(false);
                                currentPiece = newPiece;
                                selectPiece(game);
                            }else{
                                killPiece(square);
                            }
                        }
                    }else{ // Dropping a piece on blank square
                        dropPiece(square);
                    }
                }else{ // Clicked on piece
                    Piece newPiece = (Piece) target;
                    Square square = (Square) newPiece.getParent();
                    // Selecting a new piece
                    if(currentPiece == null){
                        currentPiece = newPiece;
                        if(!currentPiece.getColor().equals(currentPlayer)){
                            currentPiece = null;
                            return;
                        }
                        selectPiece(game);
                    }else{ // Selecting other piece of same color || Killing a piece
                        if(currentPiece.color.equals(newPiece.color)){
                            deselectPiece(false);
                            currentPiece = newPiece;
                            selectPiece(game);
                        }else{
                            killPiece(square);
                        }
                    }
                }
            }
        });
    }

    private void selectPiece(boolean game){
        if(!game){
            currentPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPiece.setEffect(borderGlow);
        currentPiece.getAllPossibleMoves();
        currentPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer){
        currentPiece.setEffect(null);
        currentPiece.showAllPossibleMoves(false);
        currentPiece = null;
        if(changePlayer) currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private void dropPiece(Square square){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        deselectPiece(true);
    }

    private void killPiece(Square square){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Piece killedPiece = (Piece) square.getChildren().get(0);
        if(killedPiece.type.equals("King")) this.game = false;

        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().remove(0);
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        deselectPiece(true);
    }
}
