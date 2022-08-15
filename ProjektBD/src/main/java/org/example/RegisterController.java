package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static org.example.App.databaseConnection;

public class RegisterController
{

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField nameField, lastnameField, ageField, addressField, emailField, passwordField;

    @FXML
    private Label message;

    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("first.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void submit(ActionEvent event) throws IOException
    {
        if(nameField.getText().trim().isEmpty() || lastnameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić pola zaznaczone *!");
            alert.show();
        }
        else
        {
            RegisterData registerData = new RegisterData();
            EmailPassVal emailPassVal = new EmailPassVal();

            registerData.setName(nameField.getText());
            registerData.setLastname(lastnameField.getText());
            registerData.setEmail(emailField.getText());
            registerData.setPassword(passwordField.getText());


            if(!ageField.getText().trim().isEmpty())
            {
                registerData.setAge(Integer.parseInt(ageField.getText()));
            }
            else
            {
                registerData.setAge(0);
            }

            if(!addressField.getText().trim().isEmpty())
            {
                registerData.setAddress(addressField.getText());
            }
            else
            {
                registerData.setAddress("Nie podano");
            }


            try
            {
                databaseConnection.weryfikacja(registerData.getEmail(),registerData.getPassword());

                if(EmailPassVal.isValidEmail(registerData.getEmail()))
                {
                    if(EmailPassVal.isValidPass(registerData.getPassword()))
                    {
                        databaseConnection.add(registerData.getName(),registerData.getLastname(),registerData.getAge(),registerData.getAddress(),registerData.getEmail(),registerData.getPassword());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Zarejestrowano pomyslnie!");
                        alert.show();
                        Thread thread = new Thread(() ->
                        {
                            try
                            {
                                Thread.sleep(2000);
                                if(alert.isShowing())
                                {
                                    Platform.runLater(alert::close);
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        });
                        thread.setDaemon(true);
                        thread.start();

                        //Przelaczanie na scene glowna
                        root = FXMLLoader.load(getClass().getResource("first.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else
                    {
                        message.setText("Haslo niepoprawne! Znakow 8 - 20, znak specjalny, cyfra, duza litera");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Uzupelnij poprawnie email, ex. example@ex.com");
                    alert.show();
                }
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
