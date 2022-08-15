package org.example;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController
{
    private Parent root;
    private Scene scene;
    private Stage stage;

    //Wylogowywanie sie
    @FXML
    public void logout(ActionEvent event) throws IOException
    {
        Pamiec.pamiec = null; //Czyszczenie pamieci

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Wypozyczanie ksiazki
    @FXML
    public void viewbook() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showbook.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setTitle("Ksiazki");
        stage2.setScene(scene);
        stage2.show();
    }

    //Dodawanie ksiazki do bazy
    @FXML
    public void addbook(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("addbook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Dodawanie ksiazki");
        stage.setScene(scene);
        stage.show();
    }

    //Usuwanie ksiazki z bazy
    @FXML
    public void deletebook(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("deletebook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Usuwanie ksiazki");
        stage.setScene(scene);
        stage.show();
    }
}
