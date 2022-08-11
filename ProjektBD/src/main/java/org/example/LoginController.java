package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button loginButton,cancelButton;

    @FXML
    private TextField passwordFld, emailFld;

    public void cancel(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("first.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException
    {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String email;
        String password;
        if(emailFld.getText().trim().isEmpty() && passwordFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić wszystkie pola!");
            alert.show();
        }
        else if(emailFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę podac email!");
            alert.show();
        }
        else if(passwordFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę podac haslo!");
            alert.show();
        }
        else
        {
            boolean wynik = false;
            email = emailFld.getText();
            password = passwordFld.getText();

            wynik = databaseConnection.weryfikacja(email,password);

            if(wynik == true)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Pomyslnie zalogowano!");
                alert.show();

                root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            //Walidacja kiedy email jest dobry (istnieje), a haslo do podanego istniejacego maila jest zle!
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Podany email nie istnieje!");
                alert.show();
            }
        }
    }
}
