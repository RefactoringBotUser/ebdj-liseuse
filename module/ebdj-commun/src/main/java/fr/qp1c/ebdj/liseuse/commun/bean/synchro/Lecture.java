package fr.qp1c.ebdj.liseuse.commun.bean.synchro;

public class Lecture {

	// Attributs

	private Long reference;

	private String dateLecture;

	private String lecteur;

	// Getters - setters

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getDateLecture() {
		return dateLecture;
	}

	public void setDateLecture(String dateLecture) {
		this.dateLecture = dateLecture;
	}

	public String getLecteur() {
		return lecteur;
	}

	public void setLecteur(String lecteur) {
		this.lecteur = lecteur;
	}

}
