package fr.qp1c.ebdj.moteur.ws.wrapper.lecture;

import java.sql.Date;

public class BdjDistanteLecture {

	private Date dateLecture;

	private Long reference;

	private String lecteur;

	public Date getDateLecture() {
		return dateLecture;
	}

	public void setDateLecture(Date dateLecture) {
		this.dateLecture = dateLecture;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public String getLecteur() {
		return lecteur;
	}

	public void setLecteur(String lecteur) {
		this.lecteur = lecteur;
	}

}
