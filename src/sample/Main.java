package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

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

        gp = board.drawBoard(gp);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(gp, 300, 275));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
