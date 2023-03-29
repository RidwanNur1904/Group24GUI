import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminBlankManagement extends JFrame {
    private JPanel ABMpanel;
    private JButton checkStocklistButton;
    private JButton editReportsButton;
    private JButton requestBlanksButton;
    private JButton sendBlanksButton;
    private JButton back;
    private JLabel AdminImage;

    AdminBlankManagement(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Blank Manager");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABMpanel);

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
                AdminOptions adminOptions = new AdminOptions();
                dispose();
                adminOptions.setVisible(true);
            }
        });

    }
}
