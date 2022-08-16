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
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.App.databaseConnection;
public class AddBookController
{
    private Stage stage;
    private Parent root;
    private Scene scene;

    private final ResultSet resultSet = null;


    @FXML
    private TextField authorField, titleField, genreField;

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
        if(authorField.getText().trim().isEmpty() || titleField.getText().trim().isEmpty() || genreField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnić pola zaznaczone *!");
            alert.show();
        }
        else
        {
            BookData bookData = new BookData();

            bookData.setAuthor(authorField.getText());
            bookData.setTitle(titleField.getText());
            bookData.setGenre(genreField.getText());
            System.out.println();

            try
            {
                databaseConnection.weryfikacja_ksiazki(bookData.getTitle());
                databaseConnection.add_book(bookData.getAuthor(),bookData.getTitle(),bookData.getGenre(),Pamiec.pamiec.getEmail());

                root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Dodano pomyslnie!");
                alert.show();
            }
            catch (SQLException ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Podany tytul znajduje sie w bazie!");
                alert.show();
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
