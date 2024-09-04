package Management;
import java.util.*;

import javax.swing.JTextArea;

import java.io.*;
import java.sql.Time;

public class TrainBooking 
{
    private static final String TRAIN_FILE_PATH = "trains.txt"; 
    private static final String BOOKED_TRAINS_FILE_PATH = "bookedTrains.txt";
    public static void saveUserData(String userData) 
    {
        try (FileWriter writer = new FileWriter(BOOKED_TRAINS_FILE_PATH, true)) 
        {
            writer.write(userData + System.lineSeparator());
        } 
        catch (IOException e) 
        { 
            e.printStackTrace();
        }
    }

    public static boolean isSeatAlreadyBooked(String trainNumber, int seatNumber) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader("bookedTrains.txt"))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(",");
                String number = data[0].trim(); 

                if (number.equals(trainNumber)) 
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

    public static void displayBookedSeats(JTextArea bookedSeatsTextArea, String trainNumber) {
        String bookedFile = "bookedTrains.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(bookedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookedData = line.split(",");
                String bookedTrainNumber = bookedData[0].trim();
                if (bookedTrainNumber.equals(trainNumber)) {
                    bookedSeatsTextArea.append(line + "\n");
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}