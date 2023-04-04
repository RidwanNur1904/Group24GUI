import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReallocateBlanks extends JFrame {
    private JPanel ReallocatePanel;
    private JLabel BlankReallocation;
    private JLabel OfficeManagerImage;
    private JButton back;
    private JComboBox InitialTA;
    private JComboBox AllocatedBlanks;
    private JComboBox FinalTA;
    private JButton ReallocateBlanksButton;
    private JTextField Date;

    ReallocateBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Insert New data");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ReallocatePanel);

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        //Set font size to 24 for Date
        Font font = new Font("Calibri", Font.BOLD, 20);
        Date.setFont(font);

        Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK); // Create black bottom border
        Date.setBorder(border);

        //shows the Usernames from the MySQL table TravelAdvisor
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Retrieve the Username values from the TravelAdvisor table
            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT Username FROM allocatedBlanks");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                InitialTA.addItem(username);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // shows the Usernames from the MySQL table TravelAdvisor for the FinalTA JComboBox
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Retrieve the Username values from the TravelAdvisor table
            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT Username FROM TravelAdvisor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                FinalTA.addItem(username); // add username to FinalTA JComboBox
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // shows the BlankID values associated with the selected Username from the allocatedBlanks table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Retrieve the BlankID values associated with the selected Username from the allocatedBlanks table
            String selectedUsername = (String) InitialTA.getSelectedItem(); // get the selected Username
            PreparedStatement stmt = con.prepareStatement("SELECT BlankID FROM allocatedBlanks WHERE Username = ?");
            stmt.setString(1, selectedUsername); // set the parameter to the selected Username
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String blankID = rs.getString("BlankID");
                AllocatedBlanks.addItem(blankID); // add BlankID to AllocatedBlanks JComboBox
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }



        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
            }
        });
    }
}
