package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.App.databaseConnection;
public class BorrowController implements Initializable
{
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private TableView<BookData> table;
    @FXML
    private TableColumn<BookData, Integer> bidCol;
    @FXML
    private TableColumn<BookData, String> authorCol;
    @FXML
    private TableColumn<BookData, String> titleCol;
    @FXML
    private TableColumn<BookData, String> genreCol;
    @FXML
    private TableColumn<BookData, Integer> numberCol;
    @FXML
    private Button quitButton, editButton;

    @FXML
    private TextField keywordField;

    //Observable List do TableView
    //private ObservableList<BookData> observableList = FXCollections.observableArrayList();
    //W tym robimy czary mary :v
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
            databaseConnection.show_books();

            bidCol.setCellValueFactory(new PropertyValueFactory<>("bid"));
            authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
            numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));


            table.setItems(databaseConnection.observableList);

            //Dodawanie mozliwosci wyszukiwania slow kluczowych w bazie
            FilteredList<BookData> filteredList = new FilteredList<>(databaseConnection.observableList, b -> true);

            keywordField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(bookData -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                    {
                        return true;
                    }

                    String search = newValue.toLowerCase();

                    if(bookData.getAuthor().toLowerCase().indexOf(search) > -1)
                    {
                        return true; //Zwracamy true jesli nasza wyszukiwana fraza znajduje sie na liscie
                    }
                    else if(bookData.getTitle().toLowerCase().indexOf(search) > -1)
                    {
                        return true;
                    }
                    else if(bookData.getGenre().toLowerCase().indexOf(search) > -1)
                    {
                        return true;
                    }
                    else
                    {
                        return false; //Brak wyszukiwanego wzorca zwraca false
                    }
                });

            });

            //Sortowanie naszej listy przy uzyciu listy posortowanej
            SortedList<BookData> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedList);
    }

    //Wychodzenie
    @FXML
    public void quit() throws IOException
    {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    //Edytowanie dodanych ksiazek
    @FXML
    public void edit() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editbook.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setTitle("Edycja Ksiazki");
        stage2.setScene(scene);
        stage2.show();
    }
}
