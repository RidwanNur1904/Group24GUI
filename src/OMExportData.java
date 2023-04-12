import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class OMExportData extends JFrame {
    private JPanel OMEpanel;
    private JButton exportTeamDataButton;
    private JButton exportIndividualDataButton;
    private JButton exitButton;
    private JLabel OfficeManagerImage;

    OMExportData(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMEpanel);

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

        exportTeamDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Connect to the MySQL database
                String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24";
                String username = "in2018g24_a";
                String password = "GTrSnz41";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    // Execute query to fetch data from the "TeamPerformanceReport" table
                    Statement statement = connection.createStatement();
                    String query = "SELECT BestPerformingTA, WorstPerformingTA, totalstockReceived, totalstockSold, bestClient, totalSales, Date FROM TeamPerformanceReport";
                    ResultSet resultSet = statement.executeQuery(query);

                    // Create an Excel file
                    String filePath = "Spreadsheets\\TeamPerformanceReport.xls";
                    FileWriter excelFile = new FileWriter(filePath);
                    BufferedWriter bufferedWriter = new BufferedWriter(excelFile);

                    // Write title to the Excel file
                    bufferedWriter.write("AirVia Ltd");
                    bufferedWriter.newLine();
                    bufferedWriter.write("5374 Main Street");
                    bufferedWriter.newLine();
                    bufferedWriter.write("City, County");
                    bufferedWriter.newLine();
                    bufferedWriter.write("WC2N 5DN");
                    bufferedWriter.newLine();
                    bufferedWriter.write("020 7946 5374");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();

                    // Write column headers to the Excel file
                    bufferedWriter.write("BestPerformingTA\tWorstPerformingTA\ttotalstockReceived\ttotalstockSold\tbestClient\ttotalSales\tDate");
                    bufferedWriter.newLine();

                    // Write data to the Excel file
                    while (resultSet.next()) {
                        String bestPerformingTA = resultSet.getString("BestPerformingTA");
                        String worstPerformingTA = resultSet.getString("WorstPerformingTA");
                        int totalstockReceived = resultSet.getInt("totalstockReceived");
                        int totalstockSold = resultSet.getInt("totalstockSold");
                        String bestClient = resultSet.getString("bestClient");
                        double totalSales = resultSet.getDouble("totalSales");
                        Date date = resultSet.getDate("Date");

                        bufferedWriter.write(bestPerformingTA + "\t" + worstPerformingTA + "\t" + totalstockReceived + "\t" + totalstockSold + "\t" + bestClient + "\t" + totalSales + "\t" + date);
                        bufferedWriter.newLine();
                    }

                    // Close resources
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    resultSet.close();
                    statement.close();
                    connection.close();

                    System.out.println("Data exported to Excel successfully!");

                    // Display a pop-up message to indicate successful export
                    JOptionPane.showMessageDialog(null, "Data has been exported to Excel successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        exportIndividualDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Connect to the MySQL database
                String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24";
                String username = "in2018g24_a";
                String password = "GTrSnz41";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);

                    // Execute query to fetch data from the "individualReports" table
                    Statement statement = connection.createStatement();
                    String query = "SELECT Username, stockReceived, stockSold, bestClient, totalSales, commissionEarned, Date FROM individualReports";
                    ResultSet resultSet = statement.executeQuery(query);

                    // Create an Excel file
                    String filePath = "Spreadsheets\\individualReports.xls";
                    FileWriter excelFile = new FileWriter(filePath);
                    BufferedWriter bufferedWriter = new BufferedWriter(excelFile);

                    // Write title to the Excel file
                    bufferedWriter.write("AirVia Ltd");
                    bufferedWriter.newLine();
                    bufferedWriter.write("5374 Main Street");
                    bufferedWriter.newLine();
                    bufferedWriter.write("City, County");
                    bufferedWriter.newLine();
                    bufferedWriter.write("WC2N 5DN");
                    bufferedWriter.newLine();
                    bufferedWriter.write("020 7946 5374");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();

                    // Write column headers to the Excel file
                    bufferedWriter.write("Username\tstockReceived\tstockSold\tbestClient\ttotalSales\tcommissionEarned\tDate");
                    bufferedWriter.newLine();

                    // Write data to the Excel file
                    while (resultSet.next()) {
                        String username1 = resultSet.getString("Username");
                        int stockReceived = resultSet.getInt("stockReceived");
                        int stockSold = resultSet.getInt("stockSold");
                        String bestClient = resultSet.getString("bestClient");
                        double totalSales = resultSet.getDouble("totalSales");
                        double commissionEarned = resultSet.getDouble("commissionEarned");
                        Date date = resultSet.getDate("Date");

                        bufferedWriter.write(username1 + "\t" + stockReceived + "\t" + stockSold + "\t" + bestClient + "\t" + totalSales + "\t" + commissionEarned + "\t" + date);
                        bufferedWriter.newLine();
                    }

                    // Close resources
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    resultSet.close();
                    statement.close();
                    connection.close();

                    System.out.println("Data exported to Excel successfully!");

                    // Display a pop-up message to indicate successful export
                    JOptionPane.showMessageDialog(null, "Data has been exported to Excel successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
                dispose();
            }
        });
    }
}
