import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CommissionRateChange extends JFrame {

    private JPanel CRCpanel;
    private JLabel OfficeManagerImage;
    private JComboBox TAlist;
    private JTextField CRamountText;
    private JButton modifyratebutton;
    private JButton exitButton;

    CommissionRateChange(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Change Commission Rate");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(CRCpanel);

        // Fetch data from MySQL and populate JComboBox
        try {
            // Connect to MySQL database
            Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
            Statement stmt = conn.createStatement();

            // Fetch data from TravelAdvisor table
            ResultSet rs = stmt.executeQuery("SELECT Username FROM TravelAdvisor");

            // Populate JComboBox with fetched data
            while (rs.next()) {
                String username = rs.getString("Username");
                TAlist.addItem(username);
            }

            // Close database connections
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        modifyratebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = (String) TAlist.getSelectedItem(); // Get selected username from JComboBox
                String newRate = CRamountText.getText(); // Get new rate from JTextField
                if (selectedUsername != null && !selectedUsername.isEmpty() && newRate != null && !newRate.isEmpty()) {
                    try {
                        // Connect to MySQL database
                        Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                        PreparedStatement pstmt = conn.prepareStatement("UPDATE CommissionRates SET Rates = ? WHERE Username = ?");
                        pstmt.setString(1, newRate);
                        pstmt.setString(2, selectedUsername);
                        int rowsAffected = pstmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Commission rate updated successfully!");
                        } else {
                            // If no rows were affected, it means the username is not in CommissionRates table, so insert a new row
                            pstmt = conn.prepareStatement("INSERT INTO CommissionRates (Username, Rates) VALUES (?, ?)");
                            pstmt.setString(1, selectedUsername);
                            pstmt.setString(2, newRate);
                            int rowsInserted = pstmt.executeUpdate();

                            if (rowsInserted > 0) {
                                JOptionPane.showMessageDialog(null, "New commission rate set for a new user!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update commission rate.");
                            }
                        }

                        // Close database connections
                        pstmt.close();
                        conn.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a username and enter a new rate.");
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
