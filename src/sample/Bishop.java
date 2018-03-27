package sample;

import java.util.ArrayList;

public class Bishop extends Piece {
    Bishop(int rank, int file){
        worth = 3;
        pieceName = "B";
        currRank = rank;
        currFile = file;
        hasMoved = false;
    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // forward-left
        for (int i = 1; i < 8; i++) {
            if (currRank +i < 8 && currFile -i > 0){
                if (game.board[currRank + i][currFile - i].isVacant){
                    movesList.add(game.board[currRank + i][currFile - i]);
                    game.board[currRank + i][currFile - i].attackedBy = this.color;
                } else if (!game.board[currRank + i][currFile - i].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + i][currFile - i]);
                    game.board[currRank + i][currFile - i].attackedBy = this.color;
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }
        // forward-right
        for (int i = 1; i < 8; i++) {
            if (currRank + i < 8 && currFile + i < 8) {
                if (game.board[currRank + i][currFile + i].isVacant) {
                    movesList.add(game.board[currRank + i][currFile + i]);
                    game.board[currRank + i][currFile + i].attackedBy = this.color;
                } else if (!game.board[currRank + i][currFile + i].pieceOnMe.color.equals(this.color)) {
                    movesList.add(game.board[currRank + i][currFile + i]);
                    game.board[currRank + i][currFile + i].attackedBy = this.color;
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

        // back-left
        for (int i = 1; i < 8; i++) {
            if (currRank -i > 0 && currFile -i > 0){
                if (game.board[currRank - i][currFile - i].isVacant){
                    movesList.add(game.board[currRank - i][currFile - i]);
                    game.board[currRank - i][currFile - i].attackedBy = this.color;
                } else if (!game.board[currRank - i][currFile - i].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - i][currFile - i]);
                    game.board[currRank - i][currFile - i].attackedBy = this.color;
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

        // back-right
        for (int i = 1; i < 8; i++) {
            if (currRank -i > 0 && currFile -i > 0){
                if (game.board[currRank - i][currFile + i].isVacant){
                    movesList.add(game.board[currRank - i][currFile + i]);
                    game.board[currRank - i][currFile + i].attackedBy = this.color;
                } else if (!game.board[currRank - i][currFile + i].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - i][currFile + i]);
                    game.board[currRank - i][currFile + i].attackedBy = this.color;
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

        return movesList;
    }
}
