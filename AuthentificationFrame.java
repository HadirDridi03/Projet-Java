package views;

import service.Authentification;
import service.GestContact;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.sql.SQLException;

public class AuthentificationFrame extends JFrame {
    private Authentification authentification;
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

    public AuthentificationFrame(Authentification authentification, GestContact gestContact) {
        this.authentification = authentification;
        this.gestContact = gestContact;
        initAuth();
    }

    private void initAuth() {
        setTitle("Connexion");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(PANEL_BORDER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(PANEL_BORDER);

        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        usernameLabel.setFont(LABEL_FONT);
        usernameLabel.setForeground(TEXT_COLOR);
        JTextField usernameField = new JTextField();
        usernameField.setFont(TEXT_FIELD_FONT);
        usernameField.setBackground(BACKGROUND_COLOR);
        usernameField.setForeground(TEXT_COLOR);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(LABEL_FONT);
        passwordLabel.setForeground(TEXT_COLOR);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(TEXT_FIELD_FONT);
        passwordField.setBackground(BACKGROUND_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton loginButton = new JButton("Connexion");
        loginButton.setFont(BUTTON_FONT);
        loginButton.setBackground(BUTTON_COLOR);
        loginButton.setForeground(BUTTON_TEXT_COLOR);
        loginButton.setBorder(BUTTON_BORDER);
        loginButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setBackground(BUTTON_COLOR);
        cancelButton.setForeground(BUTTON_TEXT_COLOR);
        cancelButton.setBorder(BUTTON_BORDER);
        cancelButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs !",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (authentification.verif(username, password)) {
                dispose();
                new InterphaceContact(gestContact).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nom d'utilisateur ou mot de passe incorrect !",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            new AuthentificationFrame(new Authentification(), new GestContact()).setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}