package service;

public class Authentification {
	private final String nomUser="Hadir";
	private final String mdpUser="1234";
	public boolean verif(String nom,String mdp) {
		 if (nom == null || mdp == null || nom.isEmpty() || mdp.isEmpty()) {
		        return false;
		    }
		return nomUser.equals(nom)&&mdpUser.equals(mdp);
	}

}
