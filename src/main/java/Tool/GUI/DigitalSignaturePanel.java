package Tool.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;

public class DigitalSignaturePanel extends JPanel {

    private JTextArea invoiceDataArea;
    private JTextField privateKeyField;
    private JTextArea signatureArea;
    private JLabel statusLabel;

    public DigitalSignaturePanel() {

        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // ================= TITLE =================

        JLabel titleLabel = new JLabel("DIGITAL SIGNATURE TOOL");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ================= INVOICE DATA =================

        JLabel invoiceLabel = new JLabel("Invoice Data (JSON/Text):");
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(invoiceLabel, gbc);

        invoiceDataArea = new JTextArea(8, 50);
        invoiceDataArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane invoiceScroll = new JScrollPane(invoiceDataArea);

        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(invoiceScroll, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        // ================= PRIVATE KEY =================

        JLabel keyLabel = new JLabel("Private Key:");
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(keyLabel, gbc);

        JPanel keyPanel = new JPanel(new BorderLayout(8, 0));
        keyPanel.setOpaque(false);

        privateKeyField = new JTextField();
        JButton browseBtn = new JButton("Browse");

        browseBtn.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();
                privateKeyField.setText(file.getAbsolutePath());
            }
        });

        keyPanel.add(privateKeyField, BorderLayout.CENTER);
        keyPanel.add(browseBtn, BorderLayout.EAST);

        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(keyPanel, gbc);

        // ================= HASH =================

        JLabel hashLabel = new JLabel("Signature Algorithm: SHA256withRSA");
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(hashLabel, gbc);

        // ================= GENERATE BUTTON =================

        JButton generateBtn = new JButton("Generate Signature");
        generateBtn.setPreferredSize(new Dimension(180, 36));
        generateBtn.addActionListener(e -> generateSignature());

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(generateBtn, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ================= SIGNATURE =================

        JLabel sigLabel = new JLabel("Generated Signature:");
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(sigLabel, gbc);

        signatureArea = new JTextArea(5, 50);
        signatureArea.setEditable(false);
        signatureArea.setLineWrap(true);
        signatureArea.setWrapStyleWord(true);
        JScrollPane sigScroll = new JScrollPane(signatureArea);

        gbc.gridy = row++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(sigScroll, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        // ================= ACTION BUTTONS =================

        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setOpaque(false);

        JButton copyBtn = new JButton("Copy Signature");
        JButton clearBtn = new JButton("Clear");

        copyBtn.addActionListener(e -> {

            String sig = signatureArea.getText().trim();

            if (!sig.isEmpty()) {

                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(sig), null);

                setStatus("Signature copied to clipboard.");
            }
        });

        clearBtn.addActionListener(e -> {

            invoiceDataArea.setText("");
            signatureArea.setText("");
            privateKeyField.setText("");

            setStatus("Ready");
        });

        actionPanel.add(copyBtn);
        actionPanel.add(clearBtn);

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(actionPanel, gbc);

        // ================= STATUS =================

        statusLabel = new JLabel("Status: Ready");

        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(statusLabel, gbc);
    }

    private void generateSignature() {

        String data = invoiceDataArea.getText().trim();
        String keyPath = privateKeyField.getText().trim();

        if (data.isEmpty()) {
            setStatus("Invoice data is empty.");
            return;
        }

        if (keyPath.isEmpty()) {
            setStatus("Private key path is empty.");
            return;
        }

        try {

            setStatus("Signing using SHA256withRSA...");

            String signatureBase64 = SHA256withRSA.signWithKeyFile(data, keyPath);

            signatureArea.setText(signatureBase64);
            setStatus("Signature generated successfully.");

        } catch (Exception ex) {

            setStatus("Lỗi: " + ex.getMessage());
            signatureArea.setText("");
        }
    }

    private void setStatus(String msg) {
        statusLabel.setText("Status: " + msg);
    }
}