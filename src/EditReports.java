import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditReports extends JFrame {

    private JPanel EditReportForm;
    private JLabel AdminImage;
    private JComboBox TAlist;
    private JComboBox ReportDate;
    private JButton editReportButton;
    private JButton exitButton;

    EditReports(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Blank Manager");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(EditReportForm);

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        // Fetch data from MySQL table and populate TAlist JComboBox
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Username FROM individualReports");
            while(rs.next()) {
                String username = rs.getString("Username");
                TAlist.addItem(username);
            }
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        TAlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = TAlist.getSelectedItem().toString(); // Get the selected username from TAlist
                ReportDate.removeAllItems(); // Remove all existing items from ReportDate JComboBox
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT Date FROM individualReports WHERE Username = ?");
                    stmt.setString(1, selectedUsername); // Set the selected username as a parameter
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()) {
                        String date = rs.getString("Date");
                        ReportDate.addItem(date); // Add dates associated with the selected username to ReportDate JComboBox
                    }
                    conn.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = TAlist.getSelectedItem().toString(); // Get the selected username from TAlist
                String selectedDate = ReportDate.getSelectedItem().toString(); // Get the selected date from ReportDate

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM individualReports WHERE Username = ? AND Date = ?");
                    stmt.setString(1, selectedUsername); // Set the selected username as a parameter
                    stmt.setString(2, selectedDate); // Set the selected date as a parameter
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        int stockReceived = rs.getInt("stockReceived");
                        int stockSold = rs.getInt("stockSold");
                        String bestClient = rs.getString("bestClient");
                        double totalSales = rs.getDouble("totalSales");
                        double commissionEarned = rs.getDouble("commissionEarned");

                        // Create a JPanel to hold the editable fields
                        JPanel editPanel = new JPanel(new GridLayout(5, 2));
                        editPanel.add(new JLabel("Stock Received:"));
                        JTextField stockReceivedField = new JTextField(String.valueOf(stockReceived));
                        editPanel.add(stockReceivedField);
                        editPanel.add(new JLabel("Stock Sold:"));
                        JTextField stockSoldField = new JTextField(String.valueOf(stockSold));
                        editPanel.add(stockSoldField);
                        editPanel.add(new JLabel("Best Client:"));
                        JTextField bestClientField = new JTextField(bestClient);
                        editPanel.add(bestClientField);
                        editPanel.add(new JLabel("Total Sales:"));
                        JTextField totalSalesField = new JTextField(String.valueOf(totalSales));
                        editPanel.add(totalSalesField);
                        editPanel.add(new JLabel("Commission Earned:"));
                        JTextField commissionEarnedField = new JTextField(String.valueOf(commissionEarned));
                        editPanel.add(commissionEarnedField);

                        int result = JOptionPane.showConfirmDialog(EditReports.this, editPanel, "Edit Report", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            // Get updated values from the text fields
                            int updatedStockReceived = Integer.parseInt(stockReceivedField.getText());
                            int updatedStockSold = Integer.parseInt(stockSoldField.getText());
                            String updatedBestClient = bestClientField.getText();
                            double updatedTotalSales = Double.parseDouble(totalSalesField.getText());
                            double updatedCommissionEarned = Double.parseDouble(commissionEarnedField.getText());

                            // Update the individualReports table with the new values
                            PreparedStatement updateStmt = conn.prepareStatement("UPDATE individualReports SET stockReceived = ?, stockSold = ?, bestClient = ?, totalSales = ?, commissionEarned = ? WHERE Username = ? AND Date = ?");
                            updateStmt.setInt(1, updatedStockReceived);
                            updateStmt.setInt(2, updatedStockSold);
                            updateStmt.setString(3, updatedBestClient);
                            updateStmt.setDouble(4, updatedTotalSales);
                            updateStmt.setDouble(5, updatedCommissionEarned);
                            updateStmt.setString(6, selectedUsername);
                            updateStmt.setString(7, selectedDate);
                            int rowsAffected = updateStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(EditReports.this, "Report updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(EditReports.this, "Failed to update report.", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            updateStmt.close();
                        }

                    } else {
                        JOptionPane.showMessageDialog(EditReports.this, "No report found for selected username and date.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditReports.this, "Failed to connect to database.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditReports.this, "Invalid input. Please enter valid numbers for stock received, stock sold, total sales, and commission earned.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }
}
