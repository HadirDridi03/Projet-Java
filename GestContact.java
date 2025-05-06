package service;

import contacts.Contact;
import DAO.ContactDAO;
import java.sql.SQLException;
import java.util.List;

public class GestContact {
    private final ContactDAO dao;

    public GestContact() throws SQLException {
        this.dao = new ContactDAO();
        dao.createTable();
    }

    public boolean ajouterContact(Contact c) throws SQLException {
        if (c == null || c.getId() <= 0 || c.getNom() == null || c.getNom().trim().isEmpty() ||
            c.getPrenom() == null || c.getPrenom().trim().isEmpty() ||
            c.getTel() <= 0 || String.valueOf(c.getTel()).length() < 8) {
            return false;
        }
        if (dao.read(c.getId()) != null) {
            return false;
        }
        dao.create(c);
        return true;
    }

    public boolean supprimerContact(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        dao.delete(id);
        return true;
    }

    public boolean modifierContact(Contact c) throws SQLException {
        if (c == null || c.getId() <= 0 || c.getNom() == null || c.getNom().trim().isEmpty() ||
            c.getPrenom() == null || c.getPrenom().trim().isEmpty() ||
            c.getTel() <= 0 || String.valueOf(c.getTel()).length() < 8) {
            return false;
        }
        dao.update(c);
        return true;
    }

    public List<Contact> listerContacts() throws SQLException {
        return dao.list();
    }

    public List<Contact> rechercherContact(String recherche) throws SQLException {
        if (recherche == null || recherche.trim().isEmpty()) {
            return dao.list();
        }
        return dao.search(recherche.trim());
    }

    public Contact trouverContactParId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.read(id);
    }

  
  }