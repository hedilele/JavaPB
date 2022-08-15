package org.example;

public class BookData
{
    private int bid;
    private String author;
    private String title;
    private String genre;
    private String email;


    //Usuniecie number i dodanie maila
    public BookData(int bid,String author, String title, String genre, String email)
    {
        this.bid = bid;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.email = email;
    }

    public BookData(String author, String title, String genre, String addedEmail)
    {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.email = email;
    }
    public BookData(int bid, String author, String title, String genre)
    {
        this.bid = bid;
        this.author = author;
        this.title = title;
        this.genre = genre;
    }
    public BookData()
    {
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public int getBid()
    {
        return bid;
    }

    public void setBid(int bid)
    {
        this.bid = bid;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "BookData{" +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
