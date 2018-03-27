package sample;

import java.util.ArrayList;

public class King extends Piece {
    King(int rank, int file){
        worth = 0;
        pieceName = "K";
        currRank = rank;
        currFile = file;
        hasMoved = false;
    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // top three
        if (currRank +1 < 8){
            // L
            if (currFile -1 >0 ){
                game.board[currRank + 1][currFile - 1].attackedBy = this.color;
                if (game.board[currRank + 1][currFile - 1].attackedBy.equals("")
                        && game.board[currRank + 1][currFile - 1].isVacant
                        || !game.board[currRank + 1][currFile - 1].pieceOnMe.color.equals(this.color)){

                    movesList.add(game.board[currRank +1][currFile+1]);
                }
            }

            // M
            game.board[currRank + 1][currFile].attackedBy = this.color;
            if (game.board[currRank + 1][currFile].attackedBy.equals("")
                    && game.board[currRank + 1][currFile].isVacant
                    || !game.board[currRank + 1][currFile].pieceOnMe.color.equals(this.color)){

                movesList.add(game.board[currRank + 1][currFile]);
            }
            // R
            if (currFile + 1 < 8){
                game.board[currRank + 1][currFile + 1].attackedBy = this.color;
                if (game.board[currRank + 1][currFile + 1].attackedBy.equals("")
                        && game.board[currRank + 1][currFile + 1].isVacant
                        || !game.board[currRank + 1][currFile + 1].pieceOnMe.color.equals(this.color)){

                    movesList.add(game.board[currRank + 1][currFile + 1]);
                }
            }
        }
        // bottom three
        if (currRank - 1 > 0){
            // L
            if (currFile - 1 > 0 ){
                game.board[currRank - 1][currFile - 1].attackedBy = this.color;
                if (game.board[currRank - 1][currFile - 1].attackedBy.equals("")
                        && game.board[currRank - 1][currFile - 1].isVacant
                        || !game.board[currRank - 1][currFile - 1].pieceOnMe.color.equals(this.color)){

                    movesList.add(game.board[currRank - 1][currFile + 1]);
                }
            }

            // M
            game.board[currRank - 1][currFile].attackedBy = this.color;
            if (game.board[currRank - 1][currFile].attackedBy.equals("")
                    && game.board[currRank - 1][currFile].isVacant
                    || !game.board[currRank - 1][currFile].pieceOnMe.color.equals(this.color)){

                movesList.add(game.board[currRank + 1][currFile]);
            }
            // R
            if (currFile + 1 < 8){
                game.board[currRank - 1][currFile + 1].attackedBy = this.color;
                if (game.board[currRank - 1][currFile + 1].attackedBy.equals("")
                        && game.board[currRank - 1][currFile + 1].isVacant
                        || !game.board[currRank - 1][currFile + 1].pieceOnMe.color.equals(this.color)){

                    movesList.add(game.board[currRank - 1][currFile + 1]);
                }
            }
        }



        return movesList;
    }
}
