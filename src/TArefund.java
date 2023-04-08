import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class TArefund extends JFrame {

    private JPanel RefundPanel;
    private JLabel TravelAdvisorImage;
    private JComboBox TAlist;
    private JComboBox BlankList;
    private JButton initiateRefundButton;
    private JButton exitButton;

    TArefund(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Travel Advisor Options");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(RefundPanel);

        // Establish connection to MySQL database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24",
                    "in2018g24_a", "GTrSnz41"); // Replace with your actual database connection details

            // Query the SoldBlanks table to retrieve the username values
            String query = "SELECT username FROM SoldBlanks";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Populate the TAlist JComboBox with retrieved username values
            ArrayList<String> usernameList = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernameList.add(username);
            }
            TAlist.setModel(new DefaultComboBoxModel<>(usernameList.toArray(new String[0])));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TAlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected username value
                String selectedUsername = (String) TAlist.getSelectedItem();

                // Establish connection to MySQL database
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24",
                            "in2018g24_a", "GTrSnz41"); // Replace with your actual database connection details

                    // Query the SoldBlanks table to retrieve the BlankID values for the selected username
                    String query = "SELECT BlankID FROM SoldBlanks WHERE username = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, selectedUsername);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Populate the BlankList JComboBox with retrieved BlankID values
                    ArrayList<String> blankIdList = new ArrayList<>();
                    while (resultSet.next()) {
                        String blankId = resultSet.getString("BlankID");
                        blankIdList.add(blankId);
                    }
                    BlankList.setModel(new DefaultComboBoxModel<>(blankIdList.toArray(new String[0])));

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        ImageIcon originalIcon = new ImageIcon("data/SalesAdvisor.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        TravelAdvisorImage.setIcon(scaledIcon);
    }
}
