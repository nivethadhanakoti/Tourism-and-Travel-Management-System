package Management;
import java.io.*;
import java.util.Scanner;
public class Signup
{
    private String name;
    private String email;
    private char gender;
    private String address;
    private long phone;
    private String password;
    private String confirmPassword;

    public Signup(String name, String email, char gender, String address, long phone, String password, String confirmPassword)
    {
        this.name=name;
        this.email=email;
        this.gender=gender;
        this.address=address;
        this.phone=phone;
        this.password=password;
        this.confirmPassword=confirmPassword;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public char getGender()
    {
        return gender;
    }

    public long getPhone()
    {
        return phone;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPassword()
    {
        return password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }
}

