package Management;
import java.util.*;
import java.io.*;

public class Transport 
{
    private String departureTime;
    private String departureDate;
    private String departureLocation;
    private String arrivalTime;
    private String arrivalDate;
    private String arrivalLocation;

    public Transport(String departureLocation, String arrivalLocation, String departureDate, String departureTime, String arrivalDate, String arrivalTime)
    {
        this.departureLocation=departureLocation;
        this.arrivalLocation=arrivalLocation;
        this.departureDate=departureDate;
        this.departureTime=departureTime;
        this.arrivalDate=arrivalDate;
        this.arrivalTime=arrivalTime;
    }
    public String getDepartureTime()
    {
        return departureTime;
    }
    public String getDepartureDate()
    {
        return departureDate;
    }
    public String getDepartureLocation()
    {
        return departureLocation;
    }
    public String getArrivalDate()
    {
        return arrivalDate;
    }
    public String getArrivalTime()
    {
        return arrivalTime;
    }
    public String getArrivalLocation()
    {
        return arrivalLocation;
    }
}
