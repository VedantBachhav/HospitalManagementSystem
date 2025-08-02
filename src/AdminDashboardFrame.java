import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardFrame extends JFrame {
    public AdminDashboardFrame() {
        setTitle("Hospital Management System - Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(11, 1));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton patientsButton = new JButton("Patients");
        JButton doctorsButton = new JButton("Doctors");
        JButton appointmentsButton = new JButton("Appointments");
        JButton medicalRecordsButton = new JButton("Medical Records");
        JButton billingButton = new JButton("Billing");
        JButton reportsButton = new JButton("Reports");
        JButton userManagementButton = new JButton("User Management");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Logout");

        menuPanel.add(patientsButton);
        menuPanel.add(doctorsButton);
        menuPanel.add(appointmentsButton);
        menuPanel.add(medicalRecordsButton);
        menuPanel.add(billingButton);
        menuPanel.add(reportsButton);
        menuPanel.add(userManagementButton);
        menuPanel.add(settingsButton);
        menuPanel.add(logoutButton);

        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Welcome to the Admin Dashboard", SwingConstants.CENTER), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new PatientListPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        doctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new DoctorListPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        appointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new AppointmentListPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        medicalRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new MedicalRecordsPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new BillingPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new ReportsPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new UserManagementPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new SettingsPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
