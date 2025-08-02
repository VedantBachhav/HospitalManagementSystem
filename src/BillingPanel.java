import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingPanel extends JPanel {
    private JTable billingTable;
    private DefaultTableModel tableModel;
    private DatabaseHelper dbHelper;

    public BillingPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Bill");
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Patient");
        tableModel.addColumn("Appointment");
        tableModel.addColumn("Date");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Status");

        billingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(billingTable);
        add(scrollPane, BorderLayout.CENTER);

        loadBills();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEditBillFrame(null).setVisible(true);
                loadBills(); // Refresh the table after adding a new bill
            }
        });
    }

    private void loadBills() {
        tableModel.setRowCount(0); // Clear the table
        String query = "SELECT b.bill_id, p.first_name AS patient_name, a.appointment_id, b.bill_date, b.amount, b.status " +
                "FROM Bills b " +
                "JOIN Patients p ON b.patient_id = p.patient_id " +
                "JOIN Appointments a ON b.appointment_id = a.appointment_id";
        ResultSet resultSet = dbHelper.executeQuery(query);
        try {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("bill_id"),
                        resultSet.getString("patient_name"),
                        resultSet.getInt("appointment_id"),
                        resultSet.getString("bill_date"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("status")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
