import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditUserFrame extends JFrame {
    private JTextField usernameField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String userId;

    public AddEditUserFrame(String userId) {
        this.userId = userId;
        dbHelper = new DatabaseHelper();
        setTitle(userId == null ? "Add User" : "Edit User");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel roleLabel = new JLabel("Role:");
        String[] roleOptions = {"Admin", "Doctor", "Staff"};
        roleComboBox = new JComboBox<>(roleOptions);

        saveButton = new JButton(userId == null ? "Add" : "Update");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(roleLabel, gbc);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        if (userId != null) {
            loadUserData(userId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userId == null) {
                    addUser();
                } else {
                    updateUser(userId);
                }
                dispose();
            }
        });
    }

    private void loadUserData(String userId) {
        String query = "SELECT username, role FROM Users WHERE user_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, userId);
        try {
            if (resultSet.next()) {
                usernameField.setText(resultSet.getString("username"));
                roleComboBox.setSelectedItem(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        String query = "INSERT INTO Users (username, role) VALUES (?, ?)";
        dbHelper.executeUpdate(query, usernameField.getText(), roleComboBox.getSelectedItem());
    }

    private void updateUser(String userId) {
        String query = "UPDATE Users SET username = ?, role = ? WHERE user_id = ?";
        dbHelper.executeUpdate(query, usernameField.getText(), roleComboBox.getSelectedItem(), userId);
    }
}
