package Tool.GUI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {

        setTitle("DIGITAL SIGNATURE TOOL");
        setSize(850, 700);
        setMinimumSize(new Dimension(700, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Menu Top

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(210, 210, 210)));

        JButton btnKey = new JButton("Tạo khóa");
        JButton btnSign = new JButton("Digital Signature");

        Dimension menuBtnSize = new Dimension(160, 32);
        btnKey.setPreferredSize(menuBtnSize);
        btnSign.setPreferredSize(menuBtnSize);

        menuPanel.add(btnKey);
        menuPanel.add(btnSign);

        add(menuPanel, BorderLayout.NORTH);

        // Content

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new KeyGeneratorPanel(), "KEY");
        contentPanel.add(new DigitalSignaturePanel(), "SIGN");

        add(contentPanel, BorderLayout.CENTER);

        //ActionListener

        btnKey.addActionListener(e ->
                cardLayout.show(contentPanel, "KEY"));

        btnSign.addActionListener(e ->
                cardLayout.show(contentPanel, "SIGN"));

        cardLayout.show(contentPanel, "KEY");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            new MainFrame().setVisible(true);
        });
    }
}