package views;

import contacts.Contact;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;

public class SuppContactFrame extends JFrame {
    private GestContact gestContact;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 10);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color BUTTON_COLOR = new Color(184, 53, 86);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);

    public SuppContactFrame(GestContact gestContact) {
        this.gestContact = gestContact;
        try {
            initSupp();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            dispose();
        }
    }

    private void initSupp() throws SQLException {
        setTitle("Supprimer un Contact");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(PANEL_BORDER);

        JLabel idLabel = new JLabel("ID du contact à supprimer :");
        idLabel.setFont(LABEL_FONT);
        idLabel.setForeground(TEXT_COLOR);
        JTextField idField = new JTextField();
        idField.setFont(TEXT_FIELD_FONT);
        idField.setBackground(BACKGROUND_COLOR);
        idField.setForeground(TEXT_COLOR);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.setFont(BUTTON_FONT);
        supprimerButton.setBackground(BUTTON_COLOR);
        supprimerButton.setForeground(BUTTON_TEXT_COLOR);
        supprimerButton.setBorder(BUTTON_BORDER);
        supprimerButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setBackground(BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);
        cancelButton.setBorder(BUTTON_BORDER);
        cancelButton.setFocusPainted(false);

        supprimerButton.addActionListener(e -> {
            try {
                String idText = idField.getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "L'ID est obligatoire !",
                            "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "L'ID doit être un nombre valide !",
                            "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Contact existingContact = gestContact.trouverContactParId(id);
                if (existingContact == null) {
                    JOptionPane.showMessageDialog(this,
                            "Aucun contact trouvé avec cet ID !",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestContact.supprimerContact(id)) {
                    JOptionPane.showMessageDialog(this,
                            "Contact supprimé avec succès !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de la suppression !",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(supprimerButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new SuppContactFrame(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }
}