package fr.qp1c.ebdj.moteur.bean.lecteur;

public class Lecteur {

	// Attributs

	private Long id;

	private String nom;

	private String prenom;

	private String reference;

	// Constructeurs

	public Lecteur() {
		this.nom = "";
		this.prenom = "";
	}

	// Getters - setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String formatterNomUtilisateur() {
		return prenom + " " + nom;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
