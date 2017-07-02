package fr.qp1c.ebdj.moteur.bean.synchro;

public class Anomalie {

	private Long reference;

	private Long version;

	private Long typeAnomalie;

	private String dateAnomalie;

	private String cause;

	private String lecteur;

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

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getLecteur() {
		return lecteur;
	}

	public void setLecteur(String lecteur) {
		this.lecteur = lecteur;
	}

	public String getDateAnomalie() {
		return dateAnomalie;
	}

	public void setDateAnomalie(String dateAnomalie) {
		this.dateAnomalie = dateAnomalie;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
