import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AdminBlankRequest extends JFrame {
    private JPanel ABRpanel;
    private JLabel AdminImage;
    private JTextField Amount;
    private JTextField Reason;
    private JButton requestBlanksButton;
    private JButton back;

    AdminBlankRequest(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Blank Request");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABRpanel);

        // Set default text for text fields
        Amount.setText("Please enter the amount you require");

        //Set font size to 24 for Username and Password
        Font font = new Font("Calibri", Font.BOLD, 16);
        Amount.setFont(font);
        Reason.setFont(font);

        // Add focus listeners to text fields
        Amount.addFocusListener(new AdminBlankRequest.TextFieldFocusListener(Amount, "Please enter the amount you require"));

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
            }
        });

        requestBlanksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
                String amount = Amount.getText();
                String reason = Reason.getText();

                // Check if the fields are not empty
                if (!amount.isEmpty() && !reason.isEmpty()) {
                    try {
                        // Establish a connection to the database
                        Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

                        // Create a PreparedStatement to execute the INSERT statement
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO BlankRequests (amountRequested, Reason, date) VALUES (?, ?, NOW())");

                        // Set the values for the placeholders in the statement
                        stmt.setInt(1, Integer.parseInt(amount));
                        stmt.setString(2, reason);

                        // Execute the statement
                        stmt.executeUpdate();

                        // Close the statement and connection
                        stmt.close();
                        conn.close();

                        // Display a message to the user
                        JOptionPane.showMessageDialog(AdminBlankRequest.this, "Blank request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the text fields
                        Amount.setText("");
                        Reason.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AdminBlankRequest.this, "Error submitting blank request", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminBlankRequest.this, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
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
