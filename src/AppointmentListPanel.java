import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentListPanel extends JPanel {
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public AppointmentListPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Appointment");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Patient");
        tableModel.addColumn("Doctor");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");
        tableModel.addColumn("Status");

        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        loadAppointments();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditAppointmentFrame(null).setVisible(true);
                loadAppointments(); // Refresh the table after adding a new appointment
            }
        });
    }

    private void loadAppointments() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT a.appointment_id, p.first_name AS patient_name, d.first_name AS doctor_name, a.appointment_date, a.appointment_time, a.status " +
                "FROM Appointments a " +
                "JOIN Patients p ON a.patient_id = p.patient_id " +
                "JOIN Doctors d ON a.doctor_id = d.doctor_id";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("appointment_id"),
                        resultSet.getString("patient_name"),
                        resultSet.getString("doctor_name"),
                        resultSet.getString("appointment_date"),
                        resultSet.getString("appointment_time"),
                        resultSet.getString("status")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
