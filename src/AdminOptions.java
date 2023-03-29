import javax.swing.*;
import java.awt.*;

public class AdminOptions extends JFrame {

    private JPanel AOpanel;
    private JButton updateBlanksButton;
    private JButton repairDatabaseButton;
    private JButton removeUserButton;
    private JLabel AdminImage;

    AdminOptions(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(AOpanel);

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

    }
}
