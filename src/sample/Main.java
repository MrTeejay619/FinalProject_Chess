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


    @Override
    public void start(Stage primaryStage) throws Exception{



        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root, 439, 230);

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    System.out.println(currentUsername);
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

    public static void main(String[] args) {
        launch(args);
    }

}
