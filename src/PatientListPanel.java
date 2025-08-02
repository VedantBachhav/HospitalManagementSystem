import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientListPanel extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public PatientListPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Patient");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Contact Number");
        tableModel.addColumn("Address");

        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane, BorderLayout.CENTER);

        loadPatients();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditPatientFrame(null).setVisible(true);
                loadPatients(); // Refresh the table after adding a new patient
            }
        });
    }

    private void loadPatients() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT * FROM Patients";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("patient_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("date_of_birth"),
                        resultSet.getString("gender"),
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
