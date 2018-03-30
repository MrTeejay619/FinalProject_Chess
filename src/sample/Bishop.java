package sample;

import java.util.ArrayList;

public class Bishop extends Piece {
    Bishop(int rank, int file, String col){
        worth = 3;
        pieceName = "B";
        currRank = rank;
        currFile = file;
        hasMoved = false;
        color = col;
        legalMoves = new ArrayList<>();
    }
    void getLegalMoves(Game game){
        // bottom left
        for (int i = 1; i < 8; i++) {
            if (currRank +i < 8 && currFile -i >= 0){
                if (game.board[currRank + i][currFile - i].isVacant){
                    legalMoves.add(game.board[currRank + i][currFile - i]);
                    game.attack(game.board[currRank + i][currFile - i], this.color);
                } else if (!game.board[currRank + i][currFile - i].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + i][currFile - i]);
                    game.attack(game.board[currRank + i][currFile - i], this.color);
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }
        // bottom right
        for (int i = 1; i < 8; i++) {
            if (currRank + i < 8 && currFile + i < 8) {
                if (game.board[currRank + i][currFile + i].isVacant) {
                    legalMoves.add(game.board[currRank + i][currFile + i]);
                    game.attack(game.board[currRank + i][currFile + i], this.color);
                } else if (!game.board[currRank + i][currFile + i].pieceOnMe.color.equals(this.color)) {
                    legalMoves.add(game.board[currRank + i][currFile + i]);
                    game.attack(game.board[currRank + i][currFile + i], this.color);
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

        // top left
        for (int i = 1; i < 8; i++) {
            if (currRank -i >= 0 && currFile -i >= 0){
                if (game.board[currRank - i][currFile - i].isVacant){
                    legalMoves.add(game.board[currRank - i][currFile - i]);
                    game.attack(game.board[currRank - i][currFile - i], this.color);
                } else if (!game.board[currRank - i][currFile - i].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - i][currFile - i]);
                    game.attack(game.board[currRank - i][currFile - i], this.color);
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

        //  top right
        for (int i = 1; i < 8; i++) {
            if (currRank - i >= 0 && currFile + i <8){
                if (game.board[currRank - i][currFile + i].isVacant){
                    legalMoves.add(game.board[currRank - i][currFile + i]);
                    game.attack(game.board[currRank - i][currFile + i], this.color);
                } else if (!game.board[currRank - i][currFile + i].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - i][currFile + i]);
                    game.attack(game.board[currRank - i][currFile + i], this.color);
                    break;
                } else {
                    break;
                }

            } else {
                break;
            }
        }

    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, game, player);
    }
}
