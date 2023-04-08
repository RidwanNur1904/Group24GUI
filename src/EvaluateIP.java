import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EvaluateIP extends JFrame {
    private JPanel EvaluateIPpanel;
    private JLabel OfficeManagerImage;
    private JButton comparePerformanceButton;
    private JButton exitButton;
    private JComboBox TAlist;
    private JComboBox ReportAbox;
    private JComboBox ReportBbox;

    EvaluateIP(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Performance Review");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(EvaluateIPpanel);

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

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

        // Set action listener for TAlist JComboBox
        TAlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch data from MySQL table based on selected Username key in TAlist
                String selectedUsername = (String) TAlist.getSelectedItem();
                ReportAbox.removeAllItems();
                ReportBbox.removeAllItems();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT Date FROM individualReports WHERE Username = ?");
                    stmt.setString(1, selectedUsername);
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()) {
                        String date = rs.getString("Date");
                        ReportAbox.addItem(date);
                        ReportBbox.addItem(date);
                    }
                    conn.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        comparePerformanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = (String) TAlist.getSelectedItem();
                String selectedDateA = (String) ReportAbox.getSelectedItem();
                String selectedDateB = (String) ReportBbox.getSelectedItem();

                // Fetch data from MySQL table based on selected Date keys in ReportAbox and ReportBbox
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmtA = conn.prepareStatement("SELECT * FROM individualReports WHERE Username = ? AND Date = ?");
                    stmtA.setString(1, selectedUsername);
                    stmtA.setString(2, selectedDateA);
                    ResultSet rsA = stmtA.executeQuery();

                    PreparedStatement stmtB = conn.prepareStatement("SELECT * FROM individualReports WHERE Username = ? AND Date = ?");
                    stmtB.setString(1, selectedUsername);
                    stmtB.setString(2, selectedDateB);
                    ResultSet rsB = stmtB.executeQuery();

                    if (rsA.next() && rsB.next()) {
                        int stockSoldA = rsA.getInt("stockSold");
                        int totalSalesA = rsA.getInt("totalSales");
                        int commissionEarnedA = rsA.getInt("commissionEarned");

                        int stockSoldB = rsB.getInt("stockSold");
                        int totalSalesB = rsB.getInt("totalSales");
                        int commissionEarnedB = rsB.getInt("commissionEarned");

                        // Calculate differences
                        int stockSoldDiff = stockSoldB - stockSoldA;
                        int totalSalesDiff = totalSalesB - totalSalesA;
                        int commissionEarnedDiff = commissionEarnedB - commissionEarnedA;

                        // Prepare indication for positive or negative differences
                        String stockSoldIndication = stockSoldDiff > 0 ? "increased" : "decreased";
                        String totalSalesIndication = totalSalesDiff > 0 ? "increased" : "decreased";
                        String commissionEarnedIndication = commissionEarnedDiff > 0 ? "increased" : "decreased";

                        // Display differences with indication and selected dates in a dialog
                        JOptionPane.showMessageDialog(EvaluateIP.this,
                                "Username: " + selectedUsername +
                                        "\nStart from Date: " + selectedDateA +
                                        "\nEnd to Date: " + selectedDateB +
                                        "\nStock Sold Difference: " + stockSoldDiff + " (" + stockSoldIndication + ")" +
                                        "\nTotal Sales Difference: " + totalSalesDiff + " (" + totalSalesIndication + ")" +
                                        "\nCommission Earned Difference: " + commissionEarnedDiff + " (" + commissionEarnedIndication + ")",
                                "Performance Comparison",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(EvaluateIP.this,
                                "No data found for the selected dates.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    conn.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OMperformanceReport oMperformanceReport = new OMperformanceReport();
                oMperformanceReport.setVisible(true);
            }
        });
    }
}
