import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class TravelAdvisorLogin extends JFrame {
    private JPanel TApanel;
    private JLabel TravelAdvisorLabel;
    private JLabel TravelAdvisorImage;
    private JTextField Username;
    private JPasswordField Password;
    private JButton loginButton;
    private JButton BackButton;

    private static final String USERNAME_PLACEHOLDER = "Username";
    private static final String PASSWORD_PLACEHOLDER = "Password";

    TravelAdvisorLogin(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Login");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(TApanel);

        //Set TravelAdvisorImage
        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);
        TravelAdvisorImage.setSize(100, 100);
        TravelAdvisorImage.setBorder(new EmptyBorder(40, 40, 40, 80));

        //Set font size to 24 for Username and Password
        Font font = new Font("Calibri", Font.BOLD, 24);
        Username.setFont(font);
        Password.setFont(font);

        // Add focus listeners to clear and set placeholder text
        Username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Username.getText().equals(USERNAME_PLACEHOLDER)) {
                    Username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Username.getText().isEmpty()) {
                    Username.setText(USERNAME_PLACEHOLDER);
                }
            }
        });

        Password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(Password.getPassword()).equals(PASSWORD_PLACEHOLDER)) {
                    Password.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(Password.getPassword()).isEmpty()) {
                    Password.setText(PASSWORD_PLACEHOLDER);
                }
            }
        });

        //action Button to login to the Travel Advisor Controls
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = Username.getText().trim();
                String password = new String(Password.getPassword()).trim();

                try {
                    // Connect to the MySQL database
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_d", "GAQJrx18");

                    // Prepare the SQL query with placeholders for username and password
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TravelAdvisor WHERE Username = ? AND Password = ?");
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    // Execute the prepared statement and retrieve the result set
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Check if the query returned any rows
                    if (resultSet.next()) {
                        // If a row was returned, dispose of the TravelAdvisorLogin window
                        dispose();
                        TravelAdvisorOptions travelAdvisorOptions = new TravelAdvisorOptions();
                        travelAdvisorOptions.setVisible(true);

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

        Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK); // Create black bottom border
        Password.setBorder(border);
        Username.setBorder(border);

        // declare the AccountSelect object outside of the ActionListener
        AccountSelect accountSelect = new AccountSelect();
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountSelect.setVisible(true);
                dispose();
            }
        });
    }
}
