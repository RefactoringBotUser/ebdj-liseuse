package fr.qp1c.ebdj.liseuse.commun.exchange.lecture;

import java.util.Date;

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
