package sample;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class LoginController {

    public static String usernameLogin;


    @FXML
    PasswordField password;
    @FXML
    TextField username;
    @FXML
    GridPane center;

    @FXML
    public void Login(){
        try {
            Socket socket1 = new Socket(Main.address, Main.port);
            usernameLogin = username.getText();
            Main.currentUsername = usernameLogin;
            PrintWriter out = new PrintWriter(socket1.getOutputStream());
            out.println("Login");
            out.println(usernameLogin);
            out.println(password.getText());
            out.flush();
            socket1.shutdownOutput();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));

            if(Boolean.valueOf(in.readLine()) == true){
                Alert alert = new Alert(Alert.AlertType.ERROR, "User is already logged in", ButtonType.OK);
                alert.showAndWait();
                username.clear();
                password.clear();
                System.out.println("closeConnection5");
                socket1.shutdownInput();
            } else {
                if (Boolean.valueOf(in.readLine()) == true) {
                    username.clear();
                    password.clear();

                    System.out.println("closeConnection4");
                    socket1.shutdownInput();


                    //primaryStage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                    Parent root = (Parent)loader.load();
                    Scene scene = new Scene(root, 300, 450);

                    Stage stage = (Stage) center.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();


                } else if (Boolean.valueOf(in.readLine()) == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The Username and Password is Incorrect", ButtonType.OK);
                    alert.showAndWait();
                    username.clear();
                    password.clear();

                    socket1.shutdownInput();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void createUserScene() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAccount.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root, 439, 230);

        Stage stage = (Stage) center.getScene().getWindow();
        stage.setScene(scene);

    }
}