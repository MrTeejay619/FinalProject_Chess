package sample;

import java.util.ArrayList;

public class Queen extends Piece {
    Queen(int rank, int file){
        worth = 9;
        pieceName = "Q";
        currRank = rank;
        currFile = file;
        hasMoved = false;
    }

    ArrayList<Square> getLegalMoves(Game game){
        // add legal squares to a list of legal moves
        // sets toes squares to attacked

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
                    game.board[currRank + i][currFile].attackedBy = this.color;
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
                    game.board[currRank - i][currFile].attackedBy = this.color;
                    break;
                }
            } else {
                break;
            }

        }
        // diagonal moves
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
                    game.board[currRank + i][currFile - i].attackedBy = this.color;
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
                    game.board[currRank + i][currFile + i].attackedBy = this.color;
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
                    game.board[currRank - i][currFile - i].attackedBy = this.color;
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
                    game.board[currRank - i][currFile + i].attackedBy = this.color;
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
                    game.board[currRank][currFile - i].attackedBy = this.color;
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
                    game.board[currRank][currFile + i].attackedBy = this.color;
                    break;
                }
            } else {
                break;
            }

        }

        return movesList;
    }
}
