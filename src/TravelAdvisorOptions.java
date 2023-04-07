import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TravelAdvisorOptions extends JFrame {
    private JPanel TAOpanel;
    private JLabel TravelAdvisor;
    private JLabel TravelAdvisorImage;
    private JButton sellBlankButton;
    private JButton createIndividualReportButton;
    private JButton logOutButton;

    TravelAdvisorOptions(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(TAOpanel);


        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Logout.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        logOutButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TravelAdvisorLogin travelAdvisorLogin = new TravelAdvisorLogin();
                travelAdvisorLogin.setVisible(true);
            }
        });

        sellBlankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellBlanks sellBlanks = new SellBlanks();
                sellBlanks.setVisible(true);
                dispose();
            }
        });

        createIndividualReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndReport indReport = new IndReport();
                indReport.setVisible(true);
                dispose();
            }
        });
    }
}
