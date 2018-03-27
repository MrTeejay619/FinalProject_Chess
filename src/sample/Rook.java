package sample;

import java.util.ArrayList;

public class Rook extends Piece {
    boolean hasMoved;
    Rook(int rank, int file){
        worth = 5;
        pieceName = "R";
        currRank = rank;
        currFile = file;
        hasMoved = false;

    }
    ArrayList<Square> getLegalMoves(Game game){
        ArrayList<Square> movesList = new ArrayList<>();
        // vertical move
        // straight
        for (int i = 0; i < 8; i++) {
            if(currRank + i < 8){
                if (game.board[currRank + i][currFile].isVacant){
                    movesList.add(game.board[currRank + i][currFile]);
                    game.board[currRank + i][currFile].attackedBy = this.color;
                } else if(!game.board[currRank + i][currFile].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank + i][currFile]);
                    game.board[currRank + i][currFile].attackedBy = this.color;
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }

        }
        // back
        for (int i = 0; i < 8; i++) {
            if(currRank - i > 0){
                if (game.board[currRank - i][currFile].isVacant){
                    movesList.add(game.board[currRank - i][currFile]);
                    game.board[currRank - i][currFile].attackedBy = this.color;
                } else if(!game.board[currRank - i][currFile].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank - i][currFile]);
                    game.board[currRank - i][currFile].attackedBy = this.color;
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }

        }
        // horizontal move
        // left
        for (int i = 0; i < 8; i++) {
            if(currFile - i > 0){
                if (game.board[currRank][currFile - i].isVacant){
                    movesList.add(game.board[currRank][currFile - i]);
                    game.board[currRank][currFile - i].attackedBy = this.color;
                } else if(!game.board[currRank][currFile - i].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank][currFile - i]);
                    game.board[currRank][currFile - i].attackedBy = this.color;
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }

        }
        // right
        for (int i = 0; i < 8; i++) {
            if(currFile + i < 8){
                if (game.board[currRank][currFile + i].isVacant){
                    movesList.add(game.board[currRank][currFile - i]);
                    game.board[currRank][currFile + i].attackedBy = this.color;
                } else if(!game.board[currRank][currFile + i].pieceOnMe.color.equals(this.color)){
                    movesList.add(game.board[currRank][currFile + i]);
                    game.board[currRank][currFile + i].attackedBy = this.color;
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
