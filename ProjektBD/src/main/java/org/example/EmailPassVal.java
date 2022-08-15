package org.example;

import java.util.regex.Pattern;

//Klasa do walidowania emaila i hasla
public class EmailPassVal
{
    //Walidacja maila
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);//Wyszukiwanie wzorca
        if(email == null)
        {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    //Walidacja hasla
    public static boolean isValidPass(String password)
    {
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20}$";

        Pattern pattern = Pattern.compile(passwordRegex);
        if(password == null)
        {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
