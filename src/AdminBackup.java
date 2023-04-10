import javax.swing.*;

public class AdminBackup  extends JFrame{
    private JPanel ABpanel;

    AdminBackup(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABpanel);
    }
}
