package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        ChessBoardMoveVisitor cbmv = new ChessBoardMoveVisitor();
        MateCheckVisitor mcv = new MateCheckVisitor();

        Player[] p = new Player[2];
        p[0] = new Player("Aleem");
        p[1] = new Player("Bob");


        Game game = new Game(p);
        game.printBoard();
        game.accept(cbmv, game, p[0]);
        game.movePiece(p[0],game.board[6][4].pieceOnMe, 5, 4);
        game.accept(cbmv, game, p[1]);
        game.movePiece(p[1],game.board[1][0].pieceOnMe, 2, 0);
        game.accept(cbmv, game, p[0]);
        game.movePiece(p[0],game.board[7][5].pieceOnMe, 4, 2);
        game.accept(cbmv, game, p[1]);
        game.movePiece(p[1],game.board[2][0].pieceOnMe, 3, 0);
        game.accept(cbmv, game, p[0]);
        game.movePiece(p[0],game.board[7][3].pieceOnMe, 3, 7);
        game.accept(cbmv, game, p[1]);
        game.movePiece(p[1],game.board[3][0].pieceOnMe, 4, 0);
        game.accept(cbmv, game, p[0]);
        game.movePiece(p[0],game.board[3][7].pieceOnMe, 1, 5);
        game.printBoard();
        game.accept(cbmv, game, p[0]);



        game.accept(mcv, game, p[1]);

        System.out.println(game.players[1].inMate);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
