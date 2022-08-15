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
import java.sql.SQLException;

import static org.example.App.databaseConnection;
public class EditBookController
{
    @FXML
    private TextField authorField, titleField, genreField, bidField;

    @FXML
    private Button cancelButton, approveButton;
    //Anulowanie
    @FXML
    public void cancel()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //Zatwierdzanie edycji ksiazki
    @FXML
    public void approve()
    {
        if(bidField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty() || titleField.getText().trim().isEmpty() || genreField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić pola zaznaczone *!");
            alert.show();
        }
        else
        {
            BookData bookData = new BookData();

            bookData.setBid(Integer.parseInt(bidField.getText()));
            bookData.setAuthor(authorField.getText());
            bookData.setTitle(titleField.getText());
            bookData.setGenre(genreField.getText());

            try
            {
                databaseConnection.edit_book(bookData.getBid(),bookData.getAuthor(),bookData.getTitle(),bookData.getGenre());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Edytowano pomyslnie!");
                alert.show();

                Stage stage = (Stage) approveButton.getScene().getWindow();
                stage.close();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
