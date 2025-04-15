import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffDashboardFrame extends JFrame {
    public StaffDashboardFrame() {
        setTitle("Hospital Management System - Staff Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton patientsButton = new JButton("Patients");
        JButton appointmentsButton = new JButton("Appointments");
        JButton billingButton = new JButton("Billing");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Logout");

        menuPanel.add(patientsButton);
        menuPanel.add(appointmentsButton);
        menuPanel.add(billingButton);
        menuPanel.add(settingsButton);
        menuPanel.add(logoutButton);

        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Welcome to the Staff Dashboard", SwingConstants.CENTER), BorderLayout.CENTER);

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

        appointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new AppointmentListPanel(), BorderLayout.CENTER);
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
