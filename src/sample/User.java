package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable{

    private String username;
    private String password;
    private Boolean response;
    private String colour;

    public User(){

    }

    public User(String username, Boolean response, String colour){
        this.username = username;
        this.response = response;
        this.colour = colour;
    }

    public User(String username, String password){
        this.password = password;
        this.username = username;
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
