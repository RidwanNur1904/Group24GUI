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

            }
        });
    }
}
