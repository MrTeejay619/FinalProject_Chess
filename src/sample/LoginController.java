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

    private String address = "localhost";
    private int port = 1300;

    @FXML
    PasswordField password;
    @FXML
    TextField username;
    @FXML
    GridPane center;

    @FXML
    public void Login(){
        try {
            Socket socket1 = new Socket(address, port);
            usernameLogin = username.getText();
            PrintWriter out = new PrintWriter(socket1.getOutputStream());
            out.println("Login");
            out.println(usernameLogin);
            out.println(password.getText());
            out.flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));

            if(Boolean.valueOf(in.readLine()) == true){
                Alert alert = new Alert(Alert.AlertType.ERROR, "User is already logged in", ButtonType.OK);
                alert.showAndWait();
                username.clear();
                password.clear();
                System.out.println("closeConnection5");
                socket1.shutdownInput();
                closeConnection(socket1);
            } else {
                if (Boolean.valueOf(in.readLine()) == true) {
                    // TODO: GO TO New Stage
                    username.clear();
                    password.clear();

                    System.out.println("closeConnection4");
                    socket1.shutdownInput();
                    closeConnection(socket1);


                    //primaryStage.close();
                    Socket socket3 = new Socket(address, port);
                    PrintWriter outW = new PrintWriter(socket3.getOutputStream());
                    outW.println("Get User List");
                    outW.println(usernameLogin);
                    outW.flush();
                    System.out.println("closeConnection");
                    socket3.shutdownInput();
                    closeConnection(socket3);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby.fxml"));
                    Parent root = (Parent)loader.load();
                    Scene scene = new Scene(root, 300, 450);

                    Stage stage = (Stage) center.getScene().getWindow();
                    stage.setScene(scene);


                } else if (Boolean.valueOf(in.readLine()) == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The Username and Password is Incorrect", ButtonType.OK);
                    alert.showAndWait();
                    username.clear();
                    password.clear();

                    socket1.shutdownInput();
                    closeConnection(socket1);
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
        Scene scene = new Scene(root, 250, 400);

        Stage stage = (Stage) center.getScene().getWindow();
        stage.setScene(scene);

    }

    public void closeConnection(Socket socket) throws IOException{
        InetAddress socketAddress = socket.getInetAddress();
        socket.close();
        Socket temp = new Socket(address, port);
        PrintWriter out = new PrintWriter(temp.getOutputStream());
        out.println("ServerExit");
        out.println(socketAddress);
        out.flush();
    }
}