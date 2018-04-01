package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class ServerConnectionHandler implements Runnable{

    private ObjectInputStream in;
    private String selection = "";
    private Socket socket;
    private ArrayList<String> users;
    private GridPane tables;
    private ListView<String> userList;

    private String currentItemSelected;

    private String currentUsername;

    public ServerConnectionHandler(int startingRow, int startingColumn, int destinationRow, int destinationColumn){

    }

    public ServerConnectionHandler(Socket socket, GridPane tables, String currentUsername) {
        this.socket = socket;
        this.tables = tables;
        this.currentUsername = currentUsername;
    }

    public ServerConnectionHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            //System.out.println("Test 2312");
            in = new ObjectInputStream(socket.getInputStream());
            selection = in.readUTF();

            System.out.println(selection);

            switch(selection) {
                case "Receive Move":
                    test();
                    break;
                case "List":
                    userList();
                    break;
                case "Accept Challenge":
                    receiveChallenge();
                    break;
                case "Start Game":
                    startGame();
                    break;
                case "Reject Game":
                    rejectGame();
                    break;
                default:
                    System.out.println("Do Nothing");
                    break;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void test() throws IOException{
        socket.shutdownInput();
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("Testing Fam");
        out.flush();
        socket.shutdownOutput();
    }

    public void userList() throws IOException {
        System.out.println("Hey");
        try {
            users = new ArrayList<>((ArrayList<String>) in.readObject());
        } catch (Exception e){
            e.printStackTrace();
        }
        socket.shutdownInput();
        System.out.println(users);
        userList = new ListView<>(FXCollections.observableArrayList(users));

        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                tables.add(userList, 1, 0);

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
            }
        });
    }

    public void challengeUser(String user) throws IOException{
        if (user.equals(currentUsername)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't play against yourself", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to challenge " + user + " ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Socket socket2 = new Socket(Main.address, Main.port);
                PrintWriter out = new PrintWriter(socket2.getOutputStream());
                out.println("Challenge User");
                out.println(user);
                out.println(currentUsername);
                out.flush();
                socket2.close();
            }
        }
    }

    public void receiveChallenge() throws IOException {
        String challenger = in.readUTF();
        socket.shutdownInput();
        System.out.println("I've received a challenge");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        challenger + " has challenged you, do you accept?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                try {
                    if (result.get() == ButtonType.YES) {
                        Socket socket2 = new Socket(Main.address, Main.port);
                        PrintWriter out = new PrintWriter(socket2.getOutputStream());
                        out.println("Start Game");
                        out.println("Yes");
                        out.println(challenger);
                        out.println(currentUsername);
                        out.flush();
                        socket2.shutdownOutput();
                    } else if (result.get() == ButtonType.NO) {
                        Socket socket2 = new Socket(Main.address, Main.port);
                        PrintWriter out = new PrintWriter(socket2.getOutputStream());
                        out.println("Start Game");
                        out.println("No");
                        out.println(challenger);
                        out.println(currentUsername);
                        out.flush();
                        socket2.shutdownOutput();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }


    public void startGame() throws IOException {
        String challenger = in.readUTF();
        socket.shutdownInput();

        

    }

    public void rejectGame() throws IOException{
        String challenger = in.readUTF();
        socket.shutdownInput();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        challenger + " has rejected your challenge", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
