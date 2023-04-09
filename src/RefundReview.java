import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;

public class RefundReview extends JFrame {

    private JPanel RRpanel;
    private JLabel OfficeManagerImage;
    private JComboBox SelectedRefund;
    private JButton exitButton;
    private JButton approveRefundButton;
    private JButton denyRefundButton;
    private JList RefundInfo;
    private DefaultListModel<String> listModel;

    RefundReview(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RRpanel);

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        // Create DefaultListModel for JList
        listModel = new DefaultListModel<>();
        RefundInfo.setModel(listModel);

        // Connect to MySQL database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
            Statement statement = connection.createStatement();

            // Retrieve distinct BlankID values from refundprocessing table
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT BlankID FROM refundprocessing");

            // Populate JComboBox with BlankID values
            while (resultSet.next()) {
                String blankID = resultSet.getString("BlankID");
                SelectedRefund.addItem(blankID);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SelectedRefund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected BlankID from the JComboBox
                String selectedBlankID = (String) SelectedRefund.getSelectedItem();

                // Connect to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    Statement statement = connection.createStatement();

                    // Retrieve values associated with the selected BlankID from refundprocessing table
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM refundprocessing WHERE BlankID='" + selectedBlankID + "'");

                    // Create a DefaultListModel to store the values
                    DefaultListModel<String> listModel = new DefaultListModel<>();

                    // Populate the DefaultListModel with the values
                    while (resultSet.next()) {
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

                        // Add the values to the list model
                        listModel.addElement("Username: " + username);
                        listModel.addElement("Customer First Name: " + customerFirst);
                        listModel.addElement("Customer Last Name: " + customerLast);
                        listModel.addElement("Location From: " + locationFrom);
                        listModel.addElement("Location To: " + locationTo);
                        listModel.addElement("Payment Method: " + paymentMethod);
                        listModel.addElement("Total: " + total);
                        listModel.addElement("Email: " + email);
                        listModel.addElement("Date: " + date);
                        listModel.addElement("Paid Status: " + paidStatus);
                        listModel.addElement(""); // Add an empty line for spacing
                    }

                    // Set the list model to the JList RefundInfo
                    RefundInfo.setModel(listModel);

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        approveRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected BlankID from the JComboBox
                String selectedBlankID = (String) SelectedRefund.getSelectedItem();

                // Connect to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    Statement statement = connection.createStatement();

                    // Delete values associated with the selected BlankID from refundprocessing table
                    int rowsAffected = statement.executeUpdate("DELETE FROM refundprocessing WHERE BlankID='" + selectedBlankID + "'");

                    if (rowsAffected > 0) {
                        // Show a success message
                        JOptionPane.showMessageDialog(null, "Refund approved.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the JList and JComboBox
                        listModel.clear();
                        SelectedRefund.setSelectedIndex(-1);
                    } else {
                        // Show an error message
                        JOptionPane.showMessageDialog(null, "Failed to approve refund.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        denyRefundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected BlankID from the JComboBox
                String selectedBlankID = (String) SelectedRefund.getSelectedItem();

                // Connect to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    Statement statement = connection.createStatement();

                    // Retrieve values associated with the selected BlankID from refundprocessing table
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM refundprocessing WHERE BlankID='" + selectedBlankID + "'");

                    // Insert the values into denied_refunds table
                    while (resultSet.next()) {
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

                        // Insert the values into denied_refunds table
                        String insertQuery = "INSERT INTO SoldBlanks (BlankID, username, customerFirst, customerLast, locationFrom, locationTo, paymentMethod, Total, Email, Date, paidStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, selectedBlankID);
                        preparedStatement.setString(2, username);
                        preparedStatement.setString(3, customerFirst);
                        preparedStatement.setString(4, customerLast);
                        preparedStatement.setString(5, locationFrom);
                        preparedStatement.setString(6, locationTo);
                        preparedStatement.setString(7, paymentMethod);
                        preparedStatement.setString(8, total);
                        preparedStatement.setString(9, email);
                        preparedStatement.setDate(10, date);
                        preparedStatement.setString(11, paidStatus);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }

                    // Delete values associated with the selected BlankID from refundprocessing table
                    int rowsAffected = statement.executeUpdate("DELETE FROM refundprocessing WHERE BlankID='" + selectedBlankID + "'");

                    if (rowsAffected > 0) {
                        // Show a success message
                        JOptionPane.showMessageDialog(null, "Refund denied.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the JList and JComboBox
                        listModel.clear();
                        SelectedRefund.setSelectedIndex(-1);
                    } else {
                        // Show an error message
                        JOptionPane.showMessageDialog(null, "Failed to deny refund.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
