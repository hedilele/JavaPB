package org.example;

import javafx.scene.Parent;
import javafx.scene.control.Alert;

import javax.xml.transform.Result;
import java.sql.*;

public class DatabaseConnection
{
    private Statement statement = null;
    private Connection connection = null;
    //private ResultSet resultSet = null;

    public DatabaseConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","Bakalie37");
        System.out.println("Opened database successfully");
    }

    //Dodawanie do bazy
    public void add(String name, String lastname, int age, String address, String email, String password) throws ClassNotFoundException, SQLException
    {
            statement = connection.createStatement();
            String sql = "INSERT INTO register VALUES ('"+name+"' , '"+lastname+"', "+age+", '"+address+"', '"+email+"', '"+password+"');";

            statement.executeUpdate(sql);
            statement.close();

            /*
            if(!resultSet.isBeforeFirst())
            {
                System.out.println("Podany email jest zajety!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nie mozna uzyc podanego emaila!");
                alert.show();
            }
            else
            {
                String sql = "INSERT INTO register VALUES ('"+name+"' , '"+lastname+"', "+age+", '"+address+"', '"+email+"', '"+password+"');";
                statement.executeUpdate(sql);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(resultSet != null)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            if(statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
             */
    }

    //Tworzenie tabeli
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
}
