import Management.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PasswordMismatchException extends Exception {
    public PasswordMismatchException(String message) {
        super(message);
    }
}

class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}

class SeatAlreadyBookedException extends Exception {
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}

class LoginGUI extends JFrame 
{
    private CardLayout cardLayout;
    private JPanel cards;
    private JTextField userIDField; 
    private JPasswordField passwordField;

    public LoginGUI() 
    {
        setTitle("Travel Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        JPanel signupPanel = createSignupPanel();
        JPanel loginPanel = createLoginPanel();
        JPanel mainMenuPanel = createMainMenuPanel();

        cards.add(signupPanel, "Signup");
        cards.add(loginPanel, "Login");
        cards.add(mainMenuPanel, "MainMenu");

        cardLayout.show(cards, "MainMenu");

        add(cards, BorderLayout.CENTER);
    }

    private JPanel createSignupPanel() 
    {
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(); 

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(); 

        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                handleSignup(nameField.getText(), emailField.getText(), genderField.getText().charAt(0),
                        addressField.getText(), Long.parseLong(phoneField.getText()), 
                        passwordField.getPassword(), confirmPasswordField.getPassword());
            }
        });

        signupPanel.add(nameLabel);
        signupPanel.add(nameField);
        signupPanel.add(emailLabel);
        signupPanel.add(emailField);
        signupPanel.add(genderLabel);
        signupPanel.add(genderField);
        signupPanel.add(addressLabel);
        signupPanel.add(addressField);
        signupPanel.add(phoneLabel);
        signupPanel.add(phoneField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);
        signupPanel.add(confirmPasswordLabel);
        signupPanel.add(confirmPasswordField);
        signupPanel.add(signupButton);
        return signupPanel;
    }


    private JPanel createLoginPanel() 
    {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel userIDLabel = new JLabel("User ID:");
        userIDField = new JTextField(); 

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(); 

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                handleLogin();
            }
        });

        loginPanel.add(userIDLabel);
        loginPanel.add(userIDField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        return loginPanel;
    }

    private JPanel createMainMenuPanel() 
    {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new FlowLayout());

        JButton signupButton = new JButton("Signup");
        Dimension buttonSize = new Dimension(200, 100); 
        signupButton.setPreferredSize(buttonSize);
        signupButton.setMargin(new Insets(5, 5, 5, 5));
    
        signupButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                cardLayout.show(cards, "Signup");
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMargin(new Insets(5, 5, 5, 5));

        loginButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                cardLayout.show(cards, "Login");
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMargin(new Insets(5, 5, 5, 5));
        exitButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                handleExit();
            }
        });

        mainMenuPanel.add(signupButton);
        mainMenuPanel.add(loginButton);
        mainMenuPanel.add(exitButton);
        return mainMenuPanel;
    }

    private void handleSignup(String name, String email, char gender, String address, long phone, char[] password, char[] confirmPassword) 
    {
        String passwordStr = new String(password);
        String confirmPasswordStr = new String(confirmPassword);
        if (!passwordStr.equals(confirmPasswordStr)) 
        {
            JOptionPane.showMessageDialog(this, "Password and Confirm Password do not match. Try again", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Signup newUser = new Signup(name, email, gender, address, phone, passwordStr, confirmPasswordStr);
        String userData = newUser.getName() + "," + newUser.getEmail() + "," + newUser.getGender()
                + "," + newUser.getAddress() + "," + newUser.getPhone() + "," + newUser.getPassword()
                + "," + newUser.getConfirmPassword();

        if (!DataStorage.isUserRegistered(newUser.getPhone(), newUser.getPassword()))
        {
            DataStorage.saveUserData(userData);
            JOptionPane.showMessageDialog(this, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() 
            {
                public void run()
                {
                    new LoginGUI().setVisible(true);
                }
            });

        } 
        else 
            JOptionPane.showMessageDialog(this, "User with this phone number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleLogin() 
    {
        long enteredUserID = Long.parseLong(userIDField.getText());
        String enteredPassword = new String(passwordField.getPassword());

        if (!DataStorage.isUserRegistered(enteredUserID, enteredPassword)) 
            JOptionPane.showMessageDialog(this, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        else 
        {
            Login existingUser = new Login(enteredUserID, enteredPassword);
            if (existingUser.validLoginCheck(enteredUserID, enteredPassword)) 
            {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome.", "Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(new Runnable() {
                public void run() 
                {
                    new OptionsPageGUI(enteredUserID).setVisible(true);
                }
                });
                dispose();
            }
            else 
                JOptionPane.showMessageDialog(this, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleExit()
    {
        System.exit(0);
    }
}

class OptionsPageGUI extends JFrame 
{
    private long enteredUserID;

    public OptionsPageGUI(long enteredUserID) 
    {
        this.enteredUserID = enteredUserID;
        initializeComponents();
        setTitle("Options");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        new JTextArea();
    }

    private void initializeComponents() {
        JButton bookButton = new JButton("Book Travel");
        JButton cancellationButton = new JButton("Cancellation");
        JButton mainMenuButton = new JButton("Main Menu");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookTravel();
            }
        });

        cancellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancellation();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMainMenu();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.add(bookButton);
        mainPanel.add(cancellationButton);
        mainPanel.add(mainMenuButton);
        add(mainPanel);
    }

    private void handleBookTravel() {
        SwingUtilities.invokeLater(new Runnable() {
        public void run() 
        {
            new TravelBookingGUI(enteredUserID).setVisible(true);
        }
        });
    }

    private void handleCancellation() {
        SwingUtilities.invokeLater(() -> {
            new CancellationGUI(Long.toString(enteredUserID)).setVisible(true);
        });
    }

    private void handleMainMenu() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
        dispose();
    }
}

class TravelBookingGUI extends JFrame 
{
    private JComboBox<String> destinationComboBox;
    private JTextField adultCountField;
    private JTextField childCountField;
    private JTextField packageTypeField;
    private JComboBox<String> dateComboBox;
    private JTextArea outputTextArea;
    private TravelManagementSystem travelSystem;
    private JPanel destinationPanel;
    private JPanel bookingPanel;
    private long enteredUserID;
    
    public TravelBookingGUI(long enteredUserID) {
        this.enteredUserID = enteredUserID;
        initializeComponents();
        setTitle("Travel Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        destinationPanel = createDestinationPanel();
        bookingPanel = createBookingPanel();
        showDestinationPanel();
    }

    private void showDestinationPanel() {
        setContentPane(destinationPanel);
        validate();
    }

    private JPanel createDestinationPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel destinationLabel = new JLabel("Destination:");
        destinationComboBox = new JComboBox<>(getDestinationNames());

        JButton searchButton = new JButton("Search Itinerary");
        searchButton.addActionListener(e -> {
            displayItinerary((String) destinationComboBox.getSelectedItem());
        });

        panel.add(destinationLabel);
        panel.add(destinationComboBox);
        panel.add(new JLabel());
        panel.add(searchButton);

        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1));

        JPanel row1 = new JPanel(new GridLayout(1, 8));
        JLabel adultCountLabel = new JLabel("Adult Count:");
        adultCountField = new JTextField();
        row1.add(adultCountLabel);
        row1.add(adultCountField);
        panel.add(row1);

        JPanel row2 = new JPanel(new GridLayout(1, 8));
        JLabel childCountLabel = new JLabel("Child Count:");
        childCountField = new JTextField();
        row2.add(childCountLabel);
        row2.add(childCountField);
        panel.add(row2);

        JPanel row3 = new JPanel(new GridLayout(1, 8));
        JLabel packageTypeLabel = new JLabel("Package Type (G/S/B):");
        packageTypeField = new JTextField();
        row3.add(packageTypeLabel);
        row3.add(packageTypeField);
        panel.add(row3);

        JPanel row4 = new JPanel(new GridLayout(1, 8));
        JLabel travelDateLabel = new JLabel("Travel Date:");
        dateComboBox = new JComboBox<>(new String[]{"30-12-2023", "30-1-2024"});
        row4.add(travelDateLabel);
        row4.add(dateComboBox);
        panel.add(row4);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputTextArea.setPreferredSize(new Dimension(600, 200));  // Adjust the width and height as needed
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(scrollPane);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);

        JPanel row6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton bookButton = new JButton("Book Travel");
        bookButton.addActionListener(e -> handleBooking());
        row6.add(bookButton);
        panel.add(row6);

        return panel;
    }

    private void displayItinerary(String destinationName) {
        Destination selectedDestination = travelSystem.getDestinationByName(destinationName);
        if (selectedDestination != null) {
            outputTextArea.setText(selectedDestination.getItinerary());

            setContentPane(bookingPanel);
            validate();
        } 
        else 
            JOptionPane.showMessageDialog(this, "Invalid destination name", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void initializeComponents() 
    {
        travelSystem = new TravelManagementSystem();
        loadDestinations("Packages.csv");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2));

        JLabel destinationLabel = new JLabel("Destination:");
        destinationComboBox = new JComboBox<>(getDestinationNames());

        JLabel adultCountLabel = new JLabel("Adult Count:");
        adultCountField = new JTextField();

        JLabel childCountLabel = new JLabel("Child Count:");
        childCountField = new JTextField();

        JLabel packageTypeLabel = new JLabel("Package Type (G/S/B):");
        packageTypeField = new JTextField();

        JLabel travelDateLabel = new JLabel("Travel Date:");
        dateComboBox = new JComboBox<>(new String[]{"30-12-2023", "30-1-2024"});

        JButton bookButton = new JButton("Book Travel");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setPreferredSize(new Dimension(400, 200));
        mainPanel.add(destinationLabel);
        mainPanel.add(destinationComboBox);
        mainPanel.add(adultCountLabel);
        mainPanel.add(adultCountField);
        mainPanel.add(childCountLabel);
        mainPanel.add(childCountField);
        mainPanel.add(packageTypeLabel);
        mainPanel.add(packageTypeField);
        mainPanel.add(travelDateLabel);
        mainPanel.add(dateComboBox);
        mainPanel.add(bookButton);

        add(mainPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
    }

    private void handleBooking() {
        int adultCount;
        int childCount;

        try 
        {
            adultCount = Integer.parseInt(adultCountField.getText());
            childCount = Integer.parseInt(childCountField.getText());
        } 
        catch (NumberFormatException e) {
            outputTextArea.setText("Invalid input. Please enter numeric values for counts.");
            return;
        }

        char packageType = packageTypeField.getText().toUpperCase().charAt(0);
        String travelDate = (String) dateComboBox.getSelectedItem(); 
        String selectedDestinationName = (String) destinationComboBox.getSelectedItem();
        Destination selectedDestination = travelSystem.getDestinationByName(selectedDestinationName);

        if (selectedDestination != null) {
            outputTextArea.setText("Itinerary: " + selectedDestination.getItinerary());
            travelSystem.bookTravel(Long.toString(enteredUserID), selectedDestination, adultCount, childCount, packageType, travelDate);
            travelSystem.writeBookings();
            displayBookingsInNewPage();
        } 
        else 
            JOptionPane.showMessageDialog(this, "Invalid destination name", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void loadDestinations(String csvFilePath) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] values = line.split("@");
                String packname = values[0];
                String itinerary = values[1];
                int Goldpkg = Integer.parseInt(values[2]);
                int Silverpkg = Integer.parseInt(values[3]);
                int Bronzepkg = Integer.parseInt(values[4]);
                Destination destination = new Destination(packname, itinerary, Goldpkg, Silverpkg, Bronzepkg);
                travelSystem.addDestination(destination);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getDestinationNames() 
    {
        return travelSystem.getDestinationNames();
    }

    private void displayBookingsInNewPage() {
        JFrame bookingDetailsFrame = new JFrame("Booking Details");
        bookingDetailsFrame.setSize(500, 400);
        bookingDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the new frame

        JTextArea bookingDetailsTextArea = new JTextArea();
        bookingDetailsTextArea.setEditable(false);

        bookingDetailsTextArea.append("\n       Booking Details\n");
        bookingDetailsTextArea.append(travelSystem.displayBookings());

        bookingDetailsFrame.add(new JScrollPane(bookingDetailsTextArea), BorderLayout.CENTER);

        JButton proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() 
                {
                    public void run()
                    {
                        new TransportBookingGUI().setVisible(true);
                    }
                });
                bookingDetailsFrame.dispose();
            }
        });

        bookingDetailsFrame.add(proceedButton, BorderLayout.SOUTH);
        bookingDetailsFrame.setLocationRelativeTo(this);
        bookingDetailsFrame.setVisible(true);
    }
}

class TransportBookingGUI extends JFrame
{
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;
    private JComboBox<String> travelModeComboBox;
    private JComboBox<String> departureComboBox;
    private JComboBox<String> arrivalComboBox;
    private JComboBox<String> dateComboBox;
    private JButton searchButton;
    public TransportBookingGUI() 
    {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        mainPanel = new JPanel();
        initializeComponents();
        cardPanel.add(mainPanel, "main");

        add(cardPanel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String travelMode = (String) travelModeComboBox.getSelectedItem();
                String departure = (String) departureComboBox.getSelectedItem();
                String arrival = (String) arrivalComboBox.getSelectedItem();
                String date = (String) dateComboBox.getSelectedItem();
                showAvailableOptions(travelMode, departure, arrival, date);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Transport Booking");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void initializeComponents() 
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2));

        travelModeComboBox = new JComboBox<>(new String[]{"Flight", "Train"});
        departureComboBox = new JComboBox<>(new String[]{"Chennai", "Cochin", "Bangalore", "Hyderabad"});
        arrivalComboBox = new JComboBox<>(new String[]{"Orissa", "Kashmir", "Delhi"});
        dateComboBox = new JComboBox<>(new String[]{"30-12-2023", "30-1-2024"});
        searchButton = new JButton("Search");
        new JTextField();

        mainPanel.add(new JLabel("Travel Mode:"));
        mainPanel.add(travelModeComboBox);
        mainPanel.add(new JLabel("Departure Place:"));
        mainPanel.add(departureComboBox);
        mainPanel.add(new JLabel("Arrival Place:"));
        mainPanel.add(arrivalComboBox);
        mainPanel.add(new JLabel("Travel Date:"));
        mainPanel.add(dateComboBox);
        mainPanel.add(new JLabel());
        mainPanel.add(searchButton);
    }
    
    private void showAvailableOptions(String travelMode, String departure, String arrival, String date) 
    {
        JFrame optionsFrame = new JFrame("Available Options");
        optionsFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField vehicleNumberField = new JTextField(10);
        JButton confirmButton = new JButton("Confirm");

        inputPanel.add(new JLabel("Enter Flight/Train Number: "), BorderLayout.WEST);
        inputPanel.add(vehicleNumberField, BorderLayout.CENTER);
        inputPanel.add(confirmButton, BorderLayout.EAST);

        JTextArea optionsTextArea = new JTextArea();
        optionsTextArea.setEditable(false);
        optionsTextArea.setLineWrap(true);
        optionsTextArea.setWrapStyleWord(true);

        if ("Flight".equals(travelMode)) 
            FlightReader.displayAvailableFlights(optionsTextArea, departure, arrival, date);
        else if ("Train".equals(travelMode)) 
            TrainReader.displayAvailableTrains(optionsTextArea, departure, arrival, date);

        confirmButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFlightOrTrain = vehicleNumberField.getText();
                optionsFrame.dispose();
                openConfirmationPage(travelMode, selectedFlightOrTrain);
            }
        });
        optionsFrame.add(new JScrollPane(optionsTextArea), BorderLayout.CENTER);
        optionsFrame.add(inputPanel, BorderLayout.NORTH);

        optionsFrame.setSize(500, 400);
        optionsFrame.setLocationRelativeTo(this);
        optionsFrame.setVisible(true);
    }

    private String[] getFlightOrTrainDetails(String travelMode, String selectedFlightOrTrain) 
    {
        String fileName = (travelMode.equals("Flight")) ? "flights.txt" : "trains.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String number = data[0].trim();
                if (number.equals(selectedFlightOrTrain)) 
                    return data;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openConfirmationPage(String travelMode, String selectedFlightOrTrain) 
    {
        JFrame confirmationFrame = new JFrame("Confirmation Page");
        confirmationFrame.setLayout(new BorderLayout());

        JTextArea bookedSeatsTextArea = new JTextArea();
        bookedSeatsTextArea.setEditable(false);

        String[] details = getFlightOrTrainDetails(travelMode, selectedFlightOrTrain);
        String flightOrTrainNumber = details[0].trim();

        displayBookedSeats(bookedSeatsTextArea, flightOrTrainNumber);

        JTextField numSeatsField = new JTextField();

        JButton proceedButton = new JButton("Proceed");
        proceedButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSeats;
                try {
                    numSeats = Integer.parseInt(numSeatsField.getText().trim());
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input for the number of seats. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                int []selectedSeats=new int[numSeats];
                for (int i = 0; i < numSeats; i++) 
                {
                    int seatNumber = -1; 
                    while (true) 
                    {
                        try {
                            seatNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter seat number " + (i + 1) + ":"));
                            if (!isSeatAlreadyBooked(flightOrTrainNumber, seatNumber)) 
                                break; 
                            else 
                                JOptionPane.showMessageDialog(null, "Seat " + seatNumber + " is already booked for " + flightOrTrainNumber +
                                        "\nPlease choose a different seat number.", "Seat Booking Error", JOptionPane.ERROR_MESSAGE);
                        
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid seat number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    selectedSeats[i] = seatNumber;
                }

                try 
                {
                    updateBookedSeatsFile(flightOrTrainNumber, selectedSeats);
                    displayBookingDetails(details, numSeats, selectedSeats);
                    confirmationFrame.dispose();
                } 
                catch (SeatAlreadyBookedException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Seat Booking Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        confirmationFrame.add(new JScrollPane(bookedSeatsTextArea), BorderLayout.CENTER);

        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new GridLayout(3, 2));

        bookingPanel.add(new JLabel("Number of Seats:"));
        bookingPanel.add(numSeatsField);
        bookingPanel.add(new JLabel());
        bookingPanel.add(proceedButton);
        confirmationFrame.add(bookingPanel, BorderLayout.SOUTH);
        confirmationFrame.setSize(500, 400);
        confirmationFrame.setLocationRelativeTo(this);
        confirmationFrame.setVisible(true);
    }

    private void updateBookedSeatsFile(String flightOrTrainNumber, int []selectedSeats) throws SeatAlreadyBookedException 
    {
        String bookedFile = (flightOrTrainNumber.startsWith("F")) ? "bookedFlights.txt" : "bookedTrains.txt";
        String transportFile = (flightOrTrainNumber.startsWith("F")) ? "flights.txt" : "trains.txt";

        try (FileWriter fw = new FileWriter(bookedFile, true);
            BufferedReader br = new BufferedReader(new FileReader(transportFile))) 
        {
            for (int i=0; i<selectedSeats.length; i++) 
            {
                if (isSeatAlreadyBooked(flightOrTrainNumber, selectedSeats[i])) 
                    throw new SeatAlreadyBookedException("Seat " + selectedSeats[i] + " is already booked for " + flightOrTrainNumber);
            }

            String line;
            StringBuilder updatedFileContent = new StringBuilder();
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(",");
                String number = data[0].trim();

                if (number.equals(flightOrTrainNumber)) 
                {
                    int availableSeats = Integer.parseInt(data[6].trim());
                    availableSeats -= selectedSeats.length;
                    data[6] = String.valueOf(availableSeats);
                    line = String.join(",", data);
                }
                updatedFileContent.append(line).append("\n");
            }

            try (FileWriter fileWriter = new FileWriter(transportFile)) 
            {
                fileWriter.write(updatedFileContent.toString());
            }

            for (int i=0; i<selectedSeats.length; i++) 
                fw.write(flightOrTrainNumber + "," + selectedSeats[i] + "\n");
            
            for(int i=0; i<selectedSeats.length; i++)
                System.out.println("Updated booked seats file for " + flightOrTrainNumber + " with selected seats: " + selectedSeats[i]);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSeatAlreadyBooked(String flightOrTrainNumber, int seatNumber) 
    {
        String fileName = (flightOrTrainNumber.startsWith("F")) ? "bookedFlights.txt" : "bookedTrains.txt";
        if(fileName.equals("bookedFlights.txt"))
            return FlightBooking.isSeatAlreadyBooked(flightOrTrainNumber, seatNumber);
        else 
            return TrainBooking.isSeatAlreadyBooked(flightOrTrainNumber, seatNumber);
    }

    private void displayBookedSeats(JTextArea bookedSeatsTextArea, String flightOrTrainNumber) {
        String bookedFile = (flightOrTrainNumber.startsWith("F")) ? "bookedFlights.txt" : "bookedTrains.txt";
        if(bookedFile.equals("bookedFlights.txt"))  
            FlightBooking.displayBookedSeats(bookedSeatsTextArea, flightOrTrainNumber);
        else
            TrainBooking.displayBookedSeats(bookedSeatsTextArea, flightOrTrainNumber);
    }

    private void displayBookingDetails(String[] flightOrTrainDetails, int numSeats, int []selectedSeats) {
        Flight flight=null;
        Train train=null;
        JFrame detailsFrame = new JFrame("Booking Details");
        detailsFrame.setLayout(new GridLayout(11, 2));
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (flightOrTrainDetails != null && flightOrTrainDetails.length >= 11) 
        {
            String travelMode = flightOrTrainDetails[0].trim().startsWith("F") ? "Flight" : "Train";
            if(travelMode.equals("Flight"))
            {
                flight = readFlightFromFile("flights.txt", flightOrTrainDetails[0].trim(), selectedSeats);
                flight.display(detailsFrame);
                flight.writeFlightBookings();
            }
            else
            {
                train=readTrainFromFile("trains.txt", flightOrTrainDetails[0].trim(), selectedSeats);
                train.display(detailsFrame);
                train.writeTrainBookings();
            }
            JButton backButton = new JButton("Back to Main Menu");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    detailsFrame.dispose(); 
                    new LoginGUI().setVisible(true); 
                }
            });
            detailsFrame.add(backButton, BorderLayout.SOUTH);
            detailsFrame.setSize(500, 400);
            detailsFrame.setLocationRelativeTo(null);
            detailsFrame.setVisible(true);
        } 
        else 
            System.out.println("Invalid format for flight or train details.");
    }

    private static Flight readFlightFromFile(String filePath, String flightNumber, int[]seatNumber) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] flightData = line.split(",");
                String currentFlightNumber = flightData[0];

                if (currentFlightNumber.equals(flightNumber)) 
                {
                    Flight flight = new Flight(flightData[0],seatNumber,flightData[1],flightData[2],flightData[3],flightData[4],Float.parseFloat(flightData[5]),flightData[7],flightData[8],flightData[9],flightData[10]);
                    return flight;
                }
            }
        } 
        catch (IOException e){
            e.printStackTrace();
        }
        return null; 
    }

    private static Train readTrainFromFile(String filePath, String trainNumber, int[]seatNumber) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] trainData = line.split(",");
                String currentTrainNumber = trainData[0];

                if (currentTrainNumber.equals(trainNumber)) 
                {
                    Train train = new Train(trainData[0],seatNumber,trainData[1],trainData[2],trainData[3],trainData[4],Float.parseFloat(trainData[5]),trainData[7],trainData[8],trainData[9],trainData[10]);
                    return train;
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null; 
    }
}

class CancellationGUI extends JFrame 
{
    private JTextArea bookingTextArea;
    private JTextArea policyTextArea;
    private JTextField dateTextField;
    private JButton cancelBookingButton;
    private JList<String> bookingList;

    private String enteredUserID;

    public CancellationGUI(String enteredUserID) {
        this.enteredUserID = enteredUserID;
        setTitle("Cancellation GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        bookingTextArea = new JTextArea();
        bookingTextArea.setEditable(false);
        policyTextArea = new JTextArea();
        policyTextArea.setEditable(false);

        dateTextField = new JTextField(20);
        cancelBookingButton = new JButton("Cancel Booking");

        DefaultListModel<String> bookingListModel = new DefaultListModel<>();
        bookingList = new JList<>(bookingListModel);
        bookingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(new GridLayout(3, 1));

        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.add(new JLabel("Your Bookings:"), BorderLayout.NORTH);
        bookingPanel.add(new JScrollPane(bookingTextArea), BorderLayout.CENTER);
        add(bookingPanel);

        JPanel policyPanel = new JPanel(new BorderLayout());
        policyPanel.add(new JLabel("Cancellation Policy:"), BorderLayout.NORTH);
        policyPanel.add(new JScrollPane(policyTextArea), BorderLayout.CENTER);
        add(policyPanel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter Date to Cancel: "));
        inputPanel.add(dateTextField);
        inputPanel.add(cancelBookingButton);
        add(inputPanel);

        displayUserBookings();
        displayCancellationPolicyAndUserBookings();

        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancelBooking();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayUserBookings() 
    {
        boolean hasBookings = false;

        try (BufferedReader br1 = new BufferedReader(new FileReader("TourBooking.txt"))) {
            String line;

            while ((line = br1.readLine()) != null) {
                String[] values = line.split(",");
                if (enteredUserID.equals(values[0])) {
                    bookingTextArea.append(line + "\n");  
                    hasBookings = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!hasBookings) {
            bookingTextArea.append("You don't have any bookings. Cancellation policy not applicable.\n");
        }
    }


    private void displayCancellationPolicyAndUserBookings() 
    {
        policyTextArea.append("*** Cancellation policy:\n");
        policyTextArea.append("100 % refund if cancelled before 30 days from travel\n");
        policyTextArea.append("75 % refund if cancelled before 15 days from travel\n");
        policyTextArea.append("50 % refund if cancelled before 10 days from travel\n");
        policyTextArea.append("No refund if cancelled within 10 days from travel\n\n\n");

        DefaultListModel<String> bookingListModel = (DefaultListModel<String>) bookingList.getModel();
        try (BufferedReader br1 = new BufferedReader(new FileReader("TourBooking.txt"))) {
            String line;
            while ((line = br1.readLine()) != null) {
                String[] values = line.split(",");
                if (enteredUserID.equals(values[0])) 
                    bookingListModel.addElement(line);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleCancelBooking() 
    {
        String dateToCancel = dateTextField.getText().trim();
        float totcost = 0;  
        if (!dateToCancel.isEmpty()) {
            try (BufferedReader br1 = new BufferedReader(new FileReader("TourBooking.txt"))) {
                String line;
                while ((line = br1.readLine()) != null) {
                    String[] values = line.split(",");

                    if (enteredUserID.equals(values[0])) {
                        totcost = Float.parseFloat(values[6]) + Float.parseFloat(values[11]);
                        break;
                    }
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }

            if (!dateToCancel.isEmpty()) {
                Booking bookingToCancel = new Booking(enteredUserID);

                if (bookingToCancel.cancelBooking(enteredUserID) == 1) {
                    if (bookingToCancel.getCancellationStatus(enteredUserID, dateToCancel) == 1) {
                        JOptionPane.showMessageDialog(this,"Booking for date " + dateToCancel + " successfully cancelled.\nRefund amount(100%): INR " + totcost +
                                        " will be refunded in 10 days", "Cancellation Confirmation",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (bookingToCancel.getCancellationStatus(enteredUserID, dateToCancel) == 2) {
                        JOptionPane.showMessageDialog(this,"Booking for date " + dateToCancel + " successfully cancelled.\nRefund amount(75%): INR " + totcost * 0.75f +
                                        " will be refunded in 10 days","Cancellation Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else if (bookingToCancel.getCancellationStatus(enteredUserID, dateToCancel) == 3) {
                        JOptionPane.showMessageDialog(this, "Booking for date " + dateToCancel + " successfully cancelled.\nRefund amount(50%): INR " + totcost * 0.5f +
                                        " will be refunded in 10 days", "Cancellation Confirmation",JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, "Booking for date " + dateToCancel + " successfully cancelled.\nNo refund is applicable for this cancellation.",
                                "Cancellation Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    }

                    dateTextField.setText("");
                    bookingList.clearSelection();
                    dispose();
                    SwingUtilities.invokeLater(() -> new OptionsPageGUI(Long.parseLong(enteredUserID)).setVisible(true));
                } 
                else 
                    bookingTextArea.append("Failed to cancel booking for date " + dateToCancel + ".\n");
            } 
            else 
                bookingTextArea.append("Please enter a Date to cancel.\n");
        }
    }
}

public class loginMain
{
    public static void main(String []args)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run()
            {
                new LoginGUI().setVisible(true);
            }
        });
    }
}