package Management;
import java.util.*;
import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TravelManagementSystem 
{
    public ArrayList<Destination> destinations;
    private ArrayList<Booking> bookings;

    public TravelManagementSystem() 
    {
        destinations = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addDestination(Destination destination) 
    {
        destinations.add(destination);
    }

    public String displayDestinations() 
    {
        StringBuilder result = new StringBuilder("Available Destinations:\n");
        for (Destination destination : destinations) {
            result.append(destination.getName()).append("\n");
        }
        return result.toString();
    }

    public String displayDestinationDetails(Destination destination) {
        StringBuilder result = new StringBuilder("Destination Package details:\n");
        result.append("Itinerary:  ").append(destination.getItinerary()).append("\n");
        result.append("Gold:      INR ").append(destination.getGoldPkCost()).append("\n");
        result.append("Silver:    INR ").append(destination.getSilverPkCost()).append("\n");
        result.append("Bronze:    INR ").append(destination.getBronzePkCost()).append("\n");
        return result.toString();
    }

    public void bookTravel(String phone, Destination destination, int adultCount, int childCount, char pkgType, String tvlDate) 
    {
        Booking booking = new Booking(phone, destination, adultCount, childCount, pkgType, tvlDate);
        bookings.add(booking);
        System.out.println("Booking successful!");
    }

    public void writeBookings()
    {   
        for (Booking booking : bookings)
        {
            String input = booking.getPhone() + ',' + booking.getDestination().getName() + ',' + booking.getPkgType() + ',' +
                    booking.getAdultCount() + ',' + booking.getChildCount() + ',' + booking.getTvlDate() + ',' + booking.calculateTotalCost() + ',';
            try (FileWriter file = new FileWriter("TourBooking.txt", true))
            {
                file.write(input);
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        } // end for
    } // end writeBookings

    public String displayBookings() 
    {
        StringBuilder result = new StringBuilder("\n          Bookings\n\n");
        for (Booking booking : bookings) {
            result.append("Destination  : ").append(booking.getDestination().getName()).append("\n");
            result.append("PackageType: ").append(booking.getPkgType()).append("\n");
            result.append("Adults          : ").append(booking.getAdultCount()).append("\n");
            result.append("Children       : ").append(booking.getChildCount()).append("\n");
            result.append("Travel Date  : ").append(booking.getTvlDate()).append("\n\n");
            if (booking.getDiscStatus() == 1) {
                result.append("Total Cost (Before Early Bird discount)      : INR ").append(booking.calculateTotalCost() / 0.95f).append("\n");
                result.append("Total Cost (After 10% Early Bird discount) : INR ").append(booking.calculateTotalCost()).append("\n");
            } else {
                result.append("Total Cost (No discount applicable !): INR ").append(booking.calculateTotalCost()).append("\n");
            }
        }
        return result.toString();
    }

    public Destination getDestinationByName(String name) {
        for (Destination destination : destinations) {
            if (destination.getName().equals(name)) {
                return destination;
            }
        }
        return null; // Return null if destination is not found
    }

    public String[] getDestinationNames() {
        String[] names = new String[destinations.size()];
        for (int i = 0; i < destinations.size(); i++) {
            names[i] = destinations.get(i).getName();
        }
        return names;
    }
}
