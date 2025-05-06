package DAO;

import contacts.Contact;
import java.sql.*;//nclut les outils nécessaires pour interagir avec une base de données via JDBC 
import java.util.*;

public class ContactDAO {
    private Connection conn;//Déclare une variable d'instance conn de type Connection, qui représente la connexion à la base de données SQLite.

    public ContactDAO() throws SQLException {//Le mot-clé throws SQLException indique que ce constructeur peut lever une exception si la connexion à la base de données échoue
        conn = Database.getConnection();//Initialise la variable conn en appelant la méthode statique getConnection() de la classe Database qui retourne une connexion JDBC à la base de données SQLite
        createTable(); // Crée la table au démarrage
        //Cela garantit que la table contacts est créée (ou vérifiée si elle existe déjà) au moment où l'objet ContactDAO est instancié.
    }

    public void createTable() throws SQLException {//Déclare une méthode publique createTable qui crée la table contacts dans la base de données si elle n'existe pas
        String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                     "id INTEGER PRIMARY KEY, " +
                     "nom TEXT, prenom TEXT, tel INTEGER, email TEXT)";
        try (Statement stmt = conn.createStatement()) {// pour exécuter des requêtes SQL. La clause try-with-resources garantit que stmt est fermé automatiquement après utilisation, évitant les fuites de ressources.
            stmt.execute(sql);
        }
    }

    // Vérifie si un ID existe déjà
    public boolean isIdExists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM contacts WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {//Cela permet d'exécuter la requête de manière sécurisée
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();//Exécute la requête et stocke le résultat dans un objet ResultSet (rs), qui contient le nombre d'occurrences.
            return rs.getInt(1) > 0;
        }
    }

    public void create(Contact c) throws SQLException {
        // Vérifie si l'ID existe déjà
        if (isIdExists(c.getId())) {
            throw new SQLException("Erreur : Cet ID est déjà utilisé !");
        }

        String sql = "INSERT INTO contacts (id, nom, prenom, tel, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {//Crée un PreparedStatement pour exécuter l'insertion,
            pstmt.setInt(1, c.getId());//Remplace le premier ? par la valeur de c.getId() (l'ID du contact).
            pstmt.setString(2, c.getNom());
            pstmt.setString(3, c.getPrenom());
            pstmt.setInt(4, c.getTel());
            pstmt.setString(5, c.getEmail());
            pstmt.executeUpdate();//Exécute la requête d'insertion. executeUpdate() est utilisé pour les opérations qui modifient la base de données (INSERT, UPDATE, DELETE).
        }
    }

    public Contact read(int id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Contact(rs.getInt("id"), rs.getString("nom"),
                                   rs.getString("prenom"), rs.getString("email"),
                                   rs.getInt("tel"));//Si un contact est trouvé, crée un nouvel objet Contact avec les valeurs extraites du ResultSet
            }
            return null;
        }
    }

    public void update(Contact c) throws SQLException {
        String sql = "UPDATE contacts SET nom = ?, prenom = ?, tel = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getNom());
            pstmt.setString(2, c.getPrenom());
            pstmt.setInt(3, c.getTel());
            pstmt.setString(4, c.getEmail());
            pstmt.setInt(5, c.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Contact> list() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contacts.add(new Contact(rs.getInt("id"), rs.getString("nom"),
                                         rs.getString("prenom"), rs.getString("email"),
                                         rs.getInt("tel")));
            }
        }
        return contacts;
    }

    public List<Contact> search(String query) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE nom LIKE ? OR prenom LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(rs.getInt("id"), rs.getString("nom"),
                                         rs.getString("prenom"), rs.getString("email"),
                                         rs.getInt("tel")));
            }
        }
        return contacts;
    }
}