package sample;

import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Server{

    private ServerSocket serverSocket;
    public static ArrayList<String> activeUsers = new ArrayList<>();
    public static ArrayList<String> challengeUsers = new ArrayList<>();

    public void startServer(){
        new ClientConnectListener().start();
    }

    public static void main(String[] args) {
        System.out.println("Test");
        new Server().startServer();
    }

    private class ClientConnectListener extends Thread {

        public void run(){
            try {
                serverSocket = new ServerSocket(1300);
                System.out.println("Server is Up, Listening for connections");
            } catch (IOException e){
                e.printStackTrace();
            }
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    try {
                        ClientConnectHandler handler = new ClientConnectHandler(clientSocket);
                        Thread thread = new Thread(handler);
                        thread.start();
                    } catch (Exception e){
                        clientSocket.close();
                    }
                }
            } catch (Exception e){
                try {
                    serverSocket.close();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }
    }
}
