package sample;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int rank, int file){
        worth = 0;
        pieceName = "P";
        currRank = rank;
        currFile = file;
        hasMoved = false;
    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move
        if (currRank + 1 < 7);
        if (game.board[currRank + 1][currFile].isVacant){
            movesList.add(game.board[currRank + 1][currFile]);
        }
        // double forward move on first move
        if (!hasMoved){
            if (game.board[currRank + 1][currFile].isVacant && game.board[currRank + 2][currFile].isVacant){
                movesList.add(game.board[currRank + 2][currFile]);
            }
        }
        // squares that piece attacks
        // top-left
        if(currRank + 1 < 7 && currFile -1 > 0){
            game.board[currRank +1][currFile -1].attackedBy = this.color;
            if (game.board[currRank +1][currFile -1].isVacant || !game.board[currRank +1][currFile -1].pieceOnMe.color.equals(this.color)){
                movesList.add(game.board[currRank +1][currFile -1]);

            }
        }
        //top-right
        if(currRank + 1 < 7 && currFile +1 < 7){
            game.board[currRank +1][currFile +1].attackedBy = this.color;
            if (game.board[currRank +1][currFile +1].isVacant || !game.board[currRank +1][currFile +1].pieceOnMe.color.equals(this.color)){
                movesList.add(game.board[currRank +1][currFile +1]);
            }
        }

        return movesList;
    }
}
