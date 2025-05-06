package DAO;

import java.sql.Connection;//Cette classe représente une connexion à une base de données via JDBC (Java Database Connectivity), et elle sera utilisée pour établir un lien avec contacts.db.
import java.sql.DriverManager;//Elle est utilisée ici pour se connecter à SQLite.
import java.sql.SQLException;

public class Database {//Cette classe est conçue pour fournir une connexion unique à la base de données SQLite, utilisée par ContactDAO.
    // Chemin vers la base de données SQLite (fichier contacts.db dans le projet)
    private static final String URL = "jdbc:sqlite:contacts.db";

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        try {
            // Charger le pilote SQLite pour JDBC
            Class.forName("org.sqlite.JDBC");//Appelle la méthode statique Class.forName("org.sqlite.JDBC") pour charger dynamiquement le pilote JDBC de SQLite.
            // Retourner une connexion à la base
            return DriverManager.getConnection(URL);//Appelle DriverManager.getConnection(URL) pour établir une connexion à la base de données
        } catch (ClassNotFoundException e) {//bloc catch qui gère une ClassNotFoundException, qui est levée si le pilote "org.sqlite.JDBC" n'est pas trouvé
            // Si le pilote n'est pas trouvé, lancer une erreur
            throw new SQLException("Pilote SQLite introuvable : " + e.getMessage());//Transforme une ClassNotFoundException en SQLException pour rester cohérent avec le contexte de la base de données, et signale le problème.
        }
    }
}