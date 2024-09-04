package Management;
import java.util.*;
import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Destination 
{
    private String name;
    private String itinerary;
    private float GoldPkcost;
    private float SilverPkcost;
    private float BronzePkcost;

    public Destination(String name, String itinerary, float GoldPkcost, float SilverPkcost, float BronzePkcost) 
    {
        this.name = name;
        this.itinerary = itinerary;
        this.GoldPkcost = GoldPkcost;
        this.SilverPkcost = SilverPkcost;
        this.BronzePkcost = BronzePkcost;
    }

    public String getName() 
    {
        return name;
    }

    public String getItinerary() 
    {
        return itinerary;
    }

    public float getGoldPkCost() 
    {
        return GoldPkcost;
    }
    
    public float getSilverPkCost() 
    {
        return SilverPkcost;
    }

    public float getBronzePkCost() 
    {
        return BronzePkcost;
    }

}