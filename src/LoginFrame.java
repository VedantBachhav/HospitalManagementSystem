import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private DatabaseHelper dbHelper;

    public LoginFrame() {
        dbHelper = new DatabaseHelper();
        setTitle("Hospital Management System - Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set a background image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Abhijit\\Downloads\\istockphoto-2099357117-612x612.jpg"); // Replace with your image path
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());

        // Create a panel for the login form
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = authenticateUser(username, password);
                if (role != null) {
                    openDashboard(role);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        backgroundLabel.add(loginPanel, BorderLayout.CENTER);
        add(backgroundLabel);
    }

    private String authenticateUser(String username, String password) {
        String query = "SELECT role FROM Users WHERE username = ? AND password = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, username, password);
        try {
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openDashboard(String role) {
        switch (role) {
            case "Admin":
                new AdminDashboardFrame().setVisible(true);
                break;
            case "Doctor":
                new DoctorDashboardFrame().setVisible(true);
                break;
            case "Staff":
                new StaffDashboardFrame().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
