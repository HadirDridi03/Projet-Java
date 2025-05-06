package views;

import contacts.Contact;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListContactFrame extends JFrame {
    private GestContact gestContact;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private static final Border CONTACT_PANEL_BORDER = BorderFactory.createLineBorder(BORDER_COLOR);

    public ListContactFrame(GestContact gestContact) {
        this.gestContact = gestContact;
        try {
            initList();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            dispose();
        }
    }

    private void initList() throws SQLException {
        setTitle("Liste des Contacts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(PANEL_BORDER);

        List<Contact> contacts = gestContact.listerContacts();

        if (contacts.isEmpty()) {
            JLabel emptyLabel = new JLabel("Aucun contact à afficher", SwingConstants.CENTER);
            emptyLabel.setFont(LABEL_FONT);
            emptyLabel.setForeground(TEXT_COLOR);
            mainPanel.add(emptyLabel);
        } else {
            for (Contact contact : contacts) {
                JPanel contactPanel = createContactPanel(contact);
                mainPanel.add(contactPanel);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        add(scrollPane);
    }

    private JPanel createContactPanel(Contact contact) {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(CONTACT_PANEL_BORDER);
        panel.setMaximumSize(new Dimension(500, 100));

        JLabel idLabel = new JLabel("ID : " + contact.getId());
        idLabel.setFont(LABEL_FONT);
        idLabel.setForeground(TEXT_COLOR);
        panel.add(idLabel);

        JLabel nomLabel = new JLabel("Nom : " + contact.getNom());
        nomLabel.setFont(LABEL_FONT);
        nomLabel.setForeground(TEXT_COLOR);
        panel.add(nomLabel);

        JLabel prenomLabel = new JLabel("Prénom : " + contact.getPrenom());
        prenomLabel.setFont(LABEL_FONT);
        prenomLabel.setForeground(TEXT_COLOR);
        panel.add(prenomLabel);

        JLabel telLabel = new JLabel("Téléphone : " + contact.getTel());
        telLabel.setFont(LABEL_FONT);
        telLabel.setForeground(TEXT_COLOR);
        panel.add(telLabel);

        JLabel emailLabel = new JLabel("Email : " + (contact.getEmail().isEmpty() ? "N/A" : contact.getEmail()));
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setForeground(TEXT_COLOR);
        panel.add(emailLabel);

        return panel;
    }

    public static void main(String[] args) {
        try {
            new ListContactFrame(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur : " + e.getMessage());
        }
    }
}