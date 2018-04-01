package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {

    public static String usernameLogin;

    public static String address = "localhost";
    public static Integer port = 1300;

    private Button userLogin;
    private TextField username;
    private PasswordField password;

    private Button newUser;
    private TextField newUsername;
    private PasswordField newPassword;
    private PasswordField confirmPassword;
    private Button newSubmit;

    public ListView<String> userList = new ListView<>();
    private ArrayList<String> userNames;
    private Button refresh = new Button("Refresh");

    public GridPane tables;
    private BorderPane lobbyLayout;
    private GridPane bottomLeft;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Login Buddy");

        BorderPane layout = new BorderPane();
        GridPane center = new GridPane();
        center.setHgap(10);
        center.setVgap(10);

        Label usrLabel = new Label("Username:");
        username = new TextField();
        Label passLabel = new Label("Password:");
        password = new PasswordField();
        Label portLabel = new Label("Port:");
        userLogin = new Button("Login");
        newUser = new Button("Create New User");
        newSubmit = new Button("Submit");

        Label or = new Label("Or: ");

        center.add(usrLabel, 1, 1);
        center.add(username, 2, 1);
        center.add(passLabel, 1, 2);
        center.add(password, 2, 2);
        center.add(portLabel,1,3);
        center.add(userLogin, 2, 4);
        center.add(or, 2, 5);
        center.add(newUser, 2, 6);

        userLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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


                            lobbyLayout = new BorderPane();
                            tables = new GridPane();

                            primaryStage.close();
                            connectToLobby();

                            lobbyLayout.setCenter(tables);

                            bottomLeft = new GridPane();
                            refresh.setPadding(new Insets(10, 10, 10, 10));
                            bottomLeft.add(refresh, 0, 0);
                            bottomLeft.setVgap(10);
                            bottomLeft.setHgap(10);
                            lobbyLayout.setBottom(bottomLeft);


                            Scene lobby = new Scene(lobbyLayout, 500, 500);
                            primaryStage.setScene(lobby);
                            primaryStage.show();

                            System.out.println("TEST 231");

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
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    connectToLobby();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        newUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                center.getChildren().clear();

                Label usrLabel = new Label("New Username:");
                newUsername = new TextField();
                Label passLabel = new Label("New Password:");
                newPassword = new PasswordField();
                Label confirmPassLabel = new Label("Confirm Password:");
                confirmPassword = new PasswordField();

                center.add(usrLabel, 1, 1);
                center.add(newUsername, 2, 1);
                center.add(passLabel, 1, 2);
                center.add(newPassword, 2, 2);
                center.add(confirmPassLabel,1, 3);
                center.add(confirmPassword, 2, 3);
                center.add(newSubmit, 2, 5);

            }
        });

        newSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!newPassword.getText().equals(confirmPassword.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The Passwords did not match, try again", ButtonType.OK);
                    alert.showAndWait();
                    confirmPassword.clear();
                    newPassword.clear();
                } else {
                    try {

                        Socket socket2 = new Socket(address, port);

                        PrintWriter out = new PrintWriter(socket2.getOutputStream());
                        out.println("New User");
                        out.println(newUsername.getText().toLowerCase());
                        out.println(newPassword.getText());
                        out.flush();


                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket2.getInputStream()));

                        if(Boolean.valueOf(in.readLine()) == true){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Username Already Exists", ButtonType.OK);
                            alert.showAndWait();
                            newUsername.clear();
                            confirmPassword.clear();
                            newPassword.clear();

                            System.out.println("closeConnection3");
                            socket2.shutdownInput();
                            closeConnection(socket2);
                        } else {
                            center.getChildren().clear();
                            center.add(usrLabel, 1, 1);
                            center.add(username, 2, 1);
                            center.add(passLabel, 1, 2);
                            center.add(password, 2, 2);
                            center.add(portLabel,1,3);
                            center.add(userLogin, 2, 4);
                            center.add(or, 2, 5);
                            center.add(newUser, 2, 6);

                            System.out.println("closeConnection2");
                            socket2.shutdownInput();
                            closeConnection(socket2);

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Created!", ButtonType.OK);
                            alert.showAndWait();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        layout.setCenter(center);
        Scene opening = new Scene(layout, 350, 250);

        primaryStage.setScene(opening);
        primaryStage.show();
    }

    public void connectToLobby() throws IOException {
        Socket socket3 = new Socket(address, port);
        PrintWriter out = new PrintWriter(socket3.getOutputStream());
        out.println("Get User List");
        out.println(usernameLogin);
        out.flush();
        System.out.println("closeConnection");
        socket3.shutdownInput();
        closeConnection(socket3);

        ObjectInputStream objectIn = new ObjectInputStream(socket3.getInputStream());
        try {
            userNames = new ArrayList<>((ArrayList<String>) objectIn.readObject());
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("closeConnection");
        socket3.shutdownInput();
        closeConnection(socket3);

        userList = new ListView<>(FXCollections.observableArrayList(userNames));
        tables.add(userList, 1, 0);
    }

    public static void main(String[] args) {
        launch(args);
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
