package org.example;

public class RegisterData
{
    private String name;
    private String lastname;
    private int age;
    private String address;
    private String email;
    private String password;

    public RegisterData(String name, String lastname, int age, String address, String email, String password)
    {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public RegisterData(String email, String password)
    {
        this.email = email;
        this.password = password;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    //Pomocniczo, żeby wypisywac sobie czy dane wchodza do bazy
    @Override
    public String toString()
    {
        return "RegisterData{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
