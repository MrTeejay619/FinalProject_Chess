package sample;

import java.util.ArrayList;

abstract class Piece {

    String pieceName;
    String color;
    int currRank;
    int currFile;

    // array of legal squares to move to
    ArrayList<Square> legalMoves;
    int worth;
    abstract ArrayList<Square> getLegalMoves(Game game);


}
