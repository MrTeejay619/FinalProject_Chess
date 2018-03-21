package sample;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(){
        worth = 1;
        boolean hasMoved = false;
        ArrayList<Square> getLegalMoves = new ArrayList<>();
        pieceName = "P";
    }

    ArrayList<Square> getLegalMoves(){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move

        return movesList;
    }
}
