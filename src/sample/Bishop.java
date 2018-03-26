package sample;

import java.util.ArrayList;

public class Bishop extends Piece {
    Bishop(int rank, int file){
        worth = 3;
        pieceName = "B";
        currRank = rank;
        currFile = file;
    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move

        return movesList;
    }
}
