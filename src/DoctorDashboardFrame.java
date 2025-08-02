import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorDashboardFrame extends JFrame {
    public DoctorDashboardFrame() {
        setTitle("Hospital Management System - Doctor Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(10, 1));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton appointmentsButton = new JButton("Appointments");
        JButton medicalRecordsButton = new JButton("Medical Records");
        JButton settingsButton = new JButton("Settings");
        JButton logoutButton = new JButton("Logout");

        menuPanel.add(appointmentsButton);
        menuPanel.add(medicalRecordsButton);
        menuPanel.add(settingsButton);
        menuPanel.add(logoutButton);

        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Welcome to the Doctor Dashboard", SwingConstants.CENTER), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

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
