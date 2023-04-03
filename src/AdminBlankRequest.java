import javax.swing.*;

public class AdminBlankRequest extends JFrame {
    private JPanel ABRpanel;
    AdminBlankRequest(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Blank Request");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABRpanel);
    }
}
