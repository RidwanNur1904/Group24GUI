import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class CheckStockList extends JFrame {

    private JPanel CSLpanel;
    private JLabel Administrator;
    private JLabel AdminImage;
    private JButton back;
    private JScrollPane Stocktable;

    CheckStockList(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Check Stock List");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(CSLpanel);

        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Connect to the MySQL database and retrieve data from the BlankStock table
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BlankStock");

            // Create a new DefaultTableModel with column names and row data from the ResultSet
            String[] columnNames = {"BlankID", "Date"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                String id = rs.getString("BlankID"); // Retrieve BlankID as a VARCHAR
                Date date = rs.getDate("Date");
                Object[] row = {id, date};
                model.addRow(row);
            }

            // Create a new JTable with the DefaultTableModel and put it inside a JScrollPane
            JTable table = new JTable(model);
            Stocktable.setViewportView(table);

            // Close the ResultSet, statement, and connection
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image


        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
            }
        });
    }
}
