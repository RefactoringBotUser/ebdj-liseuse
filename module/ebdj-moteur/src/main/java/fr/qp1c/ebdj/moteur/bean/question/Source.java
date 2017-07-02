package fr.qp1c.ebdj.moteur.bean.question;

/**
 * Cette classe modélise la source d'une question ou d'un thème de
 * questionnaire. Elle a pour but d'assurer la tracabilité d'une question.
 * 
 * @author NICO
 *
 */
public class Source {

	// Attributs

	private String club;

	private String dateReception;

	private String dateIntegration;

	// Constructeurs

	/**
	 * Constructeur par défaut.
	 * 
	 */
	public Source() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param club
	 */
	public Source(String club) {
		super();
		this.club = club;
	}

	/**
	 * Constructeur
	 * 
	 * @param club
	 */
	public Source(String club, String dateReception) {
		super();
		this.club = club;
		this.dateReception = dateReception;
	}

	// Getters - setters

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDateReception() {
		return dateReception;
	}

	public void setDateReception(String dateReception) {
		this.dateReception = dateReception;
	}

	public String getDateIntegration() {
		return dateIntegration;
	}

	public void setDateIntegration(String dateIntegration) {
		this.dateIntegration = dateIntegration;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(club);
		sb.append(" - ");
		sb.append(dateReception);
		if (dateIntegration != null && "".equals(dateIntegration)) {
			sb.append(" - ");
			sb.append(dateIntegration);
		}
		return sb.toString();
	}

}
