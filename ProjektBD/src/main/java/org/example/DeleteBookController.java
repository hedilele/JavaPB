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

import static org.example.App.databaseConnection;
public class DeleteBookController
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField bidField;

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
    public void delete(ActionEvent event) throws IOException, SQLException
    {
        if(bidField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę podac bid ksiazki!");
            alert.show();
        }
        else
        {
            boolean wynik;
            int bid = Integer.parseInt(bidField.getText());
            wynik = databaseConnection.weryfikacja_bid(bid);

            if(wynik)
            {
                databaseConnection.delete_book(bid);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Pomyslnie usunieto pozycje o bid: " + bid);
                alert.show();

                root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Podany bid nie istnieje!");
                alert.show();
            }
        }
    }
}
