package views;

import contacts.Contact;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RechContactFrame extends JFrame {
    private GestContact gestContact;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 10);
    private static final Font TEXT_AREA_FONT = new Font("Segoe UI", Font.PLAIN, 10);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color BUTTON_COLOR = new Color(184, 53, 86);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);

    public RechContactFrame(GestContact gestContact) {
        this.gestContact = gestContact;
        try {
            initRecherche();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            dispose();
        }
    }

    private void initRecherche() throws SQLException {
        setTitle("Chercher un Contact");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(PANEL_BORDER);

        JLabel rechLabel = new JLabel("Nom ou prénom à chercher :");
        rechLabel.setFont(LABEL_FONT);
        rechLabel.setForeground(TEXT_COLOR);
        JTextField rechField = new JTextField();
        rechField.setFont(TEXT_FIELD_FONT);
        rechField.setBackground(BACKGROUND_COLOR);
        rechField.setForeground(TEXT_COLOR);
        inputPanel.add(rechLabel);
        inputPanel.add(rechField);

        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setFont(TEXT_AREA_FONT);
        resultArea.setBackground(BACKGROUND_COLOR);
        resultArea.setForeground(TEXT_COLOR);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton chercherButton = new JButton("Chercher");
        chercherButton.setFont(BUTTON_FONT);
        chercherButton.setBackground(BUTTON_COLOR);
        chercherButton.setForeground(BUTTON_TEXT_COLOR);
        chercherButton.setBorder(BUTTON_BORDER);
        chercherButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setBackground(BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);
        cancelButton.setBorder(BUTTON_BORDER);
        cancelButton.setFocusPainted(false);

        chercherButton.addActionListener(e -> {
            try {
                String cherchText = rechField.getText().trim();
                List<Contact> results = gestContact.rechercherContact(cherchText);
                resultArea.setText("");

                if (results.isEmpty()) {
                    resultArea.setText("Aucun contact trouvé pour : " + cherchText);
                } else {
                    resultArea.append("Résultats pour '" + cherchText + "':\n\n");
                    for (Contact contact : results) {
                        resultArea.append("ID : " + contact.getId() + "\n");
                        resultArea.append("Nom : " + contact.getNom() + "\n");
                        resultArea.append("Prénom : " + contact.getPrenom() + "\n");
                        resultArea.append("Téléphone : " + contact.getTel() + "\n");
                        resultArea.append("Email : " + (contact.getEmail().isEmpty() ? "N/A" : contact.getEmail()) + "\n");
                        resultArea.append("----------------------------\n");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(chercherButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new RechContactFrame(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }
}