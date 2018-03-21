package sample;

abstract class Piece {

    String pieceName;
    // white or black
    String color;

    // position
    int currRank;
    int currFile;

    // array of legal squares to move to
    Square[] legalMoves;

    //

    int worth;

    abstract void move(int rank, int file);
    abstract Square[] getLegalMoves();

}
