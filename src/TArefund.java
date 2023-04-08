import javax.swing.*;

public class TArefund extends JFrame {

    private JPanel RefundPanel;

    TArefund(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RefundPanel);
    }
}
