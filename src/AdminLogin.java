import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame {
    private JPasswordField Password;
    private JPanel AdPanel;
    private JTextField Username;
    private JLabel AdminImage;
    private JLabel AdminLabel;
    private JButton loginButton;

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
        Font font = new Font("Courier New", Font.BOLD, 24);
        Username.setFont(font);
        Password.setFont(font);

        //action Button to login to the Admin Controls
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
