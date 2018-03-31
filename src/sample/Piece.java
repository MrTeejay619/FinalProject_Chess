package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

abstract class Piece implements Pieces {

    int lastMove;
    String pieceName;
    String color;
    int currRank;
    int currFile;
    boolean hasMoved;
    Image image;
    ImageView iV;


    // array of legal squares to move to
    ArrayList<Square> legalMoves;
    int worth;
    abstract void getLegalMoves(Game game);


}
