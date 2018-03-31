package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Rook extends Piece {
    boolean hasMoved;
    Rook(int rank, int file, String col){
        worth = 5;
        pieceName = "R";
        currRank = rank;
        currFile = file;
        hasMoved = false;
        color = col;
        legalMoves = new ArrayList<>();
        image = new Image("Images/Pieces" + this.color +"_"+this.pieceName + ".png");

    }
    void getLegalMoves(Game game){
        // vertical move
        // straight
        for (int i = 0; i < 8; i++) {
            if(currRank + i < 8){
                if (game.board[currRank + i][currFile].isVacant){
                    legalMoves.add(game.board[currRank + i][currFile]);
                    game.attack(game.board[currRank + i][currFile], this.color);
                } else if(!game.board[currRank + i][currFile].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank + i][currFile]);
                    game.attack(game.board[currRank + i][currFile], this.color);
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
            if(currRank - i >= 0){
                if (game.board[currRank - i][currFile].isVacant){
                    legalMoves.add(game.board[currRank - i][currFile]);
                    game.attack(game.board[currRank - i][currFile], this.color);
                } else if(!game.board[currRank - i][currFile].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank - i][currFile]);
                    game.attack(game.board[currRank - i][currFile], this.color);
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
            if(currFile - i >= 0){
                if (game.board[currRank][currFile - i].isVacant){
                    legalMoves.add(game.board[currRank][currFile - i]);
                    game.attack(game.board[currRank][currFile - i], this.color);
                } else if(!game.board[currRank][currFile - i].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank][currFile - i]);
                    game.attack(game.board[currRank][currFile - i], this.color);
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
                    legalMoves.add(game.board[currRank][currFile + i]);
                    game.attack(game.board[currRank][currFile + i], this.color);
                } else if(!game.board[currRank][currFile + i].pieceOnMe.color.equals(this.color)){
                    legalMoves.add(game.board[currRank][currFile + i]);
                    game.attack(game.board[currRank][currFile + i], this.color);
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
