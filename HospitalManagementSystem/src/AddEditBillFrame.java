import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditBillFrame extends JFrame {
    private JComboBox<String> patientComboBox, appointmentComboBox;
    private JTextField dateField, amountField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String billId;

    public AddEditBillFrame(String billId) {
        this.billId = billId;
        dbHelper = new DatabaseHelper();
        setTitle(billId == null ? "Add Bill" : "Edit Bill");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel patientLabel = new JLabel("Patient:");
        patientComboBox = new JComboBox<>(getPatientNames());
        JLabel appointmentLabel = new JLabel("Appointment:");
        appointmentComboBox = new JComboBox<>(getAppointmentIds());
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(20);
        JLabel statusLabel = new JLabel("Status:");
        String[] statusOptions = {"Paid", "Unpaid"};
        statusComboBox = new JComboBox<>(statusOptions);

        saveButton = new JButton(billId == null ? "Add" : "Update");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(patientLabel, gbc);
        gbc.gridx = 1;
        panel.add(patientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(appointmentLabel, gbc);
        gbc.gridx = 1;
        panel.add(appointmentComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dateLabel, gbc);
        gbc.gridx = 1;
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(amountLabel, gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

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

        if (billId != null) {
            loadBillData(billId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (billId == null) {
                    addBill();
                } else {
                    updateBill(billId);
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

    private String[] getAppointmentIds() {
        String query = "SELECT appointment_id FROM Appointments";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            int size = 0;
            while (resultSet.next()) {
                size++;
            }
            resultSet.beforeFirst();
            String[] ids = new String[size];
            int i = 0;
            while (resultSet.next()) {
                ids[i++] = String.valueOf(resultSet.getInt("appointment_id"));
            }
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private void loadBillData(String billId) {
        String query = "SELECT p.first_name AS patient_name, a.appointment_id, b.bill_date, b.amount, b.status " +
                "FROM Bills b " +
                "JOIN Patients p ON b.patient_id = p.patient_id " +
                "JOIN Appointments a ON b.appointment_id = a.appointment_id " +
                "WHERE b.bill_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, billId);
        try {
            if (resultSet.next()) {
                patientComboBox.setSelectedItem(resultSet.getString("patient_name"));
                appointmentComboBox.setSelectedItem(resultSet.getString("appointment_id"));
                dateField.setText(resultSet.getString("bill_date"));
                amountField.setText(String.valueOf(resultSet.getDouble("amount")));
                statusComboBox.setSelectedItem(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBill() {
        String patientName = (String) patientComboBox.getSelectedItem();
        String appointmentId = (String) appointmentComboBox.getSelectedItem();
        String query = "INSERT INTO Bills (patient_id, appointment_id, bill_date, amount, status) " +
                "VALUES ((SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), ?, ?, ?, ?)";
        dbHelper.executeUpdate(query, patientName, appointmentId, dateField.getText(), Double.parseDouble(amountField.getText()), statusComboBox.getSelectedItem());
    }

    private void updateBill(String billId) {
        String patientName = (String) patientComboBox.getSelectedItem();
        String appointmentId = (String) appointmentComboBox.getSelectedItem();
        String query = "UPDATE Bills SET patient_id = (SELECT patient_id FROM Patients WHERE CONCAT(first_name, ' ', last_name) = ?), " +
                "appointment_id = ?, bill_date = ?, amount = ?, status = ? WHERE bill_id = ?";
        dbHelper.executeUpdate(query, patientName, appointmentId, dateField.getText(), Double.parseDouble(amountField.getText()), statusComboBox.getSelectedItem(), billId);
    }
}
