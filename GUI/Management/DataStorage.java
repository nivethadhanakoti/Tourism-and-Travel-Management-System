package Management;
import java.io.*;
import java.util.Scanner;

public class DataStorage
{
    private static final String FILE_NAME="login.txt";
    public static void saveUserData(String userData)
    {
        try(FileWriter writer=new FileWriter(FILE_NAME, true))
        {
            writer.write(userData+System.lineSeparator());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isUserRegistered(long phone, String password)
    {
        try(Scanner sc=new Scanner(new File(FILE_NAME)))
        {
            while(sc.hasNextLine())
            {
                String line=sc.nextLine();
                if(line.contains(Long.toString(phone)) && line.contains(password))
                {
                    return true;
                }
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
