import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestoreDatabase extends JFrame {

    private JPanel RepairPanel;
    private JLabel AdminImage;
    private JTextField DBURL;
    private JTextField DBUsername;
    private JTextField DBpassword;
    private JButton restoreConnectionButton;
    private JButton ExitButton;

    RestoreDatabase() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setTitle("Repair Database");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RepairPanel);

        // Set default text for text fields
        DBURL.setText("Database URL");
        DBUsername.setText("Database Username");
        DBpassword.setText("Database Password");

        //Set font size to 24 for Username and Password
        Font font = new Font("Calibri", Font.BOLD, 20);
        DBUsername.setFont(font);
        DBpassword.setFont(font);
        DBURL.setFont(font);

        // Add focus listeners to text fields
        DBURL.addFocusListener(new TextFieldFocusListener(DBURL, "Database URL"));
        DBUsername.addFocusListener(new TextFieldFocusListener(DBUsername, "Database Username"));
        DBpassword.addFocusListener(new TextFieldFocusListener(DBpassword, "Database Password"));


        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        ExitButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK); // Create black bottom border
        DBpassword.setBorder(border);
        DBUsername.setBorder(border);
        DBURL.setBorder(border);

        restoreConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbURL = DBURL.getText();
                String username = DBUsername.getText();
                String password = DBpassword.getText();

                try {
                    // Attempt to create a connection to the database
                    Connection conn = DriverManager.getConnection(dbURL, username, password);

                    // If connection is successful, show a pop-up message
                    JOptionPane.showMessageDialog(null, "Connection Successful!");
                    dispose();
                    AdminOptions adminOptions = new AdminOptions();
                    adminOptions.setVisible(true);

                    // Close the connection
                    conn.close();
                } catch (SQLException ex) {
                    // If connection fails, show a pop-up message
                    JOptionPane.showMessageDialog(null, "Connection Failed Try Again!");
                    ex.printStackTrace();
                }
            }
        });
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
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
