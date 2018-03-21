package sample;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    public Square[][] board;
    public Player player1;
    public Player player2;
    public static double timeControl;

    Game(Player[] players, double time){
        timeControl = time;
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
        startBoard(players, time);
    }
    // move is not piece based
    // leaving it in the driver class Game
    public void movePiece(Piece piece, int rank, int file){
        piece.getLegalMoves();
        // check if move is legal
        // each piece type has its own method for determining legal moves
        if (piece.legalMoves.contains(board[rank][file])){
            board[rank][file].pieceOnMe = piece;
            piece.currRank = rank;
            piece.currFile = file;
        }
    }
    public void startBoard(Player[] players, double time){
        // randomly assign one player as white
        players[new Random().nextInt(2)].color = "White";

        // populate board
        // black starts at top of board
        board[0][0].pieceOnMe = new Rook();
        board[0][1].pieceOnMe = new Knight();
        board[0][2].pieceOnMe = new Bishop();
        board[0][3].pieceOnMe = new Queen();
        board[0][4].pieceOnMe = new King();
        board[0][5].pieceOnMe = new Bishop();
        board[0][6].pieceOnMe = new Knight();
        board[0][7].pieceOnMe = new Rook();

        //pawns
        for(int i=0;i<8;i++){
            board[1][i].pieceOnMe = new Pawn();
        }

        // white at bottom
        board[7][0].pieceOnMe = new Rook();
        board[7][1].pieceOnMe = new Knight();
        board[7][2].pieceOnMe = new Bishop();
        board[7][3].pieceOnMe = new Queen();
        board[7][4].pieceOnMe = new King();
        board[7][5].pieceOnMe = new Bishop();
        board[7][6].pieceOnMe = new Knight();
        board[7][7].pieceOnMe = new Rook();

        //pawns
        for(int i=0;i<8;i++){
            board[6][i].pieceOnMe = new Pawn();
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
    }
}
