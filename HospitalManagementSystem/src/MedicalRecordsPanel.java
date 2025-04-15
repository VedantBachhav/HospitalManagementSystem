import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalRecordsPanel extends JPanel {
    private JTable medicalRecordsTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public MedicalRecordsPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Medical Record");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Patient");
        tableModel.addColumn("Doctor");
        tableModel.addColumn("Date");
        tableModel.addColumn("Diagnosis");
        tableModel.addColumn("Treatment");

        medicalRecordsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(medicalRecordsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadMedicalRecords();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditMedicalRecordFrame(null).setVisible(true);
                loadMedicalRecords(); // Refresh the table after adding a new medical record
            }
        });
    }

    private void loadMedicalRecords() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT m.record_id, p.first_name AS patient_name, d.first_name AS doctor_name, m.record_date, m.diagnosis, m.treatment " +
                "FROM MedicalRecords m " +
                "JOIN Patients p ON m.patient_id = p.patient_id " +
                "JOIN Doctors d ON m.doctor_id = d.doctor_id";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("record_id"),
                        resultSet.getString("patient_name"),
                        resultSet.getString("doctor_name"),
                        resultSet.getString("record_date"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("treatment")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
