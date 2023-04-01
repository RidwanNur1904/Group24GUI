import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RemoveUser extends JFrame {
    private JPanel RUpanel;
    private JLabel AdminImage;
    private JComboBox<String> UserList;
    private JButton RRbutton;
    private JButton exitButton;
    private JTextField Reason;
    private JLabel removallabel;

    RemoveUser(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Create Removal Request");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RUpanel);

        // Set up the JComboBox with the usernames from the MySQL table
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24";
            String user = "in2018g24_a";
            String password = "GTrSnz41";
            conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT Username FROM TravelAdvisor";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                UserList.addItem(username);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
            }
        });

        RRbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = UserList.getSelectedItem().toString();
                String reason = Reason.getText();
                if (selectedUser != null && !selectedUser.isEmpty()) {
                    Connection conn = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24";
                        String user = "in2018g24_a";
                        String password = "GTrSnz41";
                        conn = DriverManager.getConnection(url, user, password);

                        // Insert the selected user into the reportedUsers table
                        String insertQuery = "INSERT INTO reportedUsers (Username, Reason) VALUES (?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                        insertStmt.setString(1, selectedUser);
                        insertStmt.setString(2, reason);
                        insertStmt.executeUpdate();

                        // Remove the selected user from the TravelAdvisor table
                        String deleteQuery = "DELETE FROM TravelAdvisor WHERE Username = ?";
                        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                        deleteStmt.setString(1, selectedUser);
                        deleteStmt.executeUpdate();

                        // Display a confirmation message to the user
                        JOptionPane.showMessageDialog(null, "User " + selectedUser + " has been reported and is now under review ");
                        dispose();
                        AdminOptions adminOptions = new AdminOptions();
                        adminOptions.setVisible(true);

                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
