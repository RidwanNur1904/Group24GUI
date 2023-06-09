import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMperformanceReport extends JFrame {
    private JPanel OMPpanel;
    private JLabel OfficeManagerImage;
    private JButton TeamPerformanceButton;
    private JButton exitButton;
    private JButton evaluateIndividualPerformanceButton;

    OMperformanceReport(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Performance Review");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMPpanel);

        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        exitButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        TeamPerformanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EvaluateTeamPerformance evaluateTeamPerformance = new EvaluateTeamPerformance();
                evaluateTeamPerformance.setVisible(true);
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OfficeManagerOptions officeManagerOptions = new OfficeManagerOptions();
                officeManagerOptions.setVisible(true);
            }
        });

        evaluateIndividualPerformanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EvaluateIP evaluateIP = new EvaluateIP();
                evaluateIP.setVisible(true);
                dispose();
            }
        });
    }
}
