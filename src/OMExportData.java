import javax.swing.*;

public class OMExportData extends JFrame {
    private JPanel OMEpanel;
    private JButton exportTeamDataButton;
    private JButton exportIndividualDataButton;
    private JButton exitButton;

    OMExportData(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Office Manager Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(OMEpanel);
    }
}
