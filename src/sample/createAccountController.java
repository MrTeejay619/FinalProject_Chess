package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class createAccountController {

    @FXML
    private GridPane center2;

    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField newUsername;

    @FXML
    public void newSubmit() {
        if (!newPassword.getText().equals(confirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The Passwords did not match, try again", ButtonType.OK);
            alert.showAndWait();
            confirmPassword.clear();
            newPassword.clear();
        } else {
            try {

                Socket socket2 = new Socket(Main.address, Main.port);

                PrintWriter out = new PrintWriter(socket2.getOutputStream());
                out.println("New User");
                out.println(newUsername.getText().toLowerCase());
                out.println(newPassword.getText());
                out.flush();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket2.getInputStream()));

                if (Boolean.valueOf(in.readLine()) == true) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username Already Exists", ButtonType.OK);
                    alert.showAndWait();
                    newUsername.clear();
                    confirmPassword.clear();
                    newPassword.clear();

                    System.out.println("closeConnection3");
                    socket2.shutdownInput();
                } else {
                    System.out.println("closeConnection2");
                    socket2.shutdownInput();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Created!", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 500, 500);

            Stage stage = (Stage) center2.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
