package sample;

import java.util.ArrayList;

public class King extends Piece implements Pieces {
    King(int rank, int file, String col){
        worth = 0;
        pieceName = "K";
        currRank = rank;
        currFile = file;
        hasMoved = false;
        color = col;
        legalMoves = new ArrayList<>();
    }
    void getLegalMoves(Game game){
        // top three
        if (currRank + 1 < 8){
            // L
            if (currFile - 1 >= 0 ){
                game.attack(game.board[currRank + 1][currFile - 1], this.color);
                if (game.board[currRank + 1][currFile - 1].isVacant
                        || !game.board[currRank + 1][currFile - 1].pieceOnMe.color.equals(this.color)){

                    legalMoves.add(game.board[currRank +1][currFile+1]);
                }
            }

            // M
            game.attack(game.board[currRank + 1][currFile], this.color);
            if (game.board[currRank + 1][currFile].isVacant
                    || !game.board[currRank + 1][currFile].pieceOnMe.color.equals(this.color)){

                legalMoves.add(game.board[currRank + 1][currFile]);
            }
            // R
            if (currFile + 1 < 8){
                game.attack(game.board[currRank + 1][currFile + 1], this.color);
                if (game.board[currRank + 1][currFile + 1].isVacant
                        || !game.board[currRank + 1][currFile + 1].pieceOnMe.color.equals(this.color)){

                    legalMoves.add(game.board[currRank + 1][currFile + 1]);
                }
            }
        }
        // bottom three
        if (currRank - 1 >= 0){
            // L
            if (currFile - 1 >= 0 ){
                game.attack(game.board[currRank - 1][currFile - 1], this.color);
                if (game.board[currRank - 1][currFile - 1].isVacant
                        || !game.board[currRank - 1][currFile - 1].pieceOnMe.color.equals(this.color)){

                    legalMoves.add(game.board[currRank - 1][currFile + 1]);
                }
            }

            // M
            game.attack(game.board[currRank - 1][currFile], this.color);
            if (game.board[currRank - 1][currFile].isVacant
                    || !game.board[currRank - 1][currFile].pieceOnMe.color.equals(this.color)){

                legalMoves.add(game.board[currRank - 1][currFile]);
            }
            // R
            if (currFile + 1 < 8){
                game.attack(game.board[currRank - 1][currFile + 1], this.color);
                if (game.board[currRank - 1][currFile + 1].isVacant
                        || !game.board[currRank - 1][currFile + 1].pieceOnMe.color.equals(this.color)){

                    legalMoves.add(game.board[currRank - 1][currFile + 1]);
                }
            }
        }

        // left
        if (currFile - 1 >= 0){
            game.attack(game.board[currRank][currFile - 1], this.color);
            if (game.board[currRank][currFile - 1].isVacant ||
                    !game.board[currRank][currFile - 1].pieceOnMe.color.equals(this.color)) {
                legalMoves.add(game.board[currRank][currFile - 1]);

            }
        }
        // right
        if (currFile + 1 < 8){
            game.attack(game.board[currRank][currFile + 1], this.color);
            if (game.board[currRank][currFile + 1].isVacant ||
                    !game.board[currRank][currFile + 1].pieceOnMe.color.equals(this.color)) {
                legalMoves.add(game.board[currRank][currFile + 1]);

            }
        }

    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, game, player);
    }
}
