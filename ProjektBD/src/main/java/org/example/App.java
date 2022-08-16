package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application
{
    public static DatabaseConnection databaseConnection;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("first.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Strona wyboru");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        try
        {
            databaseConnection = new DatabaseConnection();
            //databaseConnection.createBase(); //Do tworzenia nowych tabel - Tabela do rejestracji
            //databaseConnection.bookBase(); //Tabela do ksiazek
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        launch();
    }
}