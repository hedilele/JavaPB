package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import javax.xml.transform.Result;
import java.sql.*;

import static org.example.App.databaseConnection;

public class DatabaseConnection
{
    private Statement statement = null;
    private Connection connection = null;

    //ObservableList<BookData> observableList = FXCollections.observableArrayList();
    ObservableList<BookData> observableList = FXCollections.observableArrayList();

    public DatabaseConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","Bakalie37");
        System.out.println("Opened database successfully");
    }

    //Dodawanie do bazy uzytkownika
    public void add(String name, String lastname, int age, String address, String email, String password) throws ClassNotFoundException, SQLException
    {
            statement = connection.createStatement();
            String sql = "INSERT INTO register VALUES ('"+name+"' , '"+lastname+"', "+age+", '"+address+"', '"+email+"', '"+password+"');";

            statement.executeUpdate(sql);
            statement.close();
    }

    //Dodawanie do bazy ksiazki
    public void add_book(String author, String title, String genre, int number) throws ClassNotFoundException, SQLException
    {
        //bid = resultSet.getInt(1);
        //statement = connection.createStatement();
        //String sql1 = "SELECT COUNT(*) FROM Books";
        //JAK ZROBIC DODAWANIE BIDU?
        //"+bid+",
        //String sql = "INSERT INTO books VALUES ('"+author+"' , '"+title+"', '"+genre+"', "+number+");";

        String sql = "INSERT INTO books(author,title,genre,number) values(?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        String cos;
        ps.setString(1, author);
        ps.setString(2, title);
        ps.setString(3, genre);
        ps.setInt(4, number);
        //statement.executeUpdate(sql);
        //statement.close();
        ps.executeUpdate();
        ps.close();
    }

    //Usuwanie ksiazki
    public void delete_book(int bid) throws SQLException
    {
        String sql = "DELETE FROM Books WHERE bid = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bid);

        ps.executeUpdate();
        ps.close();
    }

    //Edytowanie ksiazki
    public void edit_book(int bid, String author, String title, String genre) throws SQLException
    {
        String sql = "UPDATE books SET author = ?, title = ?, genre = ? WHERE bid = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, author);
        ps.setString(2, title);
        ps.setString(3, genre);
        ps.setInt(4, bid);

        ps.executeUpdate();
        ps.close();
    }


    //Tworzenie tabeli register do przechowywania danych o osobach
    public void createBase() throws SQLException
    {
        statement = connection.createStatement();
        String sql = "CREATE TABLE register " +
                "(NAME TEXT NOT NULL, " +
                "LASTNAME TEXT NOT NULL, " +
                "AGE INT NOT NULL, " +
                "ADDRESS    CHAR(50), " +
                "EMAIL TEXT PRIMARY KEY NOT NULL, " +
                "PASSWORD TEXT NOT NULL)";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    //Tworzenie tabeli book do przechowywania danych z ksiazkami
    public void bookBase() throws SQLException
    {
        statement = connection.createStatement();
        String sql = "CREATE TABLE books " +
                "(BID SERIAL PRIMARY KEY, " +
                "AUTHOR TEXT NOT NULL, " +
                "TITLE TEXT NOT NULL, " +
                "GENRE TEXT NOT NULL, " +
                "NUMBER INT NOT NULL)";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }
    //Wypisywanie informacji z bazy - pomocnicze
    public void wypiszAll() throws SQLException
    {
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Register;");
        while(resultSet.next())
        {
            String name = resultSet.getString("name");
            String lastname = resultSet.getString("lastname");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            System.out.println(name + " ," + lastname + " ," + age + " ," + address + " ," + email + " ," + password);
        }
    }


    //Sprawdzanie czy podany email i haslo znajduja się w bazie - funckaj pomocnicza
    private ResultSet sprawdz(Connection connection, String email, String password) throws SQLException, ClassNotFoundException
    {
        String sql = "SELECT COUNT(*) FROM Register WHERE email = ? AND password = ?;";
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public boolean weryfikacja(String email, String password) throws SQLException, ClassNotFoundException
    {

        ResultSet resultSet = sprawdz(connection, email, password);
        int wynik = 0;

        while (resultSet.next())
        {
            wynik = resultSet.getInt(1);
        }

        if(wynik == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Wyswietlanie danych z tabeli ksiazki
    public void show_books()
    {
        databaseConnection.observableList.clear(); //czyszczenie lity po uzyciu - jesli tego nie zrobimy do dane z bazy
                                                   //beda sie duplikowaly w naszym tableview
        try
        {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Books");
            while(resultSet.next())
            {
                observableList.add(new BookData(resultSet.getInt(1),resultSet.getString("author"), resultSet.getString("title"),
                        resultSet.getString("genre"), resultSet.getInt("number")));

            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    //Do weryfikacji bidu!
    private ResultSet sprawdz_bid(Connection connection, int bid) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM Books WHERE bid = ?";
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bid);

        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //identycznie jak w weryfikacji maila
    public boolean weryfikacja_bid(int bid) throws SQLException
    {
        ResultSet resultSet = sprawdz_bid(connection, bid);
        int wynik = 0;

        while (resultSet.next())
        {
            wynik = resultSet.getInt(1);
        }

        if(wynik == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
