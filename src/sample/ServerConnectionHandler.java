package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;

public class ServerConnectionHandler implements Runnable{

    private ObjectInputStream in;
    private String selection = "";
    private Socket socket;
    private ArrayList<String> users;
    GridPane tables;
    ListView<String> userList;

    public ServerConnectionHandler(Socket socket, GridPane tables) {
        this.socket = socket;
        this.tables = tables;
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
        try {
            users = new ArrayList<>((ArrayList<String>) in.readObject());
        } catch (Exception e){
            e.printStackTrace();
        }
        socket.shutdownInput();
        System.out.println(users);
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                userList = new ListView<>(FXCollections.observableArrayList(users));
                tables.add(userList, 1, 0);
            }
        });
    }
}
