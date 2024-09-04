package Management;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Time;
import java.awt.event.*;

interface Travelable
{
    public String getTransportMode();
    public String getVehicleNo();
    public int []getSeatNo();
    public float getCost();
}

public class Flight extends Transport implements Travelable
{
    private String VehicleNo;
    private int []seatNo;
    private String AirlineType;
    private String TicketType;
    private float cost;
    private String transportMode="Flight";

    public Flight(String VehicleNo, int[]seatNo, String AirlineType, String TicketType, String departureLocation, String arrivalLocation, float cost, String departureDate, String departureTime, String arrivalDate, String arrivalTime)
    {
        super(departureLocation, arrivalLocation, departureDate, departureTime, arrivalDate, arrivalTime);
        this.VehicleNo=VehicleNo;
        this.seatNo = new int[seatNo.length];
        for(int i=0; i<seatNo.length; i++)
            this.seatNo[i]=seatNo[i];
        this.AirlineType=AirlineType;
        this.TicketType=TicketType;
        this.cost=cost;
    }

    public String getTransportMode()
    {
        return transportMode;
    }
    public String getVehicleNo()
    {
        return VehicleNo;
    }
    public int []getSeatNo()
    {
        return seatNo;
    }
    public String getAirlineType()
    {
        return AirlineType;
    }
    public String getTicketType()
    {
        return TicketType;
    }
    public float calcCost()
    {
        int n=seatNo.length;
        cost=cost*n;
        return cost;
    }
    public float getCost()
    {
        return cost;
    }
    public void display(JFrame detailsFrame)
    {
        addLabeledField(detailsFrame, "Transport Mode:", getTransportMode());
        addLabeledField(detailsFrame, "Vehicle Number:", getVehicleNo());
        addLabeledField(detailsFrame, "Airline Type:", getAirlineType());
        addLabeledField(detailsFrame, "Number of Seats:", Integer.toString(seatNo.length));
        addLabeledField(detailsFrame, "Selected Seats:", Arrays.toString(getSeatNo())); 
        addLabeledField(detailsFrame, "Ticket Type:", getTicketType());
        addLabeledField(detailsFrame, "Cost:", Double.toString(calcCost()));
        addLabeledField(detailsFrame, "Departure Date:", getDepartureDate());
        addLabeledField(detailsFrame, "Departure Time:", getDepartureTime());
        addLabeledField(detailsFrame, "Departure Place:", getDepartureLocation());
        addLabeledField(detailsFrame, "Arrival Date:", getArrivalDate());
        addLabeledField(detailsFrame, "Arrival Time:", getArrivalTime());
        addLabeledField(detailsFrame, "Arrival Place:", getArrivalLocation());
        detailsFrame.setSize(500, 400);
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setVisible(true);
    }

    private void addLabeledField(JFrame frame, String labelText, String value) 
    {
        JLabel label = new JLabel(labelText);
        JLabel fieldValue = new JLabel(value);

        // Set layout to a GridLayout with 1 row and 2 columns
        JPanel panel = new JPanel(new GridLayout(1, 2));

        // Add the label and field to the panel
        panel.add(label);
        panel.add(fieldValue);

        // Add the panel to the frame
        frame.add(panel);
    }
    public void writeFlightBookings()
    {
        {
            String input= getTransportMode()+','+getVehicleNo()+','+getAirlineType()+','
                    +getTicketType()+','+getCost()+','+getDepartureDate()+','+getDepartureTime()+','+getDepartureLocation()+
                    ','+getArrivalDate()+','+getArrivalTime()+','+getArrivalLocation();
            System.out.println(getCost());
            try(FileWriter File = new FileWriter("TourBooking.txt",true))
            {
                File.write(input+'\n');
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}


