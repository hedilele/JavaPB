package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML
    private Button loginButton,cancelButton;

    @FXML
    private TextField passwordFld, emailFld;

    public void cancel()
    {
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void login() throws SQLException, ClassNotFoundException, IOException
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

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage2 = new Stage();
                stage2.setTitle("Glowne Menu");
                stage2.setScene(scene);
                stage2.show();
                stage.close();
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
