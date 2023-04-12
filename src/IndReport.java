import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IndReport extends JFrame {
    private JPanel IndPanel;
    private JLabel TravelAdvisorImage;
    private JButton Exit;
    private JButton createReportButton;
    private JComboBox TAlist;
    private JTextField Date;

    IndReport(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Individual Reports");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(IndPanel);

        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        Exit.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

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

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = (String) TAlist.getSelectedItem(); // Get selected username from TAlist JComboBox
                String inputDate = Date.getText(); // Get input date from Date JTextField

                String password = JOptionPane.showInputDialog(null, "Please enter Travel Advisor password:", "Password Confirmation", JOptionPane.INFORMATION_MESSAGE);

                // Check if password is empty or null
                if (password == null || password.isEmpty()) {
                    // Show error pop-up for password validation
                    JOptionPane.showMessageDialog(null, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Return early if password is not provided
                }

                try {

                    // Validate input date format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    dateFormat.parse(inputDate);

                    // Establish a MySQL connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Fetch password from TravelAdvisor table for selected username
                    ResultSet passwordResultSet = statement.executeQuery("SELECT Password FROM TravelAdvisor WHERE Username = '" + selectedUsername + "'");
                    String correctPassword = "";
                    if (passwordResultSet.next()) {
                        correctPassword = passwordResultSet.getString("Password");
                    }

                    // Check if password matches
                    if (!password.equals(correctPassword)) {
                        // Show error pop-up for incorrect password
                        JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Return early if password is incorrect
                    }

                    // Fetch stockReceived value from allocatedBlanks table
                    ResultSet stockReceivedResultSet = statement.executeQuery("SELECT COUNT(BlankID) AS stockReceived FROM allocatedBlanks WHERE Username = '" + selectedUsername + "'");
                    int stockReceived = 0;
                    if (stockReceivedResultSet.next()) {
                        stockReceived = stockReceivedResultSet.getInt("stockReceived");
                    }

                    // Fetch stockSold value and customerFirst value from SoldBlanks table
                    ResultSet stockSoldResultSet = statement.executeQuery("SELECT COUNT(BlankID) AS stockSold, MIN(Email) AS bestClient FROM SoldBlanks WHERE Username = '" + selectedUsername + "'");
                    int stockSold = 0;
                    String bestClient = "";
                    if (stockSoldResultSet.next()) {
                        stockSold = stockSoldResultSet.getInt("stockSold");
                        bestClient = stockSoldResultSet.getString("bestClient");
                    }

                    // Fetch totalSales value from SoldBlanks table
                    ResultSet totalSalesResultSet = statement.executeQuery("SELECT SUM(Total) AS totalSales FROM SoldBlanks WHERE Username = '" + selectedUsername + "'");
                    BigDecimal totalSales = BigDecimal.ZERO;
                    if (totalSalesResultSet.next()) {
                        totalSales = totalSalesResultSet.getBigDecimal("totalSales");
                    }

                    // Fetch commissionEarned value from SoldBlanks table
                    ResultSet commissionEarnedResultSet = statement.executeQuery("SELECT SUM(Commission) AS commissionEarned FROM CommissionEarned WHERE Username = '" + selectedUsername + "'");
                    BigDecimal commissionEarned = BigDecimal.ZERO;
                    if (commissionEarnedResultSet.next()) {
                        commissionEarned = commissionEarnedResultSet.getBigDecimal("commissionEarned");
                    }

                    // Insert the fetched values into individualReports table
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO individualReports (Username, stockReceived, stockSold, bestClient, totalSales, commissionEarned, Date) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    insertStatement.setString(1, selectedUsername);
                    insertStatement.setInt(2, stockReceived);
                    insertStatement.setInt(3, stockSold);
                    insertStatement.setString(4, bestClient);
                    insertStatement.setBigDecimal(5, totalSales);
                    insertStatement.setBigDecimal(6, commissionEarned);
                    insertStatement.setString(7, inputDate);
                    insertStatement.executeUpdate();

                    // Close the result sets, statements, and connection
                    stockReceivedResultSet.close();
                    stockSoldResultSet.close();
                    totalSalesResultSet.close();
                    commissionEarnedResultSet.close();
                    insertStatement.close();
                    statement.close();
                    connection.close();

                    // Show success pop-up
                    JOptionPane.showMessageDialog(null, "Individual report created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (ParseException ex) {
                    // Show error pop-up for date format validation
                    JOptionPane.showMessageDialog(null, "Please enter a valid date in yyyy-MM-dd format.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    // Show error pop-up for SQL exception
                    JOptionPane.showMessageDialog(null, "Failed to create individual report. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }
        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TravelAdvisorOptions travelAdvisorOptions = new TravelAdvisorOptions();
                travelAdvisorOptions.setVisible(true);
            }
        });
    }
}
