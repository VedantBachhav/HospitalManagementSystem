import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private DatabaseHelper dbHelper;

    public SettingsPanel() {
        dbHelper = new DatabaseHelper();
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel setting1Label = new JLabel("Setting 1:");
        JTextField setting1Field = new JTextField(20);
        JLabel setting2Label = new JLabel("Setting 2:");
        JTextField setting2Field = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(setting1Label, gbc);
        gbc.gridx = 1;
        formPanel.add(setting1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(setting2Label, gbc);
        gbc.gridx = 1;
        formPanel.add(setting2Field, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your save logic here
                JOptionPane.showMessageDialog(SettingsPanel.this, "Settings saved!");
            }
        });
    }
}
