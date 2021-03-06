package sample;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Main extends Application {

    public static String currentUsername;
    public static String address = "localhost";
    public static int port = 1300;
    public static String colour;
    public static String opponent;
    private Stage firstStage;


    @Override
    public void start(Stage primaryStage) throws Exception{

        firstStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root, 439, 230);

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                new BoardStage(colour);
                try {
                    Socket close = new Socket(address, port);
                    PrintWriter out = new PrintWriter(close.getOutputStream());
                    out.println("Exit");
                    out.println(currentUsername);
                    out.flush();
                    close.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    Socket close = new Socket(address, port);
                    PrintWriter out = new PrintWriter(close.getOutputStream());
                    out.println("Exit");
                    out.println(currentUsername);
                    out.flush();
                    close.close();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class BoardStage extends Stage{
        public String myColour;

        BoardStage(String colour){
            myColour = colour;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
            try {
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root, 400, 450);
                this.setScene(scene);
            } catch (IOException e){
                e.printStackTrace();
                System.out.println("Failed to load fxml");
            }
            this.show();


            this.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
