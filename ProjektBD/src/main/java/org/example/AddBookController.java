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
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.App.databaseConnection;
public class AddBookController
{
    private Stage stage;
    private Parent root;
    private Scene scene;

    private ResultSet resultSet = null;
    @FXML
    private Button cancelButton, submitButton;

    @FXML
    private TextField authorField, titleField, genreField, numberField;

    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void submit(ActionEvent event) throws IOException
    {
        if(authorField.getText().trim().isEmpty() || titleField.getText().trim().isEmpty() || genreField.getText().trim().isEmpty() || numberField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić pola zaznaczone *!");
            alert.show();
        }
        else
        {
            BookData bookData;
            String author = authorField.getText();
            String title = titleField.getText();
            String genre = genreField.getText();
            int number = Integer.parseInt(numberField.getText());
            try
            {
                bookData = new BookData(author,title,genre,number);

                databaseConnection.add_book(author,title,genre,number);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Dodano pomyslnie!");
                alert.show();

                root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
