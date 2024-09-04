TRAVEL AND TOURISM MANAGEMENT SYSTEM
The Travel and Tourism Management System is a software solution designed to streamline and automate various processes involved in the travel and tourism industry. This system facilitates the management of reservations, bookings, customer information, and other essential tasks to enhance the overall efficiency of travel-related businesses.

TABLE OF CONTENTS
Installation
Usage
Features
File Structure

INSTALLATION
Ensure you have Java installed on your system.
Download the java files in Management folder.
Download the text files in GUI folder.
Download the loginMain.java file which is also present in GUI folder.

Compile the Java files using the following command:(outside Management folder and inside GUI folder)
javac loginMain.java

Run the program using the following command:
java loginMain

USAGE
Upon running the program, you will initially be prompted with a dashboard to either SignUp, Login or Exit the application. It is advised to first sign up before logging in. After the login process, an option to either book or cancel a package is provided. According to the booking/cancellation choice given by the user, the corresponding process takes place. 

FEATURES

Travel and Tourism Management System:
Booking: The user is first required to choose a destination package and then enter the associated details following which, he/she will be directed to book a mode of transport for travel.
Cancellation: The user is initially provided with the cancellation(refund) policy and is then asked for confirmation to cancel the package. 

(Note:  Kindly make sure to enter inputs with the right alphabet cases...the program is case sensitive)

FILE STRUCTURE
GUI folder contains the java file loginMain.java, which contains the main method of the program. This folder also contains all the text files used.
Management folder is contained within the GUI folder. This Management folder contains all the other java files except for the file which has the main method. 
Java files contained in the Management folder: Booking.java, DataStorage.java, Destination.java, Flight.java, FlightReader.java, FlightBooking.java, Login.java, Signup.java, Train.java, TrainReader.java, TrainBooking.java, Transport.java, TravelManagementSystem.java

Files used: 
login.txt : To store the customers data and the information and check validity of userID and password while logging in. 
flights.txt: To store the information of various flights from Chennai, Cochin, Bangalore and Hyderabad to Orissa, Delhi and Kashmir. 
trains.txt : To store the information of various trains from Chennai, Cochin, Bangalore and Hyderabad to Orissa, Delhi and Kashmir. 
bookedFlights.txt: To store the flight seat numbers that are already booked. (format : flightno,seatNo)
bookedTrains.txt: To store the train seat numbers that are already booked. (format : trainno,seatNo)
TourBooking.txt : Maintains a record of all the bookings done so far by each of the registered users and gets updated in case of any new booking or cancellation of existing booking.  
Packages.csv: To store all the available destination packages and other related information like package cost  (Bronze, Silver and Gold) and itinerary for each destination package.