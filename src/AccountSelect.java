import javax.swing.*;
import java.awt.*;

public class AccountSelect extends JFrame {


    private JPanel ACpanel;
    private JButton AdminButton;
    private JButton OfficeManagerButton;
    private JButton SAbutton;
    private JLabel HeaderLabel;

    AccountSelect(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Account Select");
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
        SAbutton.setIcon(salesAdvisorIcon);

    }

}
