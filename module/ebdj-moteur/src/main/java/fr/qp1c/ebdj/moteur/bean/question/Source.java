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

	private String dateEnvoi;

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

	// Getters - setters

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDateIntegration() {
		return dateIntegration;
	}

	public void setDateIntegration(String dateIntegration) {
		this.dateIntegration = dateIntegration;
	}

	public String getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(String dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(club);
		sb.append(" - ");
		sb.append(dateEnvoi);
		sb.append(" - ");
		sb.append(dateIntegration);
		return sb.toString();
	}

}
