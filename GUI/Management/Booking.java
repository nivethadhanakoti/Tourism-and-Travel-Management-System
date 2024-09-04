package Management;
import java.util.*;
import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Booking 
{
    private Destination destination;
    private int adultCount, childCount;
    private char pkgType;
    private String tvlDate;
    private float totCost;
    private String phone;

    public Booking(String phone)
    {
        this.phone=phone;
    }
    public Booking(String phone,Destination destination, int adultCount, int childCount, char pkgType, String tvlDate) 
    {   
        this.phone=phone;
        this.destination = destination;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.pkgType = pkgType;
        this.tvlDate = tvlDate;
    }

    public String getPhone() 
    {
        return phone;
    }
    
    public Destination getDestination() 
    {
        return destination;
    }

    public int getAdultCount() 
    {
        return this.adultCount;
    }

    public int getChildCount() 
    {
        return this.childCount;
    }

    public char getPkgType() 
    {
        return this.pkgType;
    }

    public String getTvlDate() 
    {
        return tvlDate;
    }

    public float calculateTotalCost() 
    {
    	char pType = this.getPkgType();
    	//float totCost;
    	
    	if (pType == 'G')
    		this.totCost = (float) (destination.getGoldPkCost() * getAdultCount() + destination.getGoldPkCost() * 0.6 * getChildCount());
    	else if (pType == 'S')
    		this.totCost = (float) (destination.getSilverPkCost() * getAdultCount() + destination.getSilverPkCost() * 0.6 * getChildCount());
    	else
    		this.totCost = (float) (destination.getBronzePkCost() * getAdultCount() + destination.getBronzePkCost() * 0.6 * getChildCount());
    	int earlyBirdDisc = getDiscStatus();
    	if (earlyBirdDisc == 1)
    		this.totCost = totCost * (float)0.9; // 10% Early bird discount
    		
        return this.totCost;
    }

    public float getTotalCost() 
    {
        return totCost;
    }
    public void setTotalCost(float totCost)
    {
        totCost=calculateTotalCost();
    }
    
    public int getDiscStatus() 
    {
    	
    	SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
    	int retval = 0;
        // Try Block
        try 
        {
            LocalDate today = LocalDate.now();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date1 = today.format(dateTimeFormatter);
            String tDate = getTvlDate();
            
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(tDate);
            long difference_In_Time= d2.getTime() - d1.getTime();

            long difference_In_Seconds = (difference_In_Time/ 1000)% 60;
            long difference_In_Minutes= (difference_In_Time/ (1000 * 60))% 60;
            long difference_In_Hours= (difference_In_Time/ (1000 * 60 * 60))% 24;
            long difference_In_Years= (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
            long difference_In_Days= (difference_In_Time/ (1000 * 60 * 60 * 24)) % 365;
            {if (difference_In_Days > 30) // Eligible for Early bird discount
                retval=1;
             else return 0;
            }
        }
        // Catch the Exception
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        return retval;
    
    }

    public int getCancellationStatus(String phone,String tDate) 
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	int retval = 0;
        // Try Block
        try 
        {
            LocalDate today = LocalDate.now();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date1 = today.format(dateTimeFormatter);
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(tDate);

            long difference_In_Time = d2.getTime() - d1.getTime();

            long difference_In_Seconds = (difference_In_Time/ 1000)% 60;
            long difference_In_Minutes= (difference_In_Time/ (1000 * 60))% 60;
            long difference_In_Hours= (difference_In_Time/ (1000 * 60 * 60))% 24;
            long difference_In_Years= (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
            long difference_In_Days= (difference_In_Time/ (1000 * 60 * 60 * 24)) % 365;

            if (difference_In_Days>30)
                retval= 1;
            else if (difference_In_Days>15)
                retval=2;
            else if (difference_In_Days>10)
                retval= 3;
            else retval= 0;

         }
    
            catch (ParseException e) 
            {
                e.printStackTrace();
            }
            return retval;
    } 

    public int cancelBooking(String phone) 
    {
        try {
        String input = "";
            try {
            try (// input the file content to the String "input"
                BufferedReader file = new BufferedReader(new FileReader("TourBooking.txt"))) {
                    String line;
                    //System.out.println("Line: "+line);
                    while ((line = file.readLine()) != null)
                    {   //System.out.println("Line: "+line);
                        if (line.contains(phone))
                            continue;
                        else
                            input += line + '\n';
                        } // end while
                }
            } // end inner try
            catch (FileNotFoundException e1) {
            return 0;
            }// end inner catch

            try (// write the new String with the replaced line OVER the same file
            FileOutputStream File = new FileOutputStream("TourBooking.txt")) {
                File.write(input.getBytes());
            }

        } //end outer try
        catch (Exception e) {
            System.out.println("Problem reading file.");
        } // end outer catch
        return 1;
    }
} 

