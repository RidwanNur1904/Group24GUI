import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateNewUser extends JFrame {
    private JPanel CNUpanel;
    private JLabel AdminImage;
    private JTextField UserField;
    private JButton createUserButton;
    private JButton exitButton;
    private JComboBox UserRole;
    private JPasswordField Pfield;

    CreateNewUser() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setTitle("Admin Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(CNUpanel);

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) UserRole.getSelectedItem();
                String username = UserField.getText();
                char[] passwordChars = Pfield.getPassword();
                String password = new String(passwordChars);

                // Check if username and password are not blank
                if (username.isBlank() || password.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Username and password cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Show password confirmation dialog
                    JPasswordField confirmPField = new JPasswordField();
                    Object[] message = {
                            "Confirm Password:", confirmPField
                    };
                    int option = JOptionPane.showOptionDialog(null, message, "Confirm Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                    if (option == JOptionPane.OK_OPTION) {
                        String confirmPassword = new String(confirmPField.getPassword());

                        // Check if password matches the confirmation
                        if (!password.equals(confirmPassword)) {
                            JOptionPane.showMessageDialog(null, "Password confirmation does not match.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            try {
                                // Connect to the database (assuming you have already established a database connection)
                                Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                                // Prepare the SQL statement based on the selected role
                                String sql = "";
                                if (selectedRole.equals("Admin")) {
                                    sql = "INSERT INTO Admin (username, password) VALUES (?, ?)";
                                } else if (selectedRole.equals("TravelAdvisor")) {
                                    sql = "INSERT INTO TravelAdvisor (username, password) VALUES (?, ?)";
                                } else if (selectedRole.equals("OfficeManager")) {
                                    sql = "INSERT INTO OfficeManager (username, password) VALUES (?, ?)";
                                }

                                // Execute the SQL statement
                                PreparedStatement stmt = conn.prepareStatement(sql);
                                stmt.setString(1, username);
                                stmt.setString(2, password);
                                stmt.executeUpdate();

                                // Close the database connection and statement
                                stmt.close();
                                conn.close();

                                JOptionPane.showMessageDialog(null, "User created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error creating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
            }
        });
    }
}
