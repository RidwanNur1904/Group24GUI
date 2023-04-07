import javax.swing.*;

public class IndReport extends JFrame {
    private JPanel IndPanel;

    IndReport(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Individual Reports");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(IndPanel);
    }
}
