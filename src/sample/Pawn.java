package sample;

import java.util.ArrayList;

public class Pawn extends Piece{
    Pawn(int rank, int file, String col){
        worth = 0;
        pieceName = "P";
        currRank = rank;
        currFile = file;
        hasMoved = false;
        color = col;
        legalMoves = new ArrayList<>();

    }
    void getLegalMoves(Game game){

        // forward move
        if (this.color.equals("Black")){
            if (currRank + 1 < 8);
            if (game.board[currRank + 1][currFile].isVacant){
                legalMoves.add(game.board[currRank + 1][currFile]);
            }
        } else{
            if (currRank - 1 >= 0);
            if (game.board[currRank - 1][currFile].isVacant){
                legalMoves.add(game.board[currRank - 1][currFile]);
            }
        }

        // double forward move on first move
        if (!hasMoved){
            if (this.color.equals("Black")){
                if (game.board[currRank + 1][currFile].isVacant && game.board[currRank + 2][currFile].isVacant){
                    legalMoves.add(game.board[currRank + 2][currFile]);
                }
            } else {
                if (game.board[currRank - 1][currFile].isVacant && game.board[currRank - 2][currFile].isVacant){
                    legalMoves.add(game.board[currRank - 2][currFile]);
                }
            }

        }
        // squares that piece attacks
        if (this.color.equals("Black")){
            // bottom left
            if(currRank + 1 < 8 && currFile -1 >= 0){
                game.attack(game.board[currRank +1][currFile -1], this.color);
                if (game.board[currRank +1][currFile -1].isVacant
                        || !game.board[currRank +1][currFile -1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank +1][currFile -1]);

                }
            }
            //bottom right
            if(currRank + 1 < 8 && currFile +1 < 7){
                game.attack(game.board[currRank +1][currFile +1], this.color);
                if (game.board[currRank +1][currFile +1].isVacant
                        || !game.board[currRank +1][currFile +1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank +1][currFile +1]);
                }
            }

            // en passent
            // right
            if (currFile + 1 < 8){
                if (game.board[currRank][currFile + 1].pieceOnMe!= null
                        && game.board[currRank][currFile + 1].pieceOnMe.pieceName.equals("P")
                        && game.board[currRank][currFile + 1].pieceOnMe.lastMove == 2){
                    legalMoves.add(game.board[currRank + 1][currFile + 1]);
                }
            }
            // left
            if (currFile - 1 >= 0){
                if (game.board[currRank][currFile - 1].pieceOnMe!= null
                        && game.board[currRank][currFile - 1].pieceOnMe.pieceName.equals("P")
                        && game.board[currRank][currFile - 1].pieceOnMe.lastMove == 2){
                    legalMoves.add(game.board[currRank + 1][currFile - 1]);
                }
            }

        } else {
            // top left
            if(currRank - 1 >= 0 && currFile -1 >= 0){
                game.attack(game.board[currRank - 1][currFile - 1], this.color);
                if (game.board[currRank - 1][currFile - 1].isVacant
                        || !game.board[currRank - 1][currFile - 1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 1][currFile - 1]);

                }
            }
            //top right
            if(currRank - 1 >= 0 && currFile + 1 < 8){
                game.attack(game.board[currRank - 1][currFile +1], this.color);
                if (game.board[currRank - 1][currFile +1].isVacant
                        || !game.board[currRank - 1][currFile +1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 1][currFile +1]);
                }
            }

            // en passent
            // right
            if (currFile + 1 < 8){
                if (game.board[currRank][currFile + 1].pieceOnMe!= null
                        && game.board[currRank][currFile + 1].pieceOnMe.pieceName.equals("P")
                        && game.board[currRank][currFile + 1].pieceOnMe.lastMove == 2){
                    legalMoves.add(game.board[currRank - 1][currFile + 1]);
                }
            }
            // left
            if (currFile - 1 >= 0){
                if (game.board[currRank][currFile - 1].pieceOnMe!= null
                        && game.board[currRank][currFile - 1].pieceOnMe.pieceName.equals("P")
                        && game.board[currRank][currFile - 1].pieceOnMe.lastMove == 2){
                    legalMoves.add(game.board[currRank - 1][currFile - 1]);
                }
            }

        }


    }


    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, game, player);
    }
}
