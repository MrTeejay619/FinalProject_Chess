package sample;

import java.util.ArrayList;

public class Game {
    Square[][] board;
    Player player1;
    Player player2;

    Game(){
        board = new Square[8][8];
        for (int i =1; i < 8; i++){
            for (int j =1; j < 8; j++){
                Square sqr = new Square();
                if ((i%2 == 0 && j%2==0)||(i%2 != 0 && j%2 != 0)){
                    sqr.color = "Black";
                }else {
                    sqr.color = "White";
                }

                board[i][j] = sqr;
                System.out.print(sqr.color);
            }
            System.out.print('\n');
        }
    }
}
