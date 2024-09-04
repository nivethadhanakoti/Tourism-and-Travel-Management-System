package Management;
import java.util.*;

import javax.swing.JTextArea;

import java.io.*;
import java.sql.Time;

public class TrainReader 
{
    private static final String TRAIN_FILE_PATH = "trains.txt"; 
    public static void saveUserData(String userData) 
    {
        try (FileWriter writer = new FileWriter(TRAIN_FILE_PATH, true)) 
        {
            writer.write(userData + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void displayAvailableTrains(JTextArea optionsTextArea, String departure, String arrival, String date) {
        try (BufferedReader br = new BufferedReader(new FileReader("trains.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] trainData = line.split(",");
                String trainDeparture = trainData[3];
                String trainArrival = trainData[4];
                String trainDate = trainData[7];

                if (departure.equals(trainDeparture) && arrival.equals(trainArrival) && date.equals(trainDate)) {
                    optionsTextArea.append(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}