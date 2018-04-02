package sample;

import javafx.application.Platform;
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
import sun.nio.ch.SocketAdaptor;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


public class LobbyController {

    @FXML
    private ListView<String> userList = new ListView<>();
    private ArrayList<String> userNames;

    public static String currentItemSelected;

    private String currentUsername = Main.currentUsername;

    @FXML
    private BorderPane tables;

    public void initialize() throws IOException {
        refreshLobby();

        new Thread(new Refresh((long)2e9, this)).start();
        userList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    currentItemSelected = userList.getSelectionModel()
                            .getSelectedItem();
                    try {
                        challengeUser(currentItemSelected);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void refreshLobby() throws IOException {
        Socket socket = new Socket(Main.address, Main.port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("Get User List");
        out.flush();
        socket.shutdownOutput();


        ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
        try {
            userNames = new ArrayList<>((ArrayList<String>) objectIn.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userList = new ListView<>(FXCollections.observableArrayList(userNames));
        Platform.runLater(() -> {
            tables.setCenter(userList);
        });

        userList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    currentItemSelected = userList.getSelectionModel()
                            .getSelectedItem();
                    try {
                        challengeUser(currentItemSelected);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        socket.close();
    }

    public void challengeUser(String user) throws IOException {
        if (user.equals(currentUsername)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't play against yourself", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to challenge " + user + " ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Socket socket = new Socket(Main.address, Main.port);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Challenge User");
                out.println(user);
                out.println(currentUsername);
                Main.opponent = user;
                out.flush();
                socket.shutdownOutput();
                socket.close();
                getResponse();
            }
        }
    }

    public void getResponse() throws IOException {
        String selection = null;
        String colour = null;
        String challenger = null;
        while (true) {
            Socket socket2 = new Socket(Main.address, Main.port);
            PrintWriter out = new PrintWriter(socket2.getOutputStream());
            out.println("Get Response");
            out.println(currentUsername);
            out.flush();
            socket2.shutdownOutput();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket2.getInputStream()));
            selection = in.readLine();
            if (selection.equals("Yes")) {
                colour = in.readLine();
                Main.colour = colour;
                break;
            } else if (selection.equals("No")) {
                challenger = in.readLine();
                break;
            } else if (selection.equals("No Response")) {
                socket2.shutdownInput();
                socket2.close();
                continue;
            }
        }
        if (selection.equals("Yes")) {
            // TODO: Start new Game
            startGame();
        } else if (selection.equals("No")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    challenger + " has rejected your challenge", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void startGame() {

        Stage stage = (Stage) tables.getScene().getWindow();

        stage.hide();

    }

    public void challengeAccept() throws IOException {
        refreshLobby();

        Socket socket = new Socket(Main.address, Main.port);

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.println("Refresh");
        out.println(currentUsername);
        out.flush();
        socket.shutdownOutput();

        Random rand = new Random();
        int n = rand.nextInt(2);
        String colour;
        if (n == 1) {
            colour = "Black";
        } else {
            colour = "White";
        }
        Main.colour = colour;

        ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());

        Boolean check = objectIn.readBoolean();
        String challenger = objectIn.readUTF();

        socket.close();

        if (check) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        challenger + " has challenged you, do you accept?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();

                try {
                    if (result.get() == ButtonType.YES) {
                        Socket socket1 = new Socket(Main.address, Main.port);
                        PrintWriter out2 = new PrintWriter(socket1.getOutputStream());
                        out2.println("Challenge");
                        out2.println("Yes");
                        out2.println(challenger);
                        Main.opponent = challenger;
                        out2.println(currentUsername);
                        if(colour.equals("White")){
                            out2.println("Black");
                        } else {
                            out2.println("White");
                        }
                        out2.flush();
                        socket1.shutdownOutput();
                        startGame();
                    } else if (result.get() == ButtonType.NO) {
                        Socket socket1 = new Socket(Main.address, Main.port);
                        PrintWriter out2 = new PrintWriter(socket1.getOutputStream());
                        out2.println("Challenge");
                        out2.println("No");
                        out2.println(challenger);
                        out2.println(currentUsername);
                        out2.println(colour);
                        out2.flush();
                        socket1.shutdownOutput();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}


