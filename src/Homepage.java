import javax.swing.*;

public class Homepage {
    private JButton loginButton;
    private JPanel panel1;
    private JButton exitButton;
    private JLabel ImageLogo;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Homepage");
        frame.setContentPane(new Homepage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000,700);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageLogo = new JLabel(new ImageIcon("data/Airvialogo.png"));
        ImageLogo.setSize(200,200);

    }
}
