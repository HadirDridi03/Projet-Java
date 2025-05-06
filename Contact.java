package contacts;
public class Contact {
	private int id;
	private String nom;
	private String prenom;
	private int tel;
	private String email;
	public Contact() {
        this.id = 0;
        this.nom = "";
        this.prenom = "";
        this.tel = 0;
        this.email = "";
    }
	public Contact(int id,String nom,String prenom,String email,int tel) {
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
		this.tel=tel;
	}
	public int getId() {return id;}
	public void setId(int id) {this.id=id;}
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom=nom;}
	public String getPrenom() {return prenom;}
	public void setPrenom(String prenom) {this.prenom=prenom;}
	public int getTel() {return tel;}
	public void setTel(int tel) {this.tel=tel;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email=email;}
	
public String toString() {
	return "Contact-->id: "+id+",nom: "+nom+",prenom: "+prenom+",numéro de tel: "+tel+",email: "+email;}}