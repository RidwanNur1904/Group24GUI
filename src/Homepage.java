import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame {
    private JButton loginButton;
    private JPanel homePanel;
    private JButton exitButton;
    private JLabel ImageLogo;

    public Homepage() {
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
        ImageIcon originalIcon = new ImageIcon("data/airplane.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        ImageLogo.setIcon(scaledIcon);
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
