package sample;

import java.util.ArrayList;

public class King extends Piece {
    King(int rank, int file){
        worth = 0;
        pieceName = "K";
        currRank = rank;
        currFile = file;
    }
    ArrayList<Square> getLegalMoves(){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move


        return movesList;
    }
}
