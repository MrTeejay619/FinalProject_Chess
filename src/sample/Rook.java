package sample;

import java.util.ArrayList;

public class Rook extends Piece {
    Rook(int rank, int file){
        worth = 5;
        pieceName = "R";
        currRank = rank;
        currFile = file;

    }
    ArrayList<Square> getLegalMoves(){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move

        return movesList;
    }
}
