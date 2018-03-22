package sample;

import java.util.ArrayList;

public class Queen extends Piece {
    Queen(int rank, int file){
        worth = 9;
        pieceName = "Q";
        currRank = rank;
        currFile = file;
    }

    ArrayList<Square> getLegalMoves(){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move

        return movesList;
    }
}
