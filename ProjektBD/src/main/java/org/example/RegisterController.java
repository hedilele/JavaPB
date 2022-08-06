package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.App.databaseConnection;

public class RegisterController
{

    @FXML
    private TextField nameField, lastnameField, ageField, addressField, emailField, passwordField;

    @FXML
    private Button cancelButton, submitButton;

    @FXML
    public void cancel()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void submit()
    {
        //LEPSZA WALIDACJA - UWZGLEDNIC WSZYSTKIE MOZLIWOSCI !
        if(nameField.getText().trim().isEmpty() || lastnameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić pola zaznaczone *!");
            alert.show();
        }
        else
        {
            RegisterData registerData;
            String name = nameField.getText();
            String lastname = lastnameField.getText();
            String address;
            String email = emailField.getText(); //EMAIL NIE MOZE SIE POWTORZYC W REJESTRACJI - POTRZEBNA WALIDACJA!
            String password = passwordField.getText();
            int age;
            if(!ageField.getText().trim().isEmpty())
            {
                age = Integer.parseInt(ageField.getText());
            }
            else
            {
                age = 0;
            }

            if(!addressField.getText().trim().isEmpty())
            {
                address = addressField.getText();
            }
            else
            {
                address = "Nie podano";
            }


            try
            {

                databaseConnection.weryfikacja(email,password);

                registerData = new RegisterData(name,lastname,age,address,email,password);

                databaseConnection.add(name,lastname,age,address,email,password);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Zarejestrowano pomyslnie!");
                alert.show();
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
            catch (SQLException ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Podany email jest juz zajety!");
                alert.show();
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
