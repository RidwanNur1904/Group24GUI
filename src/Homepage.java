import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {
    private JButton loginButton;
    private JPanel homePanel;
    private JButton exitButton;
    private JLabel ImageLogo;
    private JLabel WeatherLabel;

    private ImageIcon originalIcon;
    private ImageIcon clickedIcon;

    public Homepage() {

        // Load original and clicked icons
        originalIcon = new ImageIcon("data/AIrviaLogo.png"); // Replace this with the actual path to your original image file
        clickedIcon = new ImageIcon("data/airplane.png"); // Replace this with the actual path to your clicked image file

        // Scale original icon to 100x100 pixels
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        originalIcon = new ImageIcon(scaledImage);

        // Scale clicked icon to 100x100 pixels
        Image clickedImage = clickedIcon.getImage();
        Image scaledClickedImage = clickedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        clickedIcon = new ImageIcon(scaledClickedImage);

        // Set original icon to ImageLogo
        ImageLogo.setIcon(originalIcon);

        // Add mouse listener to ImageLogo
        ImageLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Change icon when ImageLogo is clicked
                if (ImageLogo.getIcon().equals(originalIcon)) {
                    ImageLogo.setIcon(clickedIcon);
                } else {
                    ImageLogo.setIcon(originalIcon);
                }
            }
        });
        // Action event to close the program when exit is pressed
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });

        // Action event to move to the AccountSelect Panel
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountSelect accountSelect = new AccountSelect();
                dispose();
                accountSelect.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Homepage HP = new Homepage();
        HP.setContentPane(HP.homePanel);
        HP.setTitle("Homepage");
        HP.setSize(1000,700);
        HP.setLocationRelativeTo(null); // set location to center of the screen
        HP.setVisible(true);
        HP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
