package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField passwordFld, emailFld;

    //Anulowanie logowania
    public void cancel(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("first.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Metoda odpowiedzialna za zalogowanie
    public void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException
    {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        if(emailFld.getText().trim().isEmpty() && passwordFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Proszę wypelnić wszystkie pola!");
            alert.show();
        }
        else if(emailFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Proszę podac email!");
            alert.show();
        }
        else if(passwordFld.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Proszę podac haslo!");
            alert.show();
        }
        else
        {
            RegisterData registerData = new RegisterData();
            boolean wynik;

            registerData.setEmail(emailFld.getText());
            registerData.setPassword(passwordFld.getText());

            wynik = databaseConnection.weryfikacja(registerData.getEmail(),registerData.getPassword());
            Pamiec.pamiec = registerData;

            if(wynik)
            {
                root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Podane dane sa zle!");
                alert.show();
            }
        }
    }
}
