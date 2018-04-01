package sample;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Game implements Pieces{
    public Square[][] board;
    public Player[] players;

    ArrayList<Square> attackedByBlack;
    ArrayList<Square> attackedByWhite;

    Game(Player[] players){
        this.players = players;
        attackedByBlack = new ArrayList<>();
        attackedByWhite = new ArrayList<>();
        board = new Square[8][8];
        for (int i =0; i < 8; i++){
            for (int j =0; j < 8; j++){
                Square sqr = new Square(i, j);

                if ((i%2 == 0 && j%2==0)||(i%2 != 0 && j%2 != 0)){
                    sqr.color = "White";
                }else {
                    sqr.color = "Black";
                }

                board[i][j] = sqr;
            }
        }
        startBoard(this.players);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                if (players[0].color == "Black"){
                    players[0].myPieces.add(board[j][i].pieceOnMe);
                } else {
                    players[1].myPieces.add(board[j][i].pieceOnMe);
                }

            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 6; j < 8; j++) {
                if (players[0].color == "White"){
                    players[0].myPieces.add(board[j][i].pieceOnMe);
                } else {
                    players[1].myPieces.add(board[j][i].pieceOnMe);
                }

            }
        }
    }
    // move is not piece based
    // leaving it in the driver class Game
    public int movePiece(Player player, Piece piece, int rank, int file){
        // check that the square clicked has a piece
        if  (piece != null){
            // check that it is your turn
            if (player.myTurn){
                if (player.color.equals(piece.color)){
                    if (piece.legalMoves.contains(board[rank][file])){
                        // check if castle move
                        if (piece.pieceName.equals("K") && abs(file - piece.currFile) > 1){
                            castle(piece, file);
                        } else {
                            // make move
                            Piece taken;
                            // check if en passent
                            if (piece.pieceName.equals("P") && file != piece.currFile && board[rank][file].isVacant){
                                if (piece.color.equals("Black")){
                                    taken = board[rank - 1][file].pieceOnMe;
                                } else {
                                    taken = board[rank + 1][file].pieceOnMe;
                                }
                            } else {
                                taken = board[rank][file].pieceOnMe;
                            }
                            board[rank][file].pieceOnMe = piece;
                            board[rank][file].isVacant = false;
                            board[piece.currRank][piece.currFile].pieceOnMe = null;
                            board[piece.currRank][piece.currFile].isVacant = true;
                            int oldRank = piece.currRank;
                            int oldFile = piece.currFile;
                            piece.currRank = rank;
                            piece.currFile = file;
                            // check if king is in check after move
                            this.accept(new ChessBoardMoveVisitor(), this, getOpponent(piece.color));

                            if (player.inCheck){
                                // revert to previous position
                                piece.currRank = oldRank;
                                piece.currFile = oldFile;

                                board[piece.currRank][piece.currFile].pieceOnMe = piece;
                                board[piece.currRank][piece.currFile].isVacant = false;

                                if (taken != null){
                                    board[taken.currRank][taken.currFile].pieceOnMe = taken;
                                } else {
                                    board[rank][file].pieceOnMe = null;
                                    board[rank][file].isVacant = true;
                                }

                                // TODO alert
                                System.out.println("Not Legal Move");
                                return 0;
                            } else {
                                piece.hasMoved = true;
                                piece.lastMove = abs(oldRank - piece.currRank);
                                if (taken !=  null) {
                                    player.piecesCaptured.add(taken);
                                    this.getOpponent(piece.color).myPieces.remove(taken);
                                }

                                // check for promotion
                                if (piece.pieceName.equals("P")){
                                    if (rank == 0 && piece.color.equals("White")){
                                        promote(piece);
                                    } else if (rank == 7 && piece.color.equals("Black")){
                                        promote(piece);
                                    }
                                }

                                this.accept(new ChessBoardMoveVisitor(), this, player);

                                // not my turn anymore
                                player.myTurn = false;
                                // other guy's turn
                                this.getOpponent(piece.color).myTurn = true;

                                // TODO update server side board
                                return 1;
                            }
                            // TODO re-render board()
                        }


                    } else {
                        // TODO alert
                        System.out.println("Not Legal Move");
                        return 2;
                    }
                } else {
                    // TODO alert
                    System.out.println("not your color");
                    return 3;
                }

            } else {
                // TODO alert
                System.out.println("not " + player.name + "'s turn");
                return 4;
            }


        } else {
            // TODO alert
            System.out.println("no piece there");
            return 5;
        }
        return 6;
    }


    public void startBoard(Player[] players){
        // randomly assign one player as white
        int rand = new Random().nextInt(2);
        players[rand].color = "White";
        players[rand].myTurn = true;

        // populate board
        // black starts at top of board
        board[0][0].pieceOnMe = new Rook(0,0,"Black");
        board[0][1].pieceOnMe = new Knight(0,1, "Black");
        board[0][2].pieceOnMe = new Bishop(0,2, "Black");
        board[0][3].pieceOnMe = new Queen(0,3, "Black");
        board[0][4].pieceOnMe = new King(0,4, "Black");
        board[0][5].pieceOnMe = new Bishop(0,5, "Black");
        board[0][6].pieceOnMe = new Knight(0,6, "Black");
        board[0][7].pieceOnMe = new Rook(0,7, "Black");

        board[0][0].isVacant = false;
        board[0][1].isVacant = false;
        board[0][2].isVacant = false;
        board[0][3].isVacant = false;
        board[0][4].isVacant = false;
        board[0][5].isVacant = false;
        board[0][6].isVacant = false;
        board[0][7].isVacant = false;

        //pawns
        for(int i=0;i<8;i++){
            board[1][i].pieceOnMe = new Pawn(1,i, "Black");
            board[1][i].isVacant = false;
        }

        // white at bottom
        board[7][0].pieceOnMe = new Rook(7,0, "White");
        board[7][1].pieceOnMe = new Knight(7,1, "White");
        board[7][2].pieceOnMe = new Bishop(7,2, "White");
        board[7][3].pieceOnMe = new Queen(7,3, "White");
        board[7][4].pieceOnMe = new King(7,4, "White");
        board[7][5].pieceOnMe = new Bishop(7,5, "White");
        board[7][6].pieceOnMe = new Knight(7,6, "White");
        board[7][7].pieceOnMe = new Rook(7,7, "White");

        board[7][0].isVacant = false;
        board[7][1].isVacant = false;
        board[7][2].isVacant = false;
        board[7][3].isVacant = false;
        board[7][4].isVacant = false;
        board[7][5].isVacant = false;
        board[7][6].isVacant = false;
        board[7][7].isVacant = false;

        //pawns
        for(int i=0;i<8;i++){
            board[6][i].pieceOnMe = new Pawn(6,i, "White");
            board[6][i].isVacant = false;
        }

        for (Player p : players){
            if (p.color.equals("White")){
                p.myKing = board[7][4].pieceOnMe;
            } else {
                p.myKing = board[0][4].pieceOnMe;
            }
        }

    }

    void printBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].pieceOnMe != null){
                    System.out.print(board[i][j].pieceOnMe.pieceName + "\t");
                } else{
                    System.out.print("O\t");
                }

            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public void attack(Square square, String attacker){
        if (attacker.equals("White")){
            if (!attackedByWhite.contains(square)){
                attackedByWhite.add(square);
            }
        } else {
            if (!attackedByBlack.contains(square)){
                attackedByBlack.add(square);
            }
        }
    }

    public ArrayList<Square> getOpponentTargets(String myColor){
        if (myColor.equals("White")){
            return attackedByBlack;
        } else {
            return attackedByWhite;
        }
    }

    Player getOpponent(String color){
        if (players[0].color.equals(color)){
            return players[1];
        } else {
            return players[0];
        }
    }

    Player getPlayer(String color){
        if (players[0].color.equals(color)){
            return players[0];
        } else {
            return players[1];
        }
    }


    @Override
    public void accept(PieceVisitor pieceVisitor, Game game, Player player){
        pieceVisitor.visit(this, player);

        player.accept(pieceVisitor, game, player);
    }

    public boolean checkTempMove(Player player, Piece piece, int rank, int file){
        // make move
        boolean check = true;
        Piece taken;
        // check if en passent
        if (piece.pieceName.equals("P") && file != piece.currFile && board[rank][file].isVacant){
            if (piece.color.equals("Black")){
                taken = board[rank - 1][file].pieceOnMe;
            } else {
                taken = board[rank + 1][file].pieceOnMe;
            }
        } else {
            taken = board[rank][file].pieceOnMe;
        }
        board[rank][file].pieceOnMe = piece;
        board[rank][file].isVacant = false;
        board[piece.currRank][piece.currFile].pieceOnMe = null;
        board[piece.currRank][piece.currFile].isVacant = true;
        int oldRank = piece.currRank;
        int oldFile = piece.currFile;
        piece.currRank = rank;
        piece.currFile = file;
        // check if king is in check after move
        this.accept(new ChessBoardMoveVisitor(), this, getOpponent(piece.color));

        if (player.inCheck){
            check = false;
        }
        // revert to previous position
        piece.currRank = oldRank;
        piece.currFile = oldFile;
        board[piece.currRank][piece.currFile].pieceOnMe = piece;
        board[piece.currRank][piece.currFile].isVacant = false;

        if (taken != null){
            board[taken.currRank][taken.currFile].pieceOnMe = taken;
        } else {
            board[rank][file].pieceOnMe = null;
            board[rank][file].isVacant = true;
        }

        return check;
    }

    public void castle(Piece piece, int file){
        if (file < piece.currFile){
            // Queen side
            board[piece.currRank][2].pieceOnMe = piece;
            board[piece.currRank][3].pieceOnMe = board[piece.currRank][0].pieceOnMe;
            board[piece.currRank][3].pieceOnMe.currFile = 3;
            board[piece.currRank][2].pieceOnMe.currFile = 2;
            board[piece.currRank][3].pieceOnMe.hasMoved = true;
            board[piece.currRank][2].pieceOnMe.hasMoved = true;

            board[piece.currRank][0].pieceOnMe = null;
            board[piece.currRank][4].pieceOnMe = null;
        } else {
            // King side
            board[piece.currRank][6].pieceOnMe = piece;
            board[piece.currRank][5].pieceOnMe = board[piece.currRank][7].pieceOnMe;
            board[piece.currRank][5].pieceOnMe.currFile = 5;
            board[piece.currRank][6].pieceOnMe.currFile = 6;
            board[piece.currRank][5].pieceOnMe.hasMoved = true;
            board[piece.currRank][6].pieceOnMe.hasMoved = true;

            board[piece.currRank][7].pieceOnMe = null;
            board[piece.currRank][4].pieceOnMe = null;
        }
    }

    public void promote(Piece piece){
        // TODO implement pop up that returns string ("Q", "B", "N", "R")
        // selected = popUpFunction();
        // switch(selected) {
        //     case "N":
        //         Piece temp = new Knight(piece.currRank, piece.currFile, piece.color);
        //         break;
        //     case "B":
        //         Piece temp = new Bishop(piece.currRank, piece.currFile, piece.color);
        //         break;
        //     case "R":
        //         Piece temp = new Rook(piece.currRank, piece.currFile, piece.color);
        //         break;
        //     case "Q":
        //         Piece temp = new Queen(piece.currRank, piece.currFile, piece.color);
        //         break;

        // for now just queen
        Piece temp = new Queen(piece.currRank, piece.currFile, piece.color);
        // add to players piece list
        getPlayer(piece.color).myPieces.add(temp);
        // add to board
        board[piece.currRank][piece.currFile].pieceOnMe = temp;
        // remove pawn from player piece list
        getPlayer(piece.color).myPieces.remove(piece);

    }
}
