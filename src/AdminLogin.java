import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminLogin extends JFrame {
    private JPasswordField Password;
    private JPanel AdPanel;
    private JTextField Username;
    private JLabel AdminImage;
    private JLabel AdminLabel;
    private JButton loginButton;
    private JButton BackButton;

    AdminLogin(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Login");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(AdPanel);

        //Set AdminLabel Image
        ImageIcon originalIcon = new ImageIcon("data/Admin.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminLabel.setIcon(scaledIcon);
        AdminLabel.setSize(200, 200);
        AdminLabel.setBorder(new EmptyBorder(40, 40, 40, 40));

        //Set font size to 24 for Username and Password
        Font font = new Font("Calibri", Font.BOLD, 24);
        Username.setFont(font);
        Password.setFont(font);

        //action Button to login to the Admin Controls
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = Username.getText().trim();
                String password = new String(Password.getPassword()).trim();

                try {
                    // Connect to the MySQL database
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                    // Prepare the SQL query with placeholders for username and password
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE Username = ? AND Password = ?");
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    // Execute the prepared statement and retrieve the result set
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Check if the query returned any rows
                    if (resultSet.next()) {
                        // If a row was returned, dispose of the AdminLogin window
                        dispose();
                        AdminOptions adminOptions = new AdminOptions();
                        adminOptions.setVisible(true);

                    } else {
                        // If no rows were returned, display an error message
                        JOptionPane.showMessageDialog(null, "Incorrect username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close the prepared statement and connection objects
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountSelect accountSelect = new AccountSelect();
                accountSelect.setVisible(true);
                dispose();
            }
        });
    }
}
