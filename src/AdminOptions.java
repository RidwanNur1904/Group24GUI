import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminOptions extends JFrame {

    private JPanel AOpanel;
    private JButton updateBlanksButton;
    private JButton repairDatabaseButton;
    private JButton removeUserButton;
    private JLabel AdminImage;
    private JButton logout;
    private JButton backupMySQLButton;

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

        ImageIcon logoutIcon = new ImageIcon("data/Logout.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        logout.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        // Add a mouse listener to the logout label to handle the click event
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminLogin adminLogin = new AdminLogin();
                dispose();
                adminLogin.setVisible(true);
            }
        });


        updateBlanksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminBlankManagement adminBlankManagement = new AdminBlankManagement();
                adminBlankManagement.setVisible(true);
            }
        });

        repairDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RestoreDatabase restoreDatabase = new RestoreDatabase();
                restoreDatabase.setVisible(true);
            }
        });

        backupMySQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminBackup adminBackup = new AdminBackup();
                adminBackup.setVisible(true);
            }
        });

        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RemoveUser removeUser = new RemoveUser();
                removeUser.setVisible(true);
            }
        });

    }
}
