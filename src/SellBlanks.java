import javax.swing.*;
import java.awt.*;

public class SellBlanks extends JFrame {
    private JPanel SBframe;
    private JLabel TravelAdvisorImage;
    private JLabel TravelAdvisorName;
    private JButton sellBlankButton;
    private JButton exitButton;
    private JTextField EmailField;
    private JComboBox ToBox;
    private JComboBox FromBox;
    private JCheckBox internationalCheckBox;
    private JCheckBox domesticCheckBox;
    private JComboBox PaymentTypeBox;
    private JComboBox CurrencyBox;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JComboBox TAlist;
    private JTextField FirstName;
    private JTextField LastName;
    private JLabel TotalBeforeLabel;
    private JLabel DiscountLabel;
    private JLabel FinalTotalLabel;

    SellBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Login");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(SBframe);

        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);


    }
}
