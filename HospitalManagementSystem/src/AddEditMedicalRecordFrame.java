import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditMedicalRecordFrame extends JFrame {
    private JComboBox<String> patientComboBox, doctorComboBox;
    private JTextField dateField, diagnosisField, treatmentField;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String recordId;

    public AddEditMedicalRecordFrame(String recordId) {
        this.recordId = recordId;
        dbHelper = new DatabaseHelper();
        setTitle(recordId == null ? "Add Medical Record" : "Edit Medical Record");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel patientLabel = new JLabel("Patient:");
        patientComboBox = new JComboBox<>(getPatientNames());
        JLabel doctorLabel = new JLabel("Doctor:");
        doctorComboBox = new JComboBox<>(getDoctorNames());
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField(20);
        JLabel diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisField = new JTextField(20);
        JLabel treatmentLabel = new JLabel("Treatment:");
        treatmentField = new JTextField(20);

        saveButton = new JButton(recordId == null ? "Add" : "Update");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(patientLabel, gbc);
        gbc.gridx = 1;
        panel.add(patientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(doctorLabel, gbc);
        gbc.gridx = 1;
        panel.add(doctorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dateLabel, gbc);
        gbc.gridx = 1;
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(diagnosisLabel, gbc);
        gbc.gridx = 1;
        panel.add(diagnosisField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(treatmentLabel, gbc);
        gbc.gridx = 1;
        panel.add(treatmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        if (recordId != null) {
            loadMedicalRecordData(recordId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordId == null) {
                    addMedicalRecord();
                } else {
                    updateMedicalRecord(recordId);
                }
                dispose();
            }
        });
    }

    private String[] getPatientNames() {
        String query = "SELECT patient_id, CONCAT(first_name, ' ', last_name) AS name FROM Patients";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            int size = 0;
            while (resultSet.next()) {
                size++;
            }
            resultSet.beforeFirst();
            String[] names = new String[size];
            int i = 0;
            while (resultSet.next()) {
                names[i++] = resultSet.getString("name");
            }
            return names;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private String[] getDoctorNames() {
        String query = "SELECT doctor_id, CONCAT(first_name, ' ', last_name) AS name FROM Doctors";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            int size = 0;
            while (resultSet.next()) {
                size++;
            }
            resultSet.beforeFirst();
            String[] names = new String[size];
            int i = 0;
            while (resultSet.next()) {
                names[i++] = resultSet.getString("name");
            }
            return names;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private void loadMedicalRecordData(String recordId) {
        String query = "SELECT p.first_name AS patient_name, d.first_name AS doctor_name, m.record_date, m.diagnosis, m.treatment " +
                "FROM MedicalRecords m " +
                "JOIN Patients p ON m.patient_id = p.patient_id " +
                "JOIN Doctors d ON m.doctor_id = d.doctor_id " +
                "WHERE m.record_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, recordId);
        try {
            if (resultSet.next()) {
                patientComboBox.setSelectedItem(resultSet.getString("patient_name"));
                doctorComboBox.setSelectedItem(resultSet.getString("doctor_name"));
                dateField.setText(resultSet.getString("record_date"));
                diagnosisField.setText(resultSet.getString("diagnosis"));
                treatmentField.setText(resultSet.getString("treatment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMedicalRecord() {
        String patientName = (String) patientComboBox.getSelectedItem();
        String doctorName = (String) doctorComboBox.getSelectedItem();
        String query = "INSERT INTO MedicalRecords (patient_id, doctor_id, record_date, diagnosis, treatment) " +
                "VALUES ((SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "(SELECT doctor_id FROM Doctors WHERE CONCAT(first_name, ' ', last_name) = ?), ?, ?, ?)";
        dbHelper.executeUpdate(query, patientName, doctorName, dateField.getText(), diagnosisField.getText(), treatmentField.getText());
    }

    private void updateMedicalRecord(String recordId) {
        String patientName = (String) patientComboBox.getSelectedItem();
        String doctorName = (String) doctorComboBox.getSelectedItem();
        String query = "UPDATE MedicalRecords SET patient_id = (SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "doctor_id = (SELECT doctor_id FROM Doctors WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "record_date = ?, diagnosis = ?, treatment = ? WHERE record_id = ?";
        dbHelper.executeUpdate(query, patientName, doctorName, dateField.getText(), diagnosisField.getText(), treatmentField.getText(), recordId);
    }
}
