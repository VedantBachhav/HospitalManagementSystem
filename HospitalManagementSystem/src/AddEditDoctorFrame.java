import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEditDoctorFrame extends JFrame {
    private JTextField firstNameField, lastNameField, specializationField, contactField, addressField;
    private JButton saveButton;
    private DatabaseHelper dbHelper;
    private String doctorId;

    public AddEditDoctorFrame(String doctorId) {
        this.doctorId = doctorId;
        dbHelper = new DatabaseHelper();
        setTitle(doctorId == null ? "Add Doctor" : "Edit Doctor");
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
        JLabel specializationLabel = new JLabel("Specialization:");
        specializationField = new JTextField(20);
        JLabel contactLabel = new JLabel("Contact Number:");
        contactField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        saveButton = new JButton(doctorId == null ? "Add" : "Update");

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
        panel.add(specializationLabel, gbc);
        gbc.gridx = 1;
        panel.add(specializationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(contactLabel, gbc);
        gbc.gridx = 1;
        panel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addressLabel, gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        if (doctorId != null) {
            loadDoctorData(doctorId);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doctorId == null) {
                    addDoctor();
                } else {
                    updateDoctor(doctorId);
                }
                dispose();
            }
        });
    }

    private void loadDoctorData(String doctorId) {
        String query = "SELECT * FROM Doctors WHERE doctor_id = ?";
        ResultSet resultSet = dbHelper.executeQuery(query, doctorId);
        try {
            if (resultSet.next()) {
                firstNameField.setText(resultSet.getString("first_name"));
                lastNameField.setText(resultSet.getString("last_name"));
                specializationField.setText(resultSet.getString("specialization"));
                contactField.setText(resultSet.getString("contact_number"));
                addressField.setText(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDoctor() {
        String query = "INSERT INTO Doctors (first_name, last_name, specialization, contact_number, address) VALUES (?, ?, ?, ?, ?)";
        dbHelper.executeUpdate(query, firstNameField.getText(), lastNameField.getText(), specializationField.getText(), contactField.getText(), addressField.getText());
    }

    private void updateDoctor(String doctorId) {
        String query = "UPDATE Doctors SET first_name = ?, last_name = ?, specialization = ?, contact_number = ?, address = ? WHERE doctor_id = ?";
        dbHelper.executeUpdate(query, firstNameField.getText(), lastNameField.getText(), specializationField.getText(), contactField.getText(), addressField.getText(), doctorId);
    }
}
