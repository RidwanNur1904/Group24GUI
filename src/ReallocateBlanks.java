import javax.swing.*;

public class ReallocateBlanks extends JFrame {
    private JPanel ReallocatePanel;

    ReallocateBlanks(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Insert New data");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ReallocatePanel);
    }
}
