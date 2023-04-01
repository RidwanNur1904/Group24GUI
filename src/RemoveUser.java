import javax.swing.*;
import java.awt.*;

public class RemoveUser extends JFrame {
    private JPanel RUpanel;
    private JLabel AdminImage;

    RemoveUser(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Create Removal Request");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RUpanel);

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);
    }
}
