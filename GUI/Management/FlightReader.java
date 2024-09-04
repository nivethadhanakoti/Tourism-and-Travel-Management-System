package Management;
import java.util.*;
import javax.swing.JTextArea;
import java.io.*;
import java.sql.Time;

public class FlightReader 
{
    private static final String FLIGHT_FILE_PATH = "flights.txt"; 
    public static void saveUserData(String userData) 
    {
        try (FileWriter writer = new FileWriter(FLIGHT_FILE_PATH, true)) 
        {
            writer.write(userData + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void displayAvailableFlights(JTextArea optionsTextArea, String departure, String arrival, String date) {
        try (BufferedReader br = new BufferedReader(new FileReader("flights.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] flightData = line.split(",");
                String flightDeparture = flightData[3];
                String flightArrival = flightData[4];
                String flightDate = flightData[7];

                if (departure.equals(flightDeparture) && arrival.equals(flightArrival) && date.equals(flightDate)) {
                    optionsTextArea.append(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

