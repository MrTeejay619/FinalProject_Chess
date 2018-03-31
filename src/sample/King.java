package sample;

import javafx.scene.image.Image;

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
        image = new Image("Images/Pieces" + this.color +"_"+this.pieceName + ".png");
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

        // castle
        // white
        if (color.equals("White")){
            if (!hasMoved && !game.getPlayer(color).inCheck){
                // Queen side
                if (!game.board[7][0].isVacant){
                    // check that the piece is a rook and that it hasn't moved
                    if (game.board[7][0].pieceOnMe.pieceName.equals("R") && !game.board[7][0].pieceOnMe.hasMoved){
                        // check if the two squares the king moves on are attacked
                        if (!game.getOpponentTargets(color).contains(game.board[currRank][currFile - 1])
                                && game.board[currRank][currFile - 1].isVacant
                                && !game.getOpponentTargets(color).contains(game.board[currRank][currFile - 2])
                                && game.board[currRank][currFile - 2].isVacant){
                            legalMoves.add(game.board[currRank][currFile - 2]);
                        }
                    }
                }

                // king side
                if (!game.board[7][7].isVacant){
                    // check that the piece is a rook and that it hasn't moved
                    if (game.board[7][7].pieceOnMe.pieceName.equals("R") && !game.board[7][7].pieceOnMe.hasMoved){
                        // check if the two squares the king moves on are attacked
                        if (!game.getOpponentTargets(color).contains(game.board[currRank][currFile + 1])
                                && game.board[currRank][currFile + 1].isVacant
                                && !game.getOpponentTargets(color).contains(game.board[currRank][currFile + 2])
                                && game.board[currRank][currFile + 2].isVacant){
                            legalMoves.add(game.board[currRank][currFile + 2]);
                        }
                    }
                }

            }
        } else {
            if (!hasMoved && !game.getPlayer(color).inCheck){
                // Queen side
                if (!game.board[0][0].isVacant){
                    // check that the piece is a rook and that it hasn't moved
                    if (game.board[0][0].pieceOnMe.pieceName.equals("R") && !game.board[0][0].pieceOnMe.hasMoved){
                        // check if the two squares the king moves on are attacked
                        if (!game.getOpponentTargets(color).contains(game.board[currRank][currFile - 1])
                                && game.board[currRank][currFile - 1].isVacant
                                && !game.getOpponentTargets(color).contains(game.board[currRank][currFile - 2])
                                && game.board[currRank][currFile - 2].isVacant){
                            legalMoves.add(game.board[currRank][currFile - 2]);
                        }
                    }
                }

                // king side
                if (!game.board[0][7].isVacant){
                    // check that the piece is a rook and that it hasn't moved
                    if (game.board[0][7].pieceOnMe.pieceName.equals("R") && !game.board[0][7].pieceOnMe.hasMoved){
                        // check if the two squares the king moves on are attacked
                        if (!game.getOpponentTargets(color).contains(game.board[currRank][currFile + 1])
                                && game.board[currRank][currFile + 1].isVacant
                                && !game.getOpponentTargets(color).contains(game.board[currRank][currFile + 2])
                                && game.board[currRank][currFile + 2].isVacant){
                            legalMoves.add(game.board[currRank][currFile + 2]);
                        }
                    }
                }

            }
        }



    }

    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, game, player);
    }
}
