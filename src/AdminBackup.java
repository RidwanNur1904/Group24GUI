import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class AdminBackup  extends JFrame{
    private JPanel ABpanel;
    private JLabel AdminImage;
    private JComboBox TableList;
    private JButton backupButton;
    private JButton restoreButton;
    private JButton exitButton;

    AdminBackup(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Admin table backup");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(ABpanel);

        // Populate JComboBox with table names
        try {
            // Connect to the MySQL database
            String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace with your database URL
            String user = "in2018g24_a"; // Replace with your database username
            String password = "GTrSnz41"; // Replace with your database password

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            Vector<String> tableNames = new Vector<>();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString(1));
            }

            TableList.setModel(new DefaultComboBoxModel<>(tableNames));
            resultSet.close();
            statement.close();
            connection.close();
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
        exitButton.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTableName = (String) TableList.getSelectedItem();
                String backupTableName = selectedTableName + "_Backup";

                try {
                    // Connect to the MySQL database
                    String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace with your database URL
                    String user = "in2018g24_a"; // Replace with your database username
                    String password = "GTrSnz41"; // Replace with your database password

                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();

                    // Create the backup table
                    String createTableQuery = "CREATE TABLE " + backupTableName + " LIKE " + selectedTableName;
                    statement.executeUpdate(createTableQuery);

                    // Copy data from the selected table to the backup table
                    String copyDataQuery = "INSERT INTO " + backupTableName + " SELECT * FROM " + selectedTableName;
                    statement.executeUpdate(copyDataQuery);

                    JOptionPane.showMessageDialog(null, "Backup created successfully!");
                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to create backup: " + ex.getMessage());
                }
            }
        });

        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTableName = (String) TableList.getSelectedItem();
                String backupTableName = selectedTableName + "_Backup";

                try {
                    // Connect to the MySQL database
                    String url = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24"; // Replace with your database URL
                    String user = "in2018g24_a"; // Replace with your database username
                    String password = "GTrSnz41"; // Replace with your database password

                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();

                    // Check if the original table exists
                    DatabaseMetaData metaData = connection.getMetaData();
                    ResultSet resultSet = metaData.getTables(null, null, selectedTableName, null);
                    boolean tableExists = resultSet.next();
                    resultSet.close();

                    if (tableExists) {
                        // Drop the original table
                        String dropOriginalTableQuery = "DROP TABLE IF EXISTS " + selectedTableName;
                        statement.executeUpdate(dropOriginalTableQuery);
                    }

                    // Rename the backup table to the original table name
                    String renameTableQuery = "RENAME TABLE " + backupTableName + " TO " + selectedTableName;
                    statement.executeUpdate(renameTableQuery);

                    JOptionPane.showMessageDialog(null, "Table restored successfully!");

                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database backup not found!");
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminOptions adminOptions = new AdminOptions();
                adminOptions.setVisible(true);
            }
        });
    }
}
