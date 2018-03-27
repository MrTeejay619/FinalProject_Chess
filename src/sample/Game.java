package sample;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    public Square[][] board;
    public Player player1;
    public Player player2;;

    Game(Player[] players){
        board = new Square[8][8];
        for (int i =0; i < 8; i++){
            for (int j =0; j < 8; j++){
                Square sqr = new Square();
                if ((i%2 == 0 && j%2==0)||(i%2 != 0 && j%2 != 0)){
                    sqr.color = "Black";
                }else {
                    sqr.color = "White";
                }

                board[i][j] = sqr;
            }
        }
        startBoard(players);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                if (players[0].color == "White"){
                    players[0].myPieces.add(board[j][i].pieceOnMe);
                } else {
                    players[1].myPieces.add(board[j][i].pieceOnMe);
                }

            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 6; j < 8; j++) {
                if (players[0].color == "Black"){
                    players[0].myPieces.add(board[j][i].pieceOnMe);
                } else {
                    players[1].myPieces.add(board[j][i].pieceOnMe);
                }

            }
        }
    }
    // move is not piece based
    // leaving it in the driver class Game
    public void movePiece(Piece piece, int rank, int file){
        //piece.getLegalMoves();
        // check if move is legal
        // each piece type has its own method for determining legal moves
        //if (piece.legalMoves.contains(board[rank][file])){

            board[rank][file].pieceOnMe = piece;
            board[rank][file].isVacant = false;
            board[piece.currRank][piece.currFile].pieceOnMe = null;
            board[piece.currRank][piece.currFile].isVacant = true;
            piece.currRank = rank;
            piece.currFile = file;

        //}
    }
    public void startBoard(Player[] players){
        // randomly assign one player as white
        int rand = new Random().nextInt(2);
        players[rand].color = "White";
        players[rand].myTurn = true;

        // populate board
        // black starts at top of board
        board[0][0].pieceOnMe = new Rook(0,0);
        board[0][1].pieceOnMe = new Knight(0,1);
        board[0][2].pieceOnMe = new Bishop(0,2);
        board[0][3].pieceOnMe = new Queen(0,3);
        board[0][4].pieceOnMe = new King(0,4);
        board[0][5].pieceOnMe = new Bishop(0,5);
        board[0][6].pieceOnMe = new Knight(0,6);
        board[0][7].pieceOnMe = new Rook(0,7);

        //pawns
        for(int i=0;i<8;i++){
            board[1][i].pieceOnMe = new Pawn(1,i);
        }

        // white at bottom
        board[7][0].pieceOnMe = new Rook(7,0);
        board[7][1].pieceOnMe = new Knight(7,1);
        board[7][2].pieceOnMe = new Bishop(7,2);
        board[7][3].pieceOnMe = new Queen(7,3);
        board[7][4].pieceOnMe = new King(7,4);
        board[7][5].pieceOnMe = new Bishop(7,5);
        board[7][6].pieceOnMe = new Knight(7,6);
        board[7][7].pieceOnMe = new Rook(7,7);

        //pawns
        for(int i=0;i<8;i++){
            board[6][i].pieceOnMe = new Pawn(6,i);
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
}
