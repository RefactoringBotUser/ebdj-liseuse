package fr.qp1c.ebdj.liseuse.commun.exchange.anomalie;

import java.util.Date;

public class BdjDistanteAnomalie {

	private Date dateAnomalie;

	// TODO mettre un enum
	private Long typeAnomalie;

	private Long reference;

	private String commentaire;

	private String lecteur;

	private Long version;

	public Date getDateAnomalie() {
		return dateAnomalie;
	}

	public void setDateAnomalie(Date dateAnomalie) {
		this.dateAnomalie = dateAnomalie;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getLecteur() {
		return lecteur;
	}

	public void setLecteur(String lecteur) {
		this.lecteur = lecteur;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	public Long getTypeAnomalie() {
		return typeAnomalie;
	}

	public void setTypeAnomalie(Long typeAnomalie) {
		this.typeAnomalie = typeAnomalie;
	}

}
