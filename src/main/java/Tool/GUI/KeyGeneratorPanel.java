package Tool.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import Tool.RSA.RSA;

public class KeyGeneratorPanel extends JPanel {

    private JTextField privateKeyPath;
    private JTextField publicKeyPath;
    private JLabel statusLabel;

    public KeyGeneratorPanel() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(new Color(245, 245, 245));

        // ================= TITLE =================

        JLabel titleLabel = new JLabel("TẠO KHÓA RSA");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ================= PRIVATE KEY PATH =================

        privateKeyPath = new JTextField();
        mainPanel.add(buildPathRow(
                "Lưu vị trí Private Key (Khóa bí mật):",
                privateKeyPath
        ));

        mainPanel.add(Box.createVerticalStrut(20));

        // ================= PUBLIC KEY PATH =================

        publicKeyPath = new JTextField();
        mainPanel.add(buildPathRow(
                "Lưu vị trí Public Key (Khóa công khai):",
                publicKeyPath
        ));

        mainPanel.add(Box.createVerticalStrut(30));

        // ================= GENERATE BUTTON =================

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnGenerate = new JButton("Tạo Khóa");
        btnGenerate.setPreferredSize(new Dimension(140, 36));

        btnGenerate.addActionListener(e -> {

            String pri = privateKeyPath.getText().trim();
            String pub = publicKeyPath.getText().trim();

            if (pri.isEmpty() || pub.isEmpty()) {
                setStatus("Vui lòng chọn vị trí lưu khóa.");
                return;
            }

            try {

                Map<String, String> keys = RSA.genKey(2048);

                // Ghi khóa ra file tại đường dẫn đã chọn
                writeToFile(pri, keys.get("privateKey"));
                writeToFile(pub, keys.get("publicKey"));

                setStatus("Tạo khóa thành công!");

                JOptionPane.showMessageDialog(
                        this,
                        "Tạo khóa thành công!"
                );

            } catch (Exception ex) {

                setStatus("Lỗi: " + ex.getMessage());

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        buttonPanel.add(btnGenerate);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ================= STATUS =================

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(statusLabel);

        add(mainPanel, BorderLayout.NORTH);
    }

    private void writeToFile(String path, String content) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        }
    }

    private JPanel buildPathRow(String labelText, JTextField field) {

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(label);
        row.add(Box.createVerticalStrut(5));

        JPanel fieldRow = new JPanel(new BorderLayout(8, 0));
        fieldRow.setOpaque(false);
        fieldRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton browseBtn = new JButton("Chọn");
        browseBtn.setPreferredSize(new Dimension(80, 28));

        browseBtn.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showSaveDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();
                field.setText(file.getAbsolutePath());
            }
        });

        fieldRow.add(field, BorderLayout.CENTER);
        fieldRow.add(browseBtn, BorderLayout.EAST);

        row.add(fieldRow);

        return row;
    }

    private void setStatus(String msg) {
        statusLabel.setText("Status: " + msg);
    }
}