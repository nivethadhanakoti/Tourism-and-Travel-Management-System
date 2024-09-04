package Management;
import java.util.*;
import javax.swing.JTextArea;
import java.io.*;
import java.sql.Time;

public class FlightBooking 
{
    private static final String FLIGHT_FILE_PATH = "flights.txt"; 
    private static final String BOOKED_FLIGHTS_FILE_PATH = "bookedFlights.txt";
    public static void saveUserData(String userData) 
    {
        try (FileWriter writer = new FileWriter(BOOKED_FLIGHTS_FILE_PATH, true)) 
        {
            writer.write(userData + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static boolean isSeatAlreadyBooked(String flightNumber, int seatNumber) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader("bookedFlights.txt"))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(",");
                String number = data[0].trim(); 

                if (number.equals(flightNumber)) 
                {
                    int bookedSeat = Integer.parseInt(data[1].trim());
                    if (seatNumber == bookedSeat) 
                    {
                        return true; 
                    }
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return false; 
    }

    public static void displayBookedSeats(JTextArea bookedSeatsTextArea, String flightNumber) {
        String bookedFile = "bookedFlights.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(bookedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookedData = line.split(",");
                String bookedFlightNumber = bookedData[0].trim();
                if (bookedFlightNumber.equals(flightNumber)) {
                    bookedSeatsTextArea.append(line + "\n");
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
