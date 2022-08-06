package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController
{
    @FXML
    private Button loginButton, registerButton, cancelButton;

    @FXML
    private void login() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setTitle("Logowanie");
        stage2.setScene(scene);
        stage2.show();
    }

    @FXML
    public void register() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setTitle("Rejestracja");
        stage2.setScene(scene);
        stage2.show();
    }

    @FXML
    public void cancel() throws IOException
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
