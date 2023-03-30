import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountSelect extends JFrame {


    private JPanel ACpanel;
    private JButton AdminButton;
    private JButton OfficeManagerButton;
    private JButton TAbutton;
    private JLabel HeaderLabel;
    private JLabel AdminLabel;
    private JLabel OMlabel;
    private JLabel TAlabel;

    AccountSelect(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Account Select");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ACpanel);

        ImageIcon adminIcon = new ImageIcon("data/Admin.png");
        ImageIcon officeManagerIcon = new ImageIcon("data/OfficeManager.png");
        ImageIcon salesAdvisorIcon = new ImageIcon("data/SalesAdvisor.png");

        // create new ImageIcons from the scaled images
        adminIcon = new ImageIcon(adminIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        officeManagerIcon = new ImageIcon(officeManagerIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        salesAdvisorIcon = new ImageIcon(salesAdvisorIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        AdminButton.setIcon(adminIcon);
        OfficeManagerButton.setIcon(officeManagerIcon);
        TAbutton.setIcon(salesAdvisorIcon);

        AdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin adminLogin = new AdminLogin();
                adminLogin.setVisible(true);
                dispose();
            }
        });

        OfficeManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OfficeManagerLogin officeManagerLogin = new OfficeManagerLogin();
                officeManagerLogin.setVisible(true);
                dispose();
            }
        });

        TAbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TravelAdvisorLogin travelAdvisorLogin = new TravelAdvisorLogin();
                travelAdvisorLogin.setVisible(true);
                dispose();
            }
        });

    }

}
