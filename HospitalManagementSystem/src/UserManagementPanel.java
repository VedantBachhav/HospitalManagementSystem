import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public UserManagementPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add User");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Role");

        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        loadUsers();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditUserFrame(null).setVisible(true);
                loadUsers(); // Refresh the table after adding a new user
            }
        });
    }

    private void loadUsers() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT user_id, username, role FROM Users";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("role")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
