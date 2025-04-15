import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditPatientFrame extends JFrame {
    private JTextField firstNameField, lastNameField, contactField, addressField;
    private JTextField dobField;
    private JComboBox<String> genderComboBox;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String patientId;

    public AddEditPatientFrame(String patientId) {
        this.patientId = patientId;
        dbHelper = new DatabaseHelper();
        setTitle(patientId == null ? "Add Patient" : "Edit Patient");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobField = new JTextField(20);
        JLabel genderLabel = new JLabel("Gender:");
        String[] genderOptions = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genderOptions);
        JLabel contactLabel = new JLabel("Contact Number:");
        contactField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        saveButton = new JButton(patientId == null ? "Add" : "Update");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dobLabel, gbc);
        gbc.gridx = 1;
        panel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(genderLabel, gbc);
        gbc.gridx = 1;
        panel.add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(contactLabel, gbc);
        gbc.gridx = 1;
        panel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(addressLabel, gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        if (patientId != null) {
            loadPatientData(patientId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patientId == null) {
                    addPatient();
                } else {
                    updatePatient(patientId);
                }
                dispose();
            }
        });
    }

    private void loadPatientData(String patientId) {
        String query = "SELECT * FROM Patients WHERE patient_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, patientId);
        try {
            if (resultSet.next()) {
                firstNameField.setText(resultSet.getString("first_name"));
                lastNameField.setText(resultSet.getString("last_name"));
                dobField.setText(resultSet.getString("date_of_birth"));
                genderComboBox.setSelectedItem(resultSet.getString("gender"));
                contactField.setText(resultSet.getString("contact_number"));
                addressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPatient() {
        String query = "INSERT INTO Patients (first_name, last_name, date_of_birth, gender, contact_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        dbHelper.executeUpdate(query, firstNameField.getText(), lastNameField.getText(), dobField.getText(), genderComboBox.getSelectedItem(), contactField.getText(), addressField.getText());
    }

    private void updatePatient(String patientId) {
        String query = "UPDATE Patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, contact_number = ?, address = ? WHERE patient_id = ?";
        dbHelper.executeUpdate(query, firstNameField.getText(), lastNameField.getText(), dobField.getText(), genderComboBox.getSelectedItem(), contactField.getText(), addressField.getText(), patientId);
    }
}
