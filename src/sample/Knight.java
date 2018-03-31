package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Knight extends Piece{
    Knight(int rank, int file, String col){
        worth = 3;
        pieceName = "N";
        currRank = rank;
        currFile = file;
        hasMoved = false;
        color = col;
        legalMoves = new ArrayList<>();
        image = new Image("sample/Images/Pieces/" + this.color +"_"+this.pieceName + ".png");
    }
    void getLegalMoves(Game game){
        // two left
        if(currFile - 2 >= 0){
            // up
            if (currRank + 1 < 8){
                game.attack(game.board[currRank + 1][currFile - 2], this.color);
                if (game.board[currRank + 1][currFile - 2].isVacant || !game.board[currRank + 1][currFile - 2].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + 1][currFile - 2]);
                }
            }
            // down
            if (currRank - 1 >= 0){
                game.attack(game.board[currRank - 1][currFile - 2], this.color);
                if (game.board[currRank - 1][currFile - 2].isVacant || !game.board[currRank - 1][currFile - 2].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 1][currFile - 2]);
                }
            }
        }


        // two right
        if(currFile + 2 < 8){
            // up
            if (currRank + 1 < 8){
                game.attack(game.board[currRank + 1][currFile + 2], this.color);
                if (game.board[currRank + 1][currFile + 2].isVacant || !game.board[currRank + 1][currFile + 2].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + 1][currFile + 2]);
                }
            }
            // down
            if (currRank - 1 >= 0){
                game.attack(game.board[currRank - 1][currFile + 2], this.color);
                if (game.board[currRank - 1][currFile + 2].isVacant || !game.board[currRank - 1][currFile + 2].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 1][currFile + 2]);
                }
            }
        }

        // two up
        if(currRank + 2 < 8){
            // right
            if (currFile + 1 < 8){
                game.attack(game.board[currRank + 2][currFile + 1], this.color);
                if (game.board[currRank + 2][currFile + 1].isVacant || !game.board[currRank + 2][currFile + 1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + 2][currFile + 1]);
                }
            }
            // left
            if (currFile - 1 >= 0){
                game.attack(game.board[currRank + 2][currFile - 1], this.color);
                if (game.board[currRank + 2][currFile - 1].isVacant || !game.board[currRank + 2][currFile - 1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + 2][currFile - 1]);
                }
            }
        }

        // two down
        if(currRank - 2 >= 0){
            // right
            if (currFile + 1 < 8){
                game.attack(game.board[currRank - 2][currFile + 1], this.color);
                if (game.board[currRank - 2][currFile + 1].isVacant || !game.board[currRank - 2][currFile + 1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 2][currFile + 1]);

                }
            }
            // left
            if (currFile - 1 >= 0){
                game.attack(game.board[currRank - 2][currFile - 1], this.color);
                if (game.board[currRank - 2][currFile - 1].isVacant || !game.board[currRank - 2][currFile - 1].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - 2][currFile - 1]);

                }
            }
        }

    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, game, player);
    }
}
