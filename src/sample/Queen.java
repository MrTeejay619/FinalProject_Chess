package sample;

import java.util.ArrayList;

public class Queen extends Piece {
    Queen(int rank, int file){
        worth = 9;
        pieceName = "Q";
        currRank = rank;
        currFile = file;
    }

    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward move
        for (int i = currRank + 1; i < 7; i++) {
            if (game.board[i][currFile].isVacant){
                movesList.add(game.board[i][currFile]);
                game.board[i][currFile].isAttacked = true;

            } else if(game.board[i][currFile].pieceOnMe.color != this.color){
                movesList.add(game.board[i][currFile]);
                game.board[i][currFile].isAttacked = true;
                break;
            } else {
                break;
            }
        }

        // move backwards
        for (int i = currRank - 1; i > 0; i--) {
            if (game.board[i][currFile].isVacant){
                movesList.add(game.board[i][currFile]);
                game.board[i][currFile].isAttacked = true;

            } else if(game.board[i][currFile].pieceOnMe.color != this.color){
                movesList.add(game.board[i][currFile]);
                game.board[i][currFile].isAttacked = true;
                break;
            } else {
                break;
            }
        }

        return movesList;
    }
}
