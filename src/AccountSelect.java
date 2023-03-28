import javax.swing.*;

public class AccountSelect extends JFrame {


    private JPanel ACpanel;
    private JButton AdminButton;
    private JButton OfficeManagerButton;
    private JButton SAbutton;

    AccountSelect(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Account Select");
        this.setContentPane(ACpanel);
    }

}
