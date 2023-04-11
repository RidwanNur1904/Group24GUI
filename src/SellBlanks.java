import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;

public class SellBlanks extends JFrame {
    private JPanel SBframe;
    private JLabel TravelAdvisorImage;
    private JLabel TravelAdvisorName;
    private JButton sellBlankButton;
    private JButton exitButton;
    private JTextField EmailField;
    private JComboBox ToBox;
    private JComboBox FromBox;
    private JCheckBox interlineCheckBox;
    private JCheckBox domesticCheckBox;
    private JComboBox PaymentTypeBox;
    private JComboBox CurrencyBox;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JComboBox TAlist;
    private JTextField FirstName;
    private JTextField LastName;
    private JComboBox BlankBox;
    private JTextField TaxField;
    private JTextField TotalBeforeField;
    private JTextField FTfield;
    private JTextField Date;
    private JTextField OtherTaxField;

    SellBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,800);
        this.setTitle("Selling Blanks");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(SBframe);

        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);

        // Fetch data from MySQL and populate TAlist JComboBox
        try {
            // Establish a MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to fetch data from allocatedBlanks table
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT Username FROM allocatedBlanks");

            // Populate the TAlist JComboBox with the fetched data
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                TAlist.addItem(username);
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add ActionListener to interlineCheckBox
        interlineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deselect domesticCheckBox if interlineCheckBox is selected
                if (interlineCheckBox.isSelected()) {
                    domesticCheckBox.setSelected(false);
                }
            }
        });

        // Add ActionListener to domesticCheckBox
        domesticCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deselect interlineCheckBox if domesticCheckBox is selected
                if (domesticCheckBox.isSelected()) {
                    interlineCheckBox.setSelected(false);
                }
            }
        });

        // Add ItemListener to yesCheckBox
        yesCheckBox.addItemListener(e -> {
            if (yesCheckBox.isSelected()) {
                boolean hasPayLater = false;
                // Check if "PAY LATER" option already exists in PaymentTypeBox
                for (int i = 0; i < PaymentTypeBox.getItemCount(); i++) {
                    if (PaymentTypeBox.getItemAt(i).equals("PAY LATER")) {
                        hasPayLater = true;
                        break;
                    }
                }
                if (!hasPayLater) {
                    PaymentTypeBox.addItem("PAY LATER");
                }
                noCheckBox.setSelected(false);
            } else {
                // Remove "PAY LATER" option from PaymentTypeBox
                PaymentTypeBox.removeItem("PAY LATER");
            }
        });


        // Add ItemListener to noCheckBox
        noCheckBox.addItemListener(e -> {
            if (noCheckBox.isSelected()) {
                PaymentTypeBox.removeItem("PAY LATER");
                yesCheckBox.setSelected(false);
            }
        });

        // Add ActionListener to TAlist
        TAlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = TAlist.getSelectedItem().toString(); // Get the selected Username from TAlist

                // Fetch data from MySQL and populate BlankBox based on the selected Username
                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch data from allocatedBlanks table based on the selected Username
                    ResultSet resultSet = statement.executeQuery("SELECT BlankID FROM allocatedBlanks WHERE Username = '" + selectedUsername + "'");

                    // Clear existing items in BlankBox
                    BlankBox.removeAllItems();

                    // Populate BlankBox with the fetched BlankIDs
                    while (resultSet.next()) {
                        String blankID = resultSet.getString("BlankID");
                        BlankBox.addItem(blankID);
                    }

                    // Close the result set, statement, and connection
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Fetch data from MySQL and populate FromBox with the fetched Location values
        try {
            // Establish a MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to fetch data from Locations table
            ResultSet resultSet = statement.executeQuery("SELECT Location FROM Locations");

            // Create a DefaultComboBoxModel to store the fetched Location values
            DefaultComboBoxModel<String> fromBoxModel = new DefaultComboBoxModel<>();

            // Populate the DefaultComboBoxModel with the fetched Location values
            while (resultSet.next()) {
                String location = resultSet.getString("Location");
                fromBoxModel.addElement(location);
            }

            // Set the DefaultComboBoxModel as the model for FromBox
            FromBox.setModel(fromBoxModel);

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch data from MySQL and populate ToBox JComboBox
        try {
            // Establish a MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to fetch data from Locations table
            ResultSet resultSet = statement.executeQuery("SELECT Location FROM Locations");

            // Populate the ToBox JComboBox with the fetched Location values, excluding the selected value in FromBox
            String selectedFromLocation = (String) FromBox.getSelectedItem(); // Get the selected item from FromBox
            while (resultSet.next()) {
                String location = resultSet.getString("Location");
                // Add the location to ToBox only if it's not equal to the selected value in FromBox
                if (!location.equals(selectedFromLocation)) {
                    ToBox.addItem(location);
                }
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add an ActionListener to FromBox to listen for selection changes
        FromBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch data from Locations table
                    ResultSet resultSet = statement.executeQuery("SELECT Location FROM Locations");

                    // Clear the ToBox JComboBox
                    ToBox.removeAllItems();

                    // Get the selected item from FromBox
                    String selectedFromLocation = (String) FromBox.getSelectedItem();

                    // Populate the ToBox JComboBox with the fetched Location values, excluding the selected value in FromBox
                    while (resultSet.next()) {
                        String location = resultSet.getString("Location");
                        // Add the location to ToBox only if it's not equal to the selected value in FromBox
                        if (!location.equals(selectedFromLocation)) {
                            ToBox.addItem(location);
                        }
                    }

                    // Close the result set, statement, and connection
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Fetch data from MySQL and populate CurrencyBox with ISO codes from currencies table
        try {
            // Establish a MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to fetch data from currencies table
            ResultSet resultSet = statement.executeQuery("SELECT ISOcode FROM currencies");

            // Populate CurrencyBox with the fetched ISO codes
            while (resultSet.next()) {
                String isoCode = resultSet.getString("ISOcode");
                CurrencyBox.addItem(isoCode);
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TotalBeforeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Add ActionListener to interlineCheckBox
        interlineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deselect domesticCheckBox if interlineCheckBox is selected
                if (interlineCheckBox.isSelected()) {
                    domesticCheckBox.setSelected(false);

                    String selectedUsername = TAlist.getSelectedItem().toString(); // Get the selected Username from TAlist

                    // Fetch data from MySQL and populate BlankBox based on the selected Username and BlankID prefixes
                    try {
                        // Establish a MySQL connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Create a statement
                        Statement statement = connection.createStatement();

                        // Execute a query to fetch data from allocatedBlanks table based on the selected Username and BlankID prefixes
                        ResultSet resultSet = statement.executeQuery("SELECT BlankID FROM allocatedBlanks WHERE Username = '" + selectedUsername + "' AND (BlankID LIKE '444%' OR BlankID LIKE '440%' OR BlankID LIKE '420%')");

                        // Clear existing items in BlankBox
                        BlankBox.removeAllItems();

                        // Populate BlankBox with the fetched BlankIDs
                        while (resultSet.next()) {
                            String blankID = resultSet.getString("BlankID");
                            BlankBox.addItem(blankID);
                        }

                        // Close the result set, statement, and connection
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Fetch data from MySQL and populate ToBox with Location values where Domestic is false
                    try {
                        // Establish a MySQL connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Create a statement
                        Statement statement = connection.createStatement();

                        // Execute a query to fetch Location values where Domestic is false
                        ResultSet resultSet = statement.executeQuery("SELECT Location FROM Locations WHERE Domestic = false");

                        // Clear existing items in ToBox
                        ToBox.removeAllItems();

                        // Populate ToBox with the fetched Location values
                        while (resultSet.next()) {
                            String location = resultSet.getString("Location");
                            ToBox.addItem(location);
                        }

                        // Close the result set, statement, and connection
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Add ActionListener to CurrencyBox and ToBox to update TotalBeforeField with the calculated value
        // Add ActionListener to CurrencyBox and ToBox to update TotalBeforeField with the calculated value
        ActionListener calculateTotalBefore = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLocation = (String) ToBox.getSelectedItem();
                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch the Price value associated with the selected location on ToBox
                    ResultSet resultSet = statement.executeQuery("SELECT Price FROM Locations WHERE Location = '" + selectedLocation + "'");

                    // Get the Price value from the result set
                    double price = 0;
                    if (resultSet.next()) {
                        price = resultSet.getDouble("Price");
                    }

                    // Close the result set, statement, and connection
                    resultSet.close();
                    statement.close();
                    connection.close();

                    // Update the TotalBeforeField with the Price value
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    TotalBeforeField.setText(decimalFormat.format(price));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };


        // Add ActionListener to CurrencyBox and ToBox to update TotalBeforeField with the calculated value
        CurrencyBox.addActionListener(calculateTotalBefore);
        ToBox.addActionListener(calculateTotalBefore);

        // Add ActionListener to TaxField
        OtherTaxField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected ISO code from CurrencyBox
                String selectedISO = (String) CurrencyBox.getSelectedItem();

                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch FromOneUSD from currencies table based on the selected ISO code
                    ResultSet resultSet = statement.executeQuery("SELECT FromOneUSD FROM currencies WHERE ISOcode='" + selectedISO + "'");

                    // Get the FromOneUSD value from the result set
                    double fromOneUSD = 0.0;
                    if (resultSet.next()) {
                        fromOneUSD = resultSet.getDouble("FromOneUSD");
                    }

                    // Close the result set and statement
                    resultSet.close();
                    statement.close();

                    // Get the values of TaxField and TotalBeforeField
                    double tax = Double.parseDouble(TaxField.getText());
                    double othertax = Double.parseDouble(OtherTaxField.getText());
                    double totalBefore = Double.parseDouble(TotalBeforeField.getText());

                    // Add the values of TaxField and TotalBeforeField
                    double totalPrice = tax + totalBefore + othertax;

                    // Multiply the total price by the FromOneUSD value
                    double totalPriceInUSD = totalPrice * fromOneUSD;

                    // Round the total price to 2 decimal places
                    DecimalFormat df = new DecimalFormat("#.##");
                    double roundedPrice = Double.parseDouble(df.format(totalPriceInUSD));

                    // Set the text of FTfield to the total price in USD
                    FTfield.setText(Double.toString(roundedPrice));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        TaxField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected ISO code from CurrencyBox
                String selectedISO = (String) CurrencyBox.getSelectedItem();

                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch FromOneUSD from currencies table based on the selected ISO code
                    ResultSet resultSet = statement.executeQuery("SELECT FromOneUSD FROM currencies WHERE ISOcode='" + selectedISO + "'");

                    // Get the FromOneUSD value from the result set
                    double fromOneUSD = 0.0;
                    if (resultSet.next()) {
                        fromOneUSD = resultSet.getDouble("FromOneUSD");
                    }

                    // Close the result set and statement
                    resultSet.close();
                    statement.close();

                    // Get the values of TaxField and TotalBeforeField
                    double tax = Double.parseDouble(TaxField.getText());
                    double othertax = Double.parseDouble(OtherTaxField.getText());
                    double totalBefore = Double.parseDouble(TotalBeforeField.getText());

                    // Add the values of TaxField and TotalBeforeField
                    double totalPrice = tax + totalBefore + othertax;

                    // Multiply the total price by the FromOneUSD value
                    double totalPriceInUSD = totalPrice * fromOneUSD;

                    // Round the total price to 2 decimal places
                    DecimalFormat df = new DecimalFormat("#.##");
                    double roundedPrice = Double.parseDouble(df.format(totalPriceInUSD));

                    // Set the text of FTfield to the total price in USD
                    FTfield.setText(Double.toString(roundedPrice));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add ActionListener to domesticCheckBox
        domesticCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deselect interlineCheckBox if domesticCheckBox is selected
                if (domesticCheckBox.isSelected()) {
                    interlineCheckBox.setSelected(false);

                    String selectedUsername = TAlist.getSelectedItem().toString(); // Get the selected Username from TAlist

                    // Fetch data from MySQL and populate BlankBox based on the selected Username and BlankID prefixes
                    try {
                        // Establish a MySQL connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Create a statement
                        Statement statement = connection.createStatement();

                        // Execute a query to fetch data from allocatedBlanks table based on the selected Username and BlankID prefixes
                        ResultSet resultSet = statement.executeQuery("SELECT BlankID FROM allocatedBlanks WHERE Username = '" + selectedUsername + "' AND (BlankID LIKE '201%' OR BlankID LIKE '101%')");

                        // Clear existing items in BlankBox
                        BlankBox.removeAllItems();

                        // Populate BlankBox with the fetched BlankIDs
                        while (resultSet.next()) {
                            String blankID = resultSet.getString("BlankID");
                            BlankBox.addItem(blankID);
                        }

                        // Close the result set, statement, and connection
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Fetch data from MySQL and populate ToBox with Location values where Domestic is true
                    try {
                        // Establish a MySQL connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Create a statement
                        Statement statement = connection.createStatement();

                        // Execute a query to fetch Location values where Domestic is true
                        ResultSet resultSet = statement.executeQuery("SELECT Location FROM Locations WHERE Domestic = true");

                        // Clear existing items in ToBox
                        ToBox.removeAllItems();

                        // Populate ToBox with the fetched Location values
                        while (resultSet.next()) {
                            String location = resultSet.getString("Location");
                            ToBox.addItem(location);
                        }

                        // Close the result set, statement, and connection
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        sellBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = EmailField.getText();
                String to = (String) ToBox.getSelectedItem();
                String from = (String) FromBox.getSelectedItem();
                boolean isInterline = interlineCheckBox.isSelected();
                boolean isDomestic = domesticCheckBox.isSelected();
                boolean yescheckbox = yesCheckBox.isSelected();
                String paymentType = (String) PaymentTypeBox.getSelectedItem();
                String currency = (String) CurrencyBox.getSelectedItem();
                String taName = (String) TAlist.getSelectedItem();
                String firstName = FirstName.getText();
                String lastName = LastName.getText();
                String blankType = (String) BlankBox.getSelectedItem();
                String date = Date.getText();
                double ft = Double.parseDouble(FTfield.getText());

                // Perform database insertion
                try {
                    // Connect to the database
                    Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Check if yescheckbox is selected and email, firstName, and lastName match in ValuedCustomers table
                    if (yescheckbox) {
                        // Prepare the SQL query to check if email, firstName, and lastName match in ValuedCustomers table
                        String query = "SELECT COUNT(*) FROM ValuedCustomers WHERE Email = ? AND FirstName = ? AND LastName = ?";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, email);
                        stmt.setString(2, firstName);
                        stmt.setString(3, lastName);
                        ResultSet result = stmt.executeQuery();
                        result.next();
                        int matchCount = result.getInt(1);
                        stmt.close();

                        if (matchCount == 0) {
                            // Display error message if email, firstName, or lastName do not match in ValuedCustomers table
                            JOptionPane.showMessageDialog(SBframe, "Email, First Name, or Last Name do not match any records in the ValuedCustomers table.");
                            conn.close();
                            return;
                        }
                    }

                    // Prepare the SQL query with parameters
                    String insertQuery = "INSERT INTO SoldBlanks (username, BlankID, customerFirst, customerLast, locationFrom, locationTo, paymentMethod, Total, Email, Date, paidStatus) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setString(1, taName);
                    insertStmt.setString(2, blankType);
                    insertStmt.setString(3, firstName);
                    insertStmt.setString(4, lastName);
                    insertStmt.setString(5, from);
                    insertStmt.setString(6, to);
                    insertStmt.setString(7, paymentType);
                    insertStmt.setDouble(8, ft);
                    insertStmt.setString(9, email);
                    insertStmt.setString(10, date);

                    // Set paidStatus based on paymentType
                    // Set paidStatus based on paymentType
                    if (paymentType.equals("PAY LATER")) {
                        // Check if email already exists in OutstandingPayments table
                        String checkQuery = "SELECT * FROM OutstandingPayments WHERE Email=?";
                        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                        checkStmt.setString(1, email);
                        ResultSet checkResult = checkStmt.executeQuery();

                        if (checkResult.next()) {
                            // Email already exists, show error message
                            JOptionPane.showMessageDialog(SBframe, "Error: Email already exists in OutstandingPayments table.");
                            checkStmt.close();
                            conn.close();
                            return;
                        } else {
                            insertStmt.setString(11, "No");

                            // Insert values for email and ft into OutstandingPayments table
                            String updateQuery = "INSERT INTO OutstandingPayments (Email, Total) VALUES (?, ?)";
                            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                            updateStmt.setString(1, email);
                            updateStmt.setDouble(2, ft);
                            updateStmt.executeUpdate();
                            updateStmt.close();
                        }
                    } else if (paymentType.equals("CARD")) {
                        // Show editable pop-up for inserting credit card information
                        JPanel panel = new JPanel(new GridLayout(4, 2));
                        JTextField firstNameField = new JTextField();
                        JTextField lastNameField = new JTextField();
                        JTextField cardNumberField = new JTextField();
                        JComboBox<String> cardTypeComboBox = new JComboBox<>(new String[]{"Visa", "MasterCard", "American Express"});

                        panel.add(new JLabel("First Name:"));
                        panel.add(firstNameField);
                        panel.add(new JLabel("Last Name:"));
                        panel.add(lastNameField);
                        panel.add(new JLabel("Card Number:"));
                        panel.add(cardNumberField);
                        panel.add(new JLabel("Card Type:"));
                        panel.add(cardTypeComboBox);

                        int result = JOptionPane.showConfirmDialog(SBframe, panel, "Enter Credit Card Information", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String firstName1 = firstNameField.getText();
                            String lastName1 = lastNameField.getText();
                            String cardNumber = cardNumberField.getText();
                            String cardType = (String) cardTypeComboBox.getSelectedItem();

                            // Validate card number
                            if (cardNumber.length() != 16) {
                                JOptionPane.showMessageDialog(SBframe, "Error: Card number must be 16 digits long.");
                                return;
                            }

                            // Set parameters in the prepared statement
                            insertStmt.setString(11, "Yes");
                        } else {
                            // User clicked cancel, do nothing and cancel the query
                            insertStmt.close();
                            conn.close();
                            return;
                        }
                    } else {
                        insertStmt.setString(11, "Yes");
                    }

                    // Execute the query
                    insertStmt.executeUpdate();

                    // Close the database connection
                    insertStmt.close();
                    conn.close();

                    // Display success message
                    JOptionPane.showMessageDialog(SBframe, "Data inserted into SoldBlanks table successfully!");
                    // Perform database insertion for CommissionEarned
                    try {
                        // Connect to the database
                        Connection commissionConn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Fetch the commission rate from CommissionRates table based on the Username
                        String commissionRateQuery = "SELECT Rates FROM CommissionRates WHERE Username = ?";
                        PreparedStatement commissionRateStmt = commissionConn.prepareStatement(commissionRateQuery);
                        commissionRateStmt.setString(1, taName);
                        ResultSet commissionRateRs = commissionRateStmt.executeQuery();

                        // Get the commission rate from the result set
                        double commissionRate = 0.0;
                        if (commissionRateRs.next()) {
                            commissionRate = commissionRateRs.getDouble("Rates");
                        }
                        commissionRateRs.close();
                        commissionRateStmt.close();

                        // Calculate the commission amount
                        double commissionAmount = ft * (commissionRate / 100.0); // Multiply ft with commission rate (converted from percentage)

                        // Prepare the SQL query to insert values into CommissionEarned table
                        String commissionInsertQuery = "INSERT INTO CommissionEarned (Username, BlankID, Commission, Date) VALUES (?, ?, ?, ?)";
                        PreparedStatement commissionInsertStmt = commissionConn.prepareStatement(commissionInsertQuery);
                        commissionInsertStmt.setString(1, taName);
                        commissionInsertStmt.setString(2, blankType);
                        commissionInsertStmt.setDouble(3, commissionAmount);
                        commissionInsertStmt.setString(4, date); // Set the date provided by the user
                        commissionInsertStmt.executeUpdate();
                        commissionInsertStmt.close();
                        commissionConn.close();

                        // Connect to the database to remove BlankID from BlankStock
                        Connection removeConn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Prepare the SQL query to remove the BlankID from BlankStock table
                        String removeQuery = "DELETE FROM BlankStock WHERE BlankID = ?";
                        PreparedStatement removeStmt = removeConn.prepareStatement(removeQuery);
                        removeStmt.setString(1, blankType);
                        removeStmt.executeUpdate();
                        removeStmt.close();
                        removeConn.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(SBframe, "Error inserting values into CommissionEarned table: " + ex.getMessage());
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SBframe, "Failed to insert data into SoldBlanks table: " + ex.getMessage());
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TravelAdvisorOptions travelAdvisorOptions = new TravelAdvisorOptions();
                travelAdvisorOptions.setVisible(true);
                dispose();
            }
        });
    }
}
