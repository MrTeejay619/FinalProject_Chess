package sample;

import java.util.ArrayList;

abstract class Piece {

    String pieceName;
    // white or black
    String color;

    // position
    int currRank;
    int currFile;

    // array of legal squares to move to
    ArrayList<Square> legalMoves;

    //

    int worth;
    abstract ArrayList<Square> getLegalMoves();

}
