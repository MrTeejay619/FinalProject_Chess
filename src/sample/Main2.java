package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));



        GridPane gp = new GridPane();
        Player[] p = new Player[2];
        p[0] = new Player();
        p[1] = new Player();


        Game game = new Game(p);
        game.printBoard();

        game.movePiece(game.board[1][0].pieceOnMe, 2, 0);
        game.printBoard();

        Board board = new Board();

        gp = board.drawBoard(gp); //Calls drawBoard from the Board Class

        //top left is 0,0
        //Bottom Right is 7,7


        for (int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.addPane(gp,i, j); //Calls the addPane from board Class, which returns what tile was clicked.

            }
        }


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(gp, 300, 275));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
