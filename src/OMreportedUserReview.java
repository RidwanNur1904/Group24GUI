import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OMreportedUserReview extends JFrame {
    private JPanel OMRURpanel;
    private JLabel OfficeManagerImage;
    private JComboBox UserList;
    private JTextField Reason;
    private JButton back;
    private JButton removeUserButton;
    private JButton reinstateUserButton;

    OMreportedUserReview(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager reported users review");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMRURpanel);

        // Fetch data from MySQL table and populate it into JComboBox
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
            PreparedStatement stmt = con.prepareStatement("SELECT Username, Reason FROM reportedUsers");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Username");
                String reason = rs.getString("Reason");
                UserList.addItem(username);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        // Update JTextField with the selected item's corresponding Reason
        UserList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmt = con.prepareStatement("SELECT Reason FROM reportedUsers WHERE Username = ?");
                    stmt.setString(1, UserList.getSelectedItem().toString());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        Reason.setText(rs.getString("Reason"));
                    }

                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
            }
        });

        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    PreparedStatement stmt = con.prepareStatement("DELETE FROM reportedUsers WHERE Username = ?");
                    stmt.setString(1, UserList.getSelectedItem().toString());
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "The User account has been removed");

                    // Remove the selected username from the JComboBox
                    UserList.removeItem(UserList.getSelectedItem());

                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        reinstateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = UserList.getSelectedItem().toString();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Insert the user into the TravelAdvisor table
                    PreparedStatement stmt = con.prepareStatement("INSERT INTO TravelAdvisor (Username, Password) VALUES (?, ?)");
                    stmt.setString(1, selectedUser);
                    stmt.setString(2, "");

                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        // Prompt the user to enter a new password for the reinstated account
                        JOptionPane.showMessageDialog(null, "Create new password for reinstated account");
                        String newPassword = JOptionPane.showInputDialog(null, "Enter new password for " + selectedUser);

                        // Update the password for the reinstated account
                        PreparedStatement updateStmt = con.prepareStatement("UPDATE TravelAdvisor SET Password = ? WHERE Username = ?");
                        updateStmt.setString(1, newPassword);
                        updateStmt.setString(2, selectedUser);
                        updateStmt.executeUpdate();

                        // Remove the user from the reportedUsers table
                        PreparedStatement removeStmt = con.prepareStatement("DELETE FROM reportedUsers WHERE Username = ?");
                        removeStmt.setString(1, selectedUser);
                        removeStmt.executeUpdate();

                        // Show a success message and remove the user from the user list
                        JOptionPane.showMessageDialog(null, "The user account has been reinstated");
                        UserList.removeItem(selectedUser);
                    }

                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });


    }
}
