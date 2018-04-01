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
    Player(String colour){
        this.name = name;
        myTurn = false;
        // starts as black, on game start one is randomly assigned white
        color = colour;
        piecesCaptured = new ArrayList<>();
        myPieces = new ArrayList<>();
        inCheck = false;

    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        for (Piece p : myPieces){
            if (p.pieceName.equals("K")){
                p.accept(pieceVisitor, game, player);
            }
            // king is checked last
            myKing.accept(pieceVisitor, game, player);
        }

    }



}
