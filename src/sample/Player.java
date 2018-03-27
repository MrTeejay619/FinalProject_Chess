package sample;

import java.util.ArrayList;

public class Player {
    String color;
    float timer;
    ArrayList<Piece> piecesCaptured;
    ArrayList<Piece> myPieces;
    boolean myTurn;
    Player(){
        myTurn = false;
        // starts as black, on game start one is randomly assigned white
        color = "Black";
        piecesCaptured = new ArrayList<>();
        myPieces = new ArrayList<>();

    }

}
