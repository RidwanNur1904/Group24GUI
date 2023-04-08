import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfficeManagerOptions extends JFrame {

    private JPanel OMOpanel;
    private JLabel OfficeManager;
    private JButton allocateBlankButton;
    private JButton reallocateBlankButton;
    private JButton createReportButton;
    private JButton setCommissionRateButton;
    private JButton reviewAccountRemovalButton;
    private JButton logOutButton;
    private JLabel OfficeManagerImage;

    OfficeManagerOptions(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMOpanel);


        ImageIcon originalIcon = new ImageIcon("data/OfficeManager.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        OfficeManagerImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Logout.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        logOutButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image



        // Add a mouse listener to the logout label to handle the click event
        logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                OfficeManagerLogin officeManagerLogin = new OfficeManagerLogin();
                officeManagerLogin.setVisible(true);
            }
        });

        reviewAccountRemovalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OMreportedUserReview oMreportedUserReview = new OMreportedUserReview();
                oMreportedUserReview.setVisible(true);
            }
        });

        allocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AllocateBlanks allocateBlanks = new AllocateBlanks();
                allocateBlanks.setVisible(true);
            }
        });

        reallocateBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ReallocateBlanks reallocateBlanks = new ReallocateBlanks();
                reallocateBlanks.setVisible(true);
            }
        });

        setCommissionRateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CommissionRateChange commissionRateChange = new CommissionRateChange();
                commissionRateChange.setVisible(true);
            }
        });

        createReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OMperformanceReport oMperformanceReport = new OMperformanceReport();
                oMperformanceReport.setVisible(true);
                dispose();
            }
        });
    }
}
