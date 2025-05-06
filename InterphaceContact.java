package views;

import service.Authentification;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;

public class InterphaceContact extends JFrame {
    private GestContact gestContact;
    private Authentification authentification;

    // Définitions des styles centralisés
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(242, 224, 216);
    private static final Color BUTTON_COLOR = new Color(184, 53, 86);
    private static final Color TEXT_COLOR = new Color(144, 12, 63);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);

    public InterphaceContact(GestContact gestContact) {
        this.gestContact = gestContact;
        this.authentification = new Authentification();
        initInterface();
    }

    private void initInterface() {
        setTitle("Gestion des Contacts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Gestion des Contacts", SwingConstants.CENTER);
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(PANEL_BORDER);

        JButton addButton = new JButton("Ajouter un contact");
        addButton.setFont(BUTTON_FONT);
        addButton.setBackground(BUTTON_COLOR);
        addButton.setForeground(BUTTON_TEXT_COLOR);
        addButton.setBorder(BUTTON_BORDER);
        addButton.setFocusPainted(false);

        JButton listButton = new JButton("Lister les contacts");
        listButton.setFont(BUTTON_FONT);
        listButton.setBackground(BUTTON_COLOR);
        listButton.setForeground(BUTTON_TEXT_COLOR);
        listButton.setBorder(BUTTON_BORDER);
        listButton.setFocusPainted(false);

        JButton editButton = new JButton("Modifier un contact");
        editButton.setFont(BUTTON_FONT);
        editButton.setBackground(BUTTON_COLOR);
        editButton.setForeground(BUTTON_TEXT_COLOR);
        editButton.setBorder(BUTTON_BORDER);
        editButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Supprimer un contact");
        deleteButton.setFont(BUTTON_FONT);
        deleteButton.setBackground(BUTTON_COLOR);
        deleteButton.setForeground(BUTTON_TEXT_COLOR);
        deleteButton.setBorder(BUTTON_BORDER);
        deleteButton.setFocusPainted(false);

        JButton searchButton = new JButton("Rechercher un contact");
        searchButton.setFont(BUTTON_FONT);
        searchButton.setBackground(BUTTON_COLOR);
        searchButton.setForeground(BUTTON_TEXT_COLOR);
        searchButton.setBorder(BUTTON_BORDER);
        searchButton.setFocusPainted(false);

        JButton logoutButton = new JButton("Se déconnecter");
        logoutButton.setFont(BUTTON_FONT);
        logoutButton.setBackground(BUTTON_COLOR);
        logoutButton.setForeground(BUTTON_TEXT_COLOR);
        logoutButton.setBorder(BUTTON_BORDER);
        logoutButton.setFocusPainted(false);

        addButton.addActionListener(e -> new AjoutContactFrame(gestContact).setVisible(true));
        listButton.addActionListener(e -> new ListContactFrame(gestContact).setVisible(true));
        editButton.addActionListener(e -> new ModifContactFrame(gestContact).setVisible(true));
        deleteButton.addActionListener(e -> new SuppContactFrame(gestContact).setVisible(true));
        searchButton.addActionListener(e -> new RechContactFrame(gestContact).setVisible(true));
        logoutButton.addActionListener(e -> {
            dispose();
            new AuthentificationFrame(authentification, gestContact).setVisible(true);
        });

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new InterphaceContact(new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}