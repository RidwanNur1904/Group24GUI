import javax.swing.*;

public class AdminBlankManagement extends JFrame {
    private JPanel ABMpanel;

    AdminBlankManagement(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Blank Manager");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABMpanel);
    }
}
