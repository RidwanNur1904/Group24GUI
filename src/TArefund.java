import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class TArefund extends JFrame {

    private JPanel RefundPanel;
    private JLabel TravelAdvisorImage;
    private JComboBox TAlist;
    private JComboBox BlankList;
    private JButton initiateRefundButton;
    private JButton exitButton;

    TArefund(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RefundPanel);

        // Establish connection to MySQL database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24",
                    "in2018g24_a", "GTrSnz41"); // Replace with your actual database connection details

            // Query the SoldBlanks table to retrieve the username values
            String query = "SELECT username FROM SoldBlanks";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the TAlist JComboBox with retrieved username values
            ArrayList<String> usernameList = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernameList.add(username);
            }
            TAlist.setModel(new DefaultComboBoxModel<>(usernameList.toArray(new String[0])));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TAlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected username value
                String selectedUsername = (String) TAlist.getSelectedItem();

                // Establish connection to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24",
                            "in2018g24_a", "GTrSnz41"); // Replace with your actual database connection details

                    // Query the SoldBlanks table to retrieve the BlankID values for the selected username
                    String query = "SELECT BlankID FROM SoldBlanks WHERE username = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, selectedUsername);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Populate the BlankList JComboBox with retrieved BlankID values
                    ArrayList<String> blankIdList = new ArrayList<>();
                    while (resultSet.next()) {
                        String blankId = resultSet.getString("BlankID");
                        blankIdList.add(blankId);
                    }
                    BlankList.setModel(new DefaultComboBoxModel<>(blankIdList.toArray(new String[0])));

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        initiateRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected BlankID value
                String selectedBlankId = (String) BlankList.getSelectedItem();

                // Establish connection to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24",
                            "in2018g24_a", "GTrSnz41"); // Replace with your actual database connection details

                    // Query the SoldBlanks table to retrieve the data for the selected BlankID
                    String selectQuery = "SELECT * FROM SoldBlanks WHERE BlankID = ?";
                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setString(1, selectedBlankId);
                    ResultSet resultSet = selectStatement.executeQuery();

                    if (resultSet.next()) {
                        // Retrieve the values from the SoldBlanks table
                        String username = resultSet.getString("username");
                        String customerFirst = resultSet.getString("customerFirst");
                        String customerLast = resultSet.getString("customerLast");
                        String locationFrom = resultSet.getString("locationFrom");
                        String locationTo = resultSet.getString("locationTo");
                        String paymentMethod = resultSet.getString("paymentMethod");
                        String total = resultSet.getString("Total");
                        String email = resultSet.getString("Email");
                        Date date = resultSet.getDate("Date");
                        String paidStatus = resultSet.getString("paidStatus");

                        // Insert the retrieved data into the refundprocessing table
                        String insertQuery = "INSERT INTO refundprocessing (username, BlankID, customerFirst, customerLast, locationFrom, locationTo, paymentMethod, Total, Email, Date, paidStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setString(1, username);
                        insertStatement.setString(2, selectedBlankId);
                        insertStatement.setString(3, customerFirst);
                        insertStatement.setString(4, customerLast);
                        insertStatement.setString(5, locationFrom);
                        insertStatement.setString(6, locationTo);
                        insertStatement.setString(7, paymentMethod);
                        insertStatement.setString(8, total);
                        insertStatement.setString(9, email);
                        insertStatement.setDate(10, date);
                        insertStatement.setString(11, paidStatus);
                        insertStatement.executeUpdate();

                        // Remove the selected BlankID from the SoldBlanks table
                        String deleteQuery = "DELETE FROM SoldBlanks WHERE BlankID = ?";
                        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                        deleteStatement.setString(1, selectedBlankId);
                        deleteStatement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "The refund process has now started.");

                        deleteStatement.close();
                        insertStatement.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "No data found for selected BlankID in SoldBlanks.");
                    }

                    resultSet.close();
                    selectStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TravelAdvisorOptions travelAdvisorOptions = new TravelAdvisorOptions();
                travelAdvisorOptions.setVisible(true);
            }
        });

        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);
    }
}
