import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorListPanel extends JPanel {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public DoctorListPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Doctor");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Contact Number");
        tableModel.addColumn("Address");

        doctorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        add(scrollPane, BorderLayout.CENTER);

        loadDoctors();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditDoctorFrame(null).setVisible(true);
                loadDoctors(); // Refresh the table after adding a new doctor
            }
        });
    }

    private void loadDoctors() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT * FROM Doctors";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("specialization"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("address")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
