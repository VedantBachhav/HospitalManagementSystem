import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsPanel extends JPanel {
    private DatabaseHelper dbHelper;

    public ReportsPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[]{"Daily Appointments", "Monthly Billing"});
        JButton generateButton = new JButton("Generate Report");
        topPanel.add(new JLabel("Select Report Type:"));
        topPanel.add(reportTypeComboBox);
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reportType = (String) reportTypeComboBox.getSelectedItem();
                generateReport(reportType, reportArea);
            }
        });
    }

    private void generateReport(String reportType, JTextArea reportArea) {
        String query = "";
        switch (reportType) {
            case "Daily Appointments":
                query = "SELECT a.appointment_id, p.first_name AS patient_name, d.first_name AS doctor_name, a.appointment_date, a.appointment_time, a.status " +
                        "FROM Appointments a " +
                        "JOIN Patients p ON a.patient_id = p.patient_id " +
                        "JOIN Doctors d ON a.doctor_id = d.doctor_id " +
                        "WHERE a.appointment_date = CURDATE()";
                break;
            case "Monthly Billing":
                query = "SELECT b.bill_id, p.first_name AS patient_name, a.appointment_id, b.bill_date, b.amount, b.status " +
                        "FROM Bills b " +
                        "JOIN Patients p ON b.patient_id = p.patient_id " +
                        "JOIN Appointments a ON b.appointment_id = a.appointment_id " +
                        "WHERE MONTH(b.bill_date) = MONTH(CURDATE()) AND YEAR(b.bill_date) = YEAR(CURDATE())";
                break;
        }

        ResultSet resultSet = dbHelper.executeQuery(query);
        StringBuilder report = new StringBuilder();
        try {
            while (resultSet.next()) {
                report.append("ID: ").append(resultSet.getInt("appointment_id")).append("\n");
                report.append("Patient: ").append(resultSet.getString("patient_name")).append("\n");
                report.append("Doctor: ").append(resultSet.getString("doctor_name")).append("\n");
                report.append("Date: ").append(resultSet.getString("appointment_date")).append("\n");
                report.append("Time: ").append(resultSet.getString("appointment_time")).append("\n");
                report.append("Status: ").append(resultSet.getString("status")).append("\n");
                report.append("------------------------\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        reportArea.setText(report.toString());
    }
}
