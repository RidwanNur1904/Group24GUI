import javax.swing.*;
import java.awt.*;

public class ReallocateBlanks extends JFrame {
    private JPanel ReallocatePanel;
    private JLabel BlankReallocation;
    private JLabel OfficeManagerImage;

    ReallocateBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Insert New data");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ReallocatePanel);

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);
    }
}
