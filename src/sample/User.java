package sample;

import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable{

    private String username;
    private String password;
    private Socket socket;
    private Integer port;

    public User(){

    }

    public User(String username, Socket socket, Integer port){
        this.username = username;
        this.socket = socket;
        this.port = port;
    }

    public User(String username, String password){
        this.password = password;
        this.username = username;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return (this.getUsername());
    }
}
