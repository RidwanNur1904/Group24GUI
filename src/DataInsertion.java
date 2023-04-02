import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class DataInsertion extends JFrame {
    private JPanel DIpanel;
    private JTextField StartBLankID;
    private JTextField EndBlankID;
    private JTextField Date;
    private JButton back;
    private JLabel AdminImage;
    private JButton insertDataButton;

    private static final String Start_PLACEHOLDER = "StartBlankID";
    private static final String End_PLACEHOLDER = "EndBlankID";
    private static final String Date_PLACEHOLDER = "yyyy-mm-dd";

    DataInsertion(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setTitle("Insert New data");
        this.setLocationRelativeTo(null); // set location to center of the screen
        this.setContentPane(DIpanel);

        ImageIcon logoutIcon = new ImageIcon("data/Back.png"); // Replace this with the actual path to your logout image file
        Image logoutImage = logoutIcon.getImage();
        Image scaledLogoutImage = logoutImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoutIcon = new ImageIcon(scaledLogoutImage);
        back.setIcon(scaledLogoutIcon); // Set the icon of the JLabel to the scaled logout image

        //Set font size to 24 for Fields
        Font font = new Font("Calibri", Font.BOLD, 24);
        StartBLankID.setFont(font);
        EndBlankID.setFont(font);
        Date.setFont(font);

        // Add focus listeners to clear and set placeholder text
        StartBLankID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (StartBLankID.getText().equals(Start_PLACEHOLDER)) {
                    StartBLankID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (StartBLankID.getText().isEmpty()) {
                    StartBLankID.setText(Start_PLACEHOLDER);
                }
            }
        });

        EndBlankID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (EndBlankID.getText().equals(End_PLACEHOLDER)) {
                    EndBlankID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (EndBlankID.getText().isEmpty()) {
                    EndBlankID.setText(End_PLACEHOLDER);
                }
            }
        });

        Date.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Date.getText().equals(Date_PLACEHOLDER)) {
                    Date.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Date.getText().isEmpty()) {
                    Date.setText(Date_PLACEHOLDER);
                }
            }
        });

        Border border = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK); // Create black bottom border
        StartBLankID.setBorder(border);
        EndBlankID.setBorder(border);
        Date.setBorder(border);

        ImageIcon originalIcon = new ImageIcon("data/Admin.png"); // Replace this with the actual path to your image file
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        AdminImage.setIcon(scaledIcon);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CheckStockList checkStockList = new CheckStockList();
                checkStockList.setVisible(true);
            }
        });
        insertDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startBlankID = StartBLankID.getText();
                String endBlankID = EndBlankID.getText();
                String date = Date.getText();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g24", "in2018g24_a", "GTrSnz41");
                    Statement stmt = con.createStatement();

                    for (int i = Integer.parseInt(startBlankID); i <= Integer.parseInt(endBlankID); i++) {
                        String insertQuery = "INSERT INTO BlankStock (BlankID, Date) VALUES ('" + i + "', '" + date + "')";
                        stmt.executeUpdate(insertQuery);
                    }

                    JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                    con.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
    }
}
