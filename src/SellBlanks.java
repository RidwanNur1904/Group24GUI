import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
                PaymentTypeBox.addItem("PAY LATER");
                noCheckBox.setSelected(false);
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
        ActionListener calculateTotalBefore = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedISOCode = (String) CurrencyBox.getSelectedItem();
                String selectedLocation = (String) ToBox.getSelectedItem();
                try {
                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to fetch the ToOneUSD value associated with the selected ISO code
                    ResultSet resultSet1 = statement.executeQuery("SELECT ToOneUSD FROM currencies WHERE ISOcode = '" + selectedISOCode + "'");

                    // Get the ToOneUSD value from the result set
                    double toOneUSD = 0;
                    if (resultSet1.next()) {
                        toOneUSD = resultSet1.getDouble("ToOneUSD");
                    }

                    // Execute a query to fetch the Price value associated with the selected location on ToBox
                    ResultSet resultSet2 = statement.executeQuery("SELECT Price FROM Locations WHERE Location = '" + selectedLocation + "'");

                    // Get the Price value from the result set
                    double price = 0;
                    if (resultSet2.next()) {
                        price = resultSet2.getDouble("Price");
                    }

                    // Close the result sets, statement, and connection
                    resultSet1.close();
                    resultSet2.close();
                    statement.close();
                    connection.close();

                    // Calculate the value (ToOneUSD * Price) and update the TotalBeforeField
                    double totalBefore = toOneUSD * price;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    TotalBeforeField.setText(decimalFormat.format(totalBefore));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        // Add ActionListener to CurrencyBox and ToBox to update TotalBeforeField with the calculated value
        CurrencyBox.addActionListener(calculateTotalBefore);
        ToBox.addActionListener(calculateTotalBefore);



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

    }
}
