package views;

import contacts.Contact;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;

public class ModifContactFrame extends JFrame {
    private GestContact gestContact;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 10);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color BUTTON_COLOR = new Color(184, 53, 86);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(40, 40, 40, 40);
    private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);

    public ModifContactFrame(GestContact gestContact) {
        this.gestContact = gestContact;
        try {
            initModif();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            dispose();
        }
    }

    private void initModif() throws SQLException {
        setTitle("Modifier un Contact");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(PANEL_BORDER);

        JLabel idLabel = new JLabel("ID du contact :");
        idLabel.setFont(LABEL_FONT);
        idLabel.setForeground(TEXT_COLOR);
        JTextField idField = new JTextField();
        idField.setFont(TEXT_FIELD_FONT);
        idField.setBackground(BACKGROUND_COLOR);
        idField.setForeground(TEXT_COLOR);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JLabel nomLabel = new JLabel("Nouveau nom :");
        nomLabel.setFont(LABEL_FONT);
        nomLabel.setForeground(TEXT_COLOR);
        JTextField nomField = new JTextField();
        nomField.setFont(TEXT_FIELD_FONT);
        nomField.setBackground(BACKGROUND_COLOR);
        nomField.setForeground(TEXT_COLOR);
        inputPanel.add(nomLabel);
        inputPanel.add(nomField);

        JLabel prenomLabel = new JLabel("Nouveau prénom :");
        prenomLabel.setFont(LABEL_FONT);
        prenomLabel.setForeground(TEXT_COLOR);
        JTextField prenomField = new JTextField();
        prenomField.setFont(TEXT_FIELD_FONT);
        prenomField.setBackground(BACKGROUND_COLOR);
        prenomField.setForeground(TEXT_COLOR);
        inputPanel.add(prenomLabel);
        inputPanel.add(prenomField);

        JLabel telLabel = new JLabel("Nouveau téléphone :");
        telLabel.setFont(LABEL_FONT);
        telLabel.setForeground(TEXT_COLOR);
        JTextField telField = new JTextField();
        telField.setFont(TEXT_FIELD_FONT);
        telField.setBackground(BACKGROUND_COLOR);
        telField.setForeground(TEXT_COLOR);
        inputPanel.add(telLabel);
        inputPanel.add(telField);

        JLabel emailLabel = new JLabel("Nouvel email :");
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setForeground(TEXT_COLOR);
        JTextField emailField = new JTextField();
        emailField.setFont(TEXT_FIELD_FONT);
        emailField.setBackground(BACKGROUND_COLOR);
        emailField.setForeground(TEXT_COLOR);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton modifButton = new JButton("Modifier");
        modifButton.setFont(BUTTON_FONT);
        modifButton.setBackground(BUTTON_COLOR);
        modifButton.setForeground(BUTTON_TEXT_COLOR);
        modifButton.setBorder(BUTTON_BORDER);
        modifButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setBackground(BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);
        cancelButton.setBorder(BUTTON_BORDER);
        cancelButton.setFocusPainted(false);

        modifButton.addActionListener(e -> {
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

                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String telText = telField.getText().trim();
                String email = emailField.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || telText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Nom, prénom et téléphone sont obligatoires !",
                            "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int tel;
                try {
                    tel = Integer.parseInt(telText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Le téléphone doit être un nombre !",
                            "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Contact updatedContact = new Contact();
                updatedContact.setId(id);
                updatedContact.setNom(nom);
                updatedContact.setPrenom(prenom);
                updatedContact.setTel(tel);
                updatedContact.setEmail(email);

                if (gestContact.modifierContact(updatedContact)) {
                    JOptionPane.showMessageDialog(this,
                            "Contact modifié avec succès !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : données invalides ",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(modifButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new ModifContactFrame(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }
}