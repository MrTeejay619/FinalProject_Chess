package sample;

import java.util.ArrayList;

public class Player implements Pieces{
    String name;
    String color;
    ArrayList<Piece> piecesCaptured;
    ArrayList<Piece> myPieces;
    boolean myTurn;
    boolean inCheck;
    boolean inMate;
    Piece myKing;
    Player(String name){
        this.name = name;
        myTurn = false;
        // starts as black, on game start one is randomly assigned white
        color = "Black";
        piecesCaptured = new ArrayList<>();
        myPieces = new ArrayList<>();
        inCheck = false;

    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        for (Piece p : myPieces){
            p.accept(pieceVisitor, game, player);
        }

    }



}
