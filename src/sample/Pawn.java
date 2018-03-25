package sample;

import java.util.ArrayList;

public class Pawn extends Piece{
    boolean hasMoved;
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
            if (game.board[currRank +1][currFile -1].isVacant || game.board[currRank +1][currFile -1].pieceOnMe.color != this.color){
                movesList.add(game.board[currRank +1][currFile -1]);
                game.board[currRank +1][currFile -1].isAttacked = true;
            }
        }
        //top-right
        if(currRank + 1 < 7 && currFile +1 < 7){
            if (game.board[currRank +1][currFile +1].isVacant || game.board[currRank +1][currFile +1].pieceOnMe.color != this.color){
                movesList.add(game.board[currRank +1][currFile +1]);
                game.board[currRank +1][currFile +1].isAttacked = true;
            }
        }

        return movesList;
    }
}
