package sample;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int rank, int file){
        worth = 0;
        pieceName = "P";
        currRank = rank;
        currFile = file;
    }
    ArrayList<Square> getLegalMoves(){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move

        return movesList;
    }
}
