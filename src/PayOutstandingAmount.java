import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
public class PayOutstandingAmount extends JFrame {
    private JPanel POAframe;
    private JLabel OfficeManager;
    private JLabel OfficeManagerImage;
    private JComboBox OutstandingList;
    private JButton makePaymentButton;
    private JButton exitButton;
    private JTextField AmountDue;

    PayOutstandingAmount(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(POAframe);

        // Add code to retrieve "Email" values from MySQL and populate the JComboBox
        try {
            // Connect to MySQL database
            String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace with your actual database URL
            String username = "in2018g24_a"; // Replace with your actual username
            String password = "GTrSnz41"; // Replace with your actual password
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "SELECT Email FROM OutstandingPayments"; // Replace with your actual table name
            ResultSet resultSet = statement.executeQuery(query);

            // Add the "Email" values from the ResultSet to the JComboBox
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String email = resultSet.getString("Email");
                comboBoxModel.addElement(email); // Use addElement() to add items to the DefaultComboBoxModel
            }

            // Set the JComboBox model to the populated model
            OutstandingList.setModel(comboBoxModel);

            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        exitButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
            }
        });

        // Add ActionListener to JComboBox OutstandingList
        OutstandingList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected email from the JComboBox
                String selectedEmail = (String) OutstandingList.getSelectedItem();

                // Query the database to fetch the total value associated with the selected email
                try {
                    // Connect to MySQL database
                    String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace with your actual database URL
                    String username = "in2018g24_a"; // Replace with your actual username
                    String password = "GTrSnz41"; // Replace with your actual password
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    String query = "SELECT Total FROM OutstandingPayments WHERE Email = '" + selectedEmail + "'"; // Replace with your actual table and column names
                    ResultSet resultSet = statement.executeQuery(query);

                    // Retrieve the total value from the ResultSet and set it as the text of the JTextField AmountDue
                    if (resultSet.next()) {
                        double total = resultSet.getDouble("Total");
                        AmountDue.setText(String.valueOf(total));
                    } else {
                        AmountDue.setText(""); // If no matching record found, clear the JTextField
                    }

                    // Close the statement and connection
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(7, 3)); // Update grid layout to accommodate 7 rows
                panel.add(new JLabel("First Name:"));
                JTextField firstNameField = new JTextField();
                firstNameField.setColumns(20);
                panel.add(firstNameField);
                panel.add(new JLabel(""));

                panel.add(new JLabel("Last Name:"));
                JTextField lastNameField = new JTextField();
                lastNameField.setColumns(20);
                panel.add(lastNameField);
                panel.add(new JLabel(""));

                panel.add(new JLabel("Email:")); // Add email field
                JTextField emailField = new JTextField();
                emailField.setColumns(20);
                panel.add(emailField);
                panel.add(new JLabel(""));

                panel.add(new JLabel("Card Number:"));
                JTextField cardNumberField = new JTextField();
                cardNumberField.setColumns(20);
                panel.add(cardNumberField);
                panel.add(new JLabel(""));

                panel.add(new JLabel("Total Due:"));
                JTextField totalDueField = new JTextField(AmountDue.getText());
                totalDueField.setColumns(20);
                panel.add(totalDueField);
                panel.add(new JLabel(""));

                panel.add(new JLabel("Card Type:"));
                JComboBox<String> cardTypeComboBox = new JComboBox<>(new String[]{"Visa", "MasterCard", "American Express"});
                panel.add(cardTypeComboBox);

                int result = JOptionPane.showConfirmDialog(null, panel, "Make Payment", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String cardNumber = cardNumberField.getText();
                    String totalDue = totalDueField.getText();
                    String cardType = (String) cardTypeComboBox.getSelectedItem();
                    String email = emailField.getText(); // Get email value

                    // Check if email matches with unpaid records in the MySQL table
                    boolean emailMatch = false;
                    try {
                        // Establish database connection and execute query
                        Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                        String query = "SELECT * FROM SoldBlanks WHERE Email = ? AND paidStatus = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, email);
                        statement.setString(2, "No");
                        ResultSet resultSet = statement.executeQuery();

                        // Check if result set has any records
                        if (resultSet.next()) {
                            emailMatch = true; // Email found with unpaid status

                            // Update paidStatus to "Yes"
                            String updateQuery = "UPDATE SoldBlanks SET paidStatus = ? WHERE Email = ?";
                            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                            updateStatement.setString(1, "Yes");
                            updateStatement.setString(2, email);
                            updateStatement.executeUpdate();
                            updateStatement.close();

                            // Remove matching records from OutstandingPayments table
                            String deleteQuery = "DELETE FROM OutstandingPayments WHERE Email = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                            deleteStatement.setString(1, email);
                            deleteStatement.executeUpdate();
                            deleteStatement.close();
                        }

                        // Close database connections
                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    // Display appropriate message based on email match status
                    if (emailMatch) {
                        JOptionPane.showMessageDialog(null, "Payment Successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Email does not match any unpaid records.");
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
            }
        });
    }
}
