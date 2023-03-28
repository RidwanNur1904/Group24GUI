import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame {
    private JButton loginButton;
    private JPanel homePanel;
    private JButton exitButton;
    private JLabel ImageLogo;

    public Homepage() {
        // Action event to close the program when exit is pressed
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // Action event to move to the AccountSelect Panel
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountSelect accountSelect = new AccountSelect();
                dispose();
                accountSelect.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Homepage HP = new Homepage();
        HP.setContentPane(HP.homePanel);
        HP.setTitle("Homepage");
        HP.setSize(1000,700);
        HP.setVisible(true);
        HP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageLogo = new JLabel(new ImageIcon("data/Airvialogo.png"));
        ImageLogo.setSize(200,200);

    }
}
