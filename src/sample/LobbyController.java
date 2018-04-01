package sample;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;


public class LobbyController {

    @FXML
    private ListView<String> userList = new ListView<>();
    private ArrayList<String> userNames;

    private String address = "localhost";
    private Integer port = 1300;

    private String currentItemSelected;

    private String currentUsername = LoginController.usernameLogin;

    @FXML
    private BorderPane tables;

    private Stage stage = (Stage) tables.getScene().getWindow();

    public void initialize() throws IOException{
        refreshLobby();

        userList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2){
                    currentItemSelected = userList.getSelectionModel()
                            .getSelectedItem();
                    try {
                        challengeUser(currentItemSelected);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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

    public void refreshLobby() throws IOException {
        Socket socket3 = new Socket(address, port);
        ObjectInputStream objectIn = new ObjectInputStream(socket3.getInputStream());
        try {
            userNames = new ArrayList<>((ArrayList<String>) objectIn.readObject());
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("closeConnection");
        socket3.shutdownInput();
        //closeConnection(socket3);
        userList = new ListView<>(FXCollections.observableArrayList(userNames));
        tables.setCenter(userList);
    }

    /*
    public void closeConnection(Socket socket) throws IOException{
        InetAddress socketAddress = socket.getInetAddress();
        socket.close();
        Socket temp = new Socket(address, port);
        PrintWriter out = new PrintWriter(temp.getOutputStream());
        out.println("ServerExit");
        out.println(socketAddress);
        out.flush();
    }*/

    public void challengeUser(String user) throws IOException{
        if (user.equals(currentUsername)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't play against yourself", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to challenge " + user + " ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Socket socket = new Socket(address, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Challenge User");
                out.println(user);
                out.println(currentUsername);
                out.flush();
                //closeConnection(socket);
            }
        }
    }
}


