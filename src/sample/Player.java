package sample;

public class Player {
    String color;
    float timer;
    Piece[] piecesCaptured;
    boolean myTurn;
    Player(){
        myTurn = false;
        // starts as black, on game start one is randomly assigned white
        color = "Black";
    }
}
