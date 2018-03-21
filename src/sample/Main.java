package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Player[] p = new Player[2];
        p[0] = new Player();
        p[1] = new Player();


        Game game = new Game(p,3.0);
        game.printBoard();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
