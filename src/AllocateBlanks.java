import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class AllocateBlanks extends JFrame {
    private JPanel AllocatePanel;
    private JLabel BlanksAllocation;
    private JLabel OfficeManagerImage;
    private JButton back;
    private JTextField Date;
    private JComboBox TAlist;
    private JButton AllocateBlanksButton;
    private JLabel StartingLabel;
    private JLabel EndingLabel;
    private JComboBox SRcombo;
    private JComboBox ERcombo;


    AllocateBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Insert New data");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(AllocatePanel);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);


        //Set font size to 24 for Date
        Font font = new Font("Calibri", Font.BOLD, 20);
        Date.setFont(font);
        //StartingPoint.setFont(font);
        //EndingPoint.setFont(font);

        Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK); // Create black bottom border
        Date.setBorder(border);
        //StartingPoint.setBorder(border);
        //EndingPoint.setBorder(border);


        //shows the Usernames from the MySQL table TravelAdvisor
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            // Retrieve the Username values from the TravelAdvisor table
            PreparedStatement stmt = con.prepareStatement("SELECT Username FROM TravelAdvisor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                TAlist.addItem(username);
            }

            // Retrieve the BlankID values from the BlankStock table
            stmt = con.prepareStatement("SELECT BlankID FROM BlankStock");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String blankID = rs.getString("BlankID");
                SRcombo.addItem(blankID);
                ERcombo.addItem(blankID);
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

        AllocateBlanksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startID = SRcombo.getSelectedItem().toString();
                String endID = ERcombo.getSelectedItem().toString();
                String date = Date.getText();
                String username = TAlist.getSelectedItem().toString();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    Statement stmt = con.createStatement();

                    long start = Long.parseLong(startID);
                    long end = Long.parseLong(endID);
                    for (long i = start; i <= end; i++) {
                        String insertQuery = "INSERT INTO allocatedBlanks (BlankID, Date, Username) VALUES ('" + i + "', '" + date + "', '" + username + "')";
                        stmt.executeUpdate(insertQuery);
                    }

                    JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                    con.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

    }

    private class TextFieldFocusListener implements FocusListener {
        private JTextField textField;
        private String defaultText;

        public TextFieldFocusListener(JTextField textField, String defaultText) {
            this.textField = textField;
            this.defaultText = defaultText;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(defaultText)) {
                textField.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(defaultText);
            }
        }
    }
}
