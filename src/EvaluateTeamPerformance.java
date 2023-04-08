import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class EvaluateTeamPerformance extends JFrame {
    private JPanel ETPpanel;
    private JButton exitButton;
    private JComboBox MonthBox;
    private JComboBox YearBox;
    private JButton createReportButton;
    private JTextField DateField;


    EvaluateTeamPerformance(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Performance Review");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ETPpanel);

        // Populate YearBox with years from current year to past 10 years
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        for (int year = currentYear; year >= currentYear - 10; year--) {
            YearBox.addItem(year);
        }

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected month from MonthBox
                String selectedMonth = String.format("%02d", MonthBox.getSelectedIndex() + 1);
                // Get selected year from YearBox
                String selectedYear = YearBox.getSelectedItem().toString();
                // Get entered date from DateField
                String selectedDate = DateField.getText();

                // Connect to MySQL database
                String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace db_name with your actual database name
                String username = "in2018g24_a"; // Replace username with your actual username
                String password = "GTrSnz41"; // Replace password with your actual password

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    // Retrieve data from individualReports table
                    String selectQuery = "SELECT * FROM individualReports WHERE MONTH(Date) = ? AND YEAR(Date) = ?";
                    try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
                        stmt.setString(1, selectedMonth);
                        stmt.setString(2, selectedYear);
                        try (ResultSet rs = stmt.executeQuery()) {
                            String bestTA = null;
                            String worstTA = null;
                            int highestStockSold = Integer.MIN_VALUE; // Initialize with minimum value
                            int lowestStockSold = Integer.MAX_VALUE; // Initialize with maximum value
                            int totalStockReceived = 0;
                            int totalStockSold = 0;
                            String bestClient = null;
                            int totalSales = 0;

                            // Process result set
                            while (rs.next()) {
                                String username1 = rs.getString("Username");
                                int stockReceived = rs.getInt("stockReceived");
                                int stockSold = rs.getInt("stockSold");
                                String client = rs.getString("bestClient");
                                int sales = rs.getInt("totalSales");

                                // Update Best Performing TA
                                if (stockSold > highestStockSold) {
                                    highestStockSold = stockSold;
                                    bestTA = username1;
                                }

                                // Update Worst Performing TA
                                if (stockSold < lowestStockSold) {
                                    lowestStockSold = stockSold;
                                    worstTA = username1;
                                }

                                // Update Total Stock Received
                                totalStockReceived += stockReceived;

                                // Update Total Stock Sold
                                totalStockSold += stockSold;

                                // Update Best Client
                                if (bestClient == null || client.equals(rs.getString("bestClient"))) {
                                    bestClient = client;
                                }

                                // Update Total Sales
                                totalSales += sales;
                            }

                            // Insert data into TeamPerformanceReport table
                            String insertQuery = "INSERT INTO TeamPerformanceReport (BestPerformingTA, WorstPerformingTA, totalstockReceived, totalstockSold, bestClient, totalSales, Date) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement stmt2 = connection.prepareStatement(insertQuery)) {
                                stmt2.setString(1, bestTA);
                                stmt2.setString(2, worstTA);
                                stmt2.setInt(3, totalStockReceived);
                                stmt2.setInt(4, totalStockSold);
                                stmt2.setString(5, bestClient);
                                stmt2.setInt(6, totalSales);
                                stmt2.setString(7, selectedDate);
                                stmt2.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Team Performance Report created successfully!");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error creating Team Performance Report: " + ex.getMessage());
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OMperformanceReport oMperformanceReport = new OMperformanceReport();
                oMperformanceReport.setVisible(true);
                dispose();
            }
        });
    }
}
