import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditAppointmentFrame extends JFrame {
    private JComboBox<String> patientComboBox, doctorComboBox;
    private JTextField dateField, timeField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String appointmentId;

    public AddEditAppointmentFrame(String appointmentId) {
        this.appointmentId = appointmentId;
        dbHelper = new DatabaseHelper();
        setTitle(appointmentId == null ? "Add Appointment" : "Edit Appointment");
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
        JLabel timeLabel = new JLabel("Time:");
        timeField = new JTextField(20);
        JLabel statusLabel = new JLabel("Status:");
        String[] statusOptions = {"Scheduled", "Completed", "Cancelled"};
        statusComboBox = new JComboBox<>(statusOptions);

        saveButton = new JButton(appointmentId == null ? "Add" : "Update");

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
        panel.add(timeLabel, gbc);
        gbc.gridx = 1;
        panel.add(timeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(statusLabel, gbc);
        gbc.gridx = 1;
        panel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        if (appointmentId != null) {
            loadAppointmentData(appointmentId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (appointmentId == null) {
                    addAppointment();
                } else {
                    updateAppointment(appointmentId);
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

    private void loadAppointmentData(String appointmentId) {
        String query = "SELECT p.first_name AS patient_name, d.first_name AS doctor_name, a.appointment_date, a.appointment_time, a.status " +
                "FROM Appointments a " +
                "JOIN Patients p ON a.patient_id = p.patient_id " +
                "JOIN Doctors d ON a.doctor_id = d.doctor_id " +
                "WHERE a.appointment_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, appointmentId);
        try {
            if (resultSet.next()) {
                patientComboBox.setSelectedItem(resultSet.getString("patient_name"));
                doctorComboBox.setSelectedItem(resultSet.getString("doctor_name"));
                dateField.setText(resultSet.getString("appointment_date"));
                timeField.setText(resultSet.getString("appointment_time"));
                statusComboBox.setSelectedItem(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAppointment() {
        String patientName = (String) patientComboBox.getSelectedItem();
        String doctorName = (String) doctorComboBox.getSelectedItem();
        String query = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, status) " +
                "VALUES ((SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "(SELECT doctor_id FROM Doctors WHERE CONCAT(first_name, ' ', last_name) = ?), ?, ?, ?)";
        dbHelper.executeUpdate(query, patientName, doctorName, dateField.getText(), timeField.getText(), statusComboBox.getSelectedItem());
    }

    private void updateAppointment(String appointmentId) {
        String patientName = (String) patientComboBox.getSelectedItem();
        String doctorName = (String) doctorComboBox.getSelectedItem();
        String query = "UPDATE Appointments SET patient_id = (SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "doctor_id = (SELECT doctor_id FROM Doctors WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "appointment_date = ?, appointment_time = ?, status = ? WHERE appointment_id = ?";
        dbHelper.executeUpdate(query, patientName, doctorName, dateField.getText(), timeField.getText(), statusComboBox.getSelectedItem(), appointmentId);
    }
}
