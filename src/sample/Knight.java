package sample;

import java.util.ArrayList;

public class Knight extends Piece{
    Knight(int rank, int file){
        worth = 3;
        pieceName = "N";
        currRank = rank;
        currFile = file;
        hasMoved = false;
    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // two left
        if(currFile - 2 > 0){
            // up
            if (currRank + 1 < 8){
                game.board[currRank + 1][currFile - 2].attackedBy = this.color;
                if (game.board[currRank + 1][currFile - 2].isVacant || !game.board[currRank + 1][currFile - 2].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + 1][currFile - 2]);
                }
            }
            // down
            if (currRank - 1 > 0){
                game.board[currRank - 1][currFile - 2].attackedBy = this.color;
                if (game.board[currRank - 1][currFile - 2].isVacant || !game.board[currRank - 1][currFile - 2].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - 1][currFile - 2]);
                }
            }
        }


        // two right
        if(currFile + 2 < 8){
            // up
            if (currRank + 1 < 8){
                game.board[currRank + 1][currFile + 2].attackedBy = this.color;
                if (game.board[currRank + 1][currFile + 2].isVacant || !game.board[currRank + 1][currFile + 2].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + 1][currFile + 2]);
                }
            }
            // down
            if (currRank - 1 > 0){
                game.board[currRank - 1][currFile + 2].attackedBy = this.color;
                if (game.board[currRank - 1][currFile + 2].isVacant || !game.board[currRank - 1][currFile + 2].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - 1][currFile + 2]);
                }
            }
        }

        // two up
        if(currRank + 2 < 8){
            // right
            if (currFile + 1 < 8){
                game.board[currRank + 2][currFile + 1].attackedBy = this.color;
                if (game.board[currRank + 2][currFile + 1].isVacant || !game.board[currRank + 2][currFile + 1].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + 2][currFile + 1]);
                }
            }
            // left
            if (currFile - 1 > 0){
                game.board[currRank + 2][currFile - 1].attackedBy = this.color;
                if (game.board[currRank + 2][currFile - 1].isVacant || !game.board[currRank + 2][currFile - 1].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + 2][currFile - 1]);
                }
            }
        }

        // two down
        if(currRank - 2 > 0){
            // right
            if (currFile + 1 < 8){
                game.board[currRank - 2][currFile + 1].attackedBy = this.color;
                if (game.board[currRank - 2][currFile + 1].isVacant || !game.board[currRank - 2][currFile + 1].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - 2][currFile + 1]);

                }
            }
            // left
            if (currFile - 1 > 0){
                game.board[currRank - 2][currFile - 1].attackedBy = this.color;
                if (game.board[currRank - 2][currFile - 1].isVacant || !game.board[currRank - 2][currFile - 1].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - 2][currFile - 1]);

                }
            }
        }

        return movesList;
    }
}
