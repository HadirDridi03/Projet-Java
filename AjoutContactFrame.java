package views;

import contacts.Contact;
import service.GestContact;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AjoutContactFrame extends JFrame {
    private GestContact gestContact;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TEXT_FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 10);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color BUTTON_COLOR = new Color(184,53, 86);
    private static final Color CANCEL_BUTTON_COLOR = new Color(184,53, 86);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(200, 200, 200);

    public AjoutContactFrame(GestContact gestContact) {
        this.gestContact = gestContact;
        try {
            initAjout();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            dispose();
        }
    }

    private void initAjout() throws SQLException {
        setTitle("Ajouter un Contact");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel idLabel = new JLabel("ID :");
        idLabel.setFont(LABEL_FONT);
        idLabel.setForeground(TEXT_COLOR);
        JTextField idField = new JTextField();
        idField.setFont(TEXT_FIELD_FONT);
        idField.setBackground(BACKGROUND_COLOR);
        idField.setForeground(TEXT_COLOR);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JLabel nomLabel = new JLabel("Nom :");
        nomLabel.setFont(LABEL_FONT);
        nomLabel.setForeground(TEXT_COLOR);
        JTextField nomField = new JTextField();
        nomField.setFont(TEXT_FIELD_FONT);
        nomField.setBackground(BACKGROUND_COLOR);
        nomField.setForeground(TEXT_COLOR);
        inputPanel.add(nomLabel);
        inputPanel.add(nomField);
        
        JLabel prenomLabel = new JLabel("Prénom :");
        prenomLabel.setFont(LABEL_FONT);
        prenomLabel.setForeground(TEXT_COLOR);
        JTextField prenomField = new JTextField();
        prenomField.setFont(TEXT_FIELD_FONT);
        prenomField.setBackground(BACKGROUND_COLOR);
        prenomField.setForeground(TEXT_COLOR);
        inputPanel.add(prenomLabel);
        inputPanel.add(prenomField);
        
        JLabel telLabel = new JLabel("Téléphone :");
        telLabel.setFont(LABEL_FONT);
        telLabel.setForeground(TEXT_COLOR);
        JTextField telField = new JTextField();
        telField.setFont(TEXT_FIELD_FONT);
        telField.setBackground(BACKGROUND_COLOR);
        telField.setForeground(TEXT_COLOR);
        inputPanel.add(telLabel);
        inputPanel.add(telField);

        JLabel emailLabel = new JLabel("Email :");
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
        
        JButton saveButton = new JButton("Ajouter");
        saveButton.setFont(BUTTON_FONT);
        saveButton.setBackground(BUTTON_COLOR);
        saveButton.setForeground(BUTTON_TEXT_COLOR);
        saveButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setBackground(CANCEL_BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);
     
        cancelButton.setFocusPainted(false);

        saveButton.addActionListener(e -> {
            try {
                String idText = idField.getText().trim();
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String telText = telField.getText().trim();
                String email = emailField.getText().trim();

                if (idText.isEmpty() || nom.isEmpty() || prenom.isEmpty() || telText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "ID, nom, prénom et téléphone sont obligatoires !",
                            "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "L'ID doit être un nombre !",
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

                Contact contact = new Contact(id, nom, prenom, email, tel);

                if (gestContact.ajouterContact(contact)) {
                    JOptionPane.showMessageDialog(this, "Contact ajouté avec succès !");
                    dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new AjoutContactFrame(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }
}