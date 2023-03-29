import javax.swing.*;

public class OfficeManagerOptions extends JFrame {

    private JPanel OMOpanel;

    OfficeManagerOptions(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin Login");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMOpanel);
    }
}
