package fr.qp1c.ebdj.liseuse.commun.bean.question;

public class QR {

	// Attributs
	
	protected String question;

	protected String reponse;

	protected Long version;

	// Constructeur

	/**
	 * 
	 */
	public QR() {
		super();
	}

	/**
	 * @param question
	 * @param reponse
	 */
	public QR(String question, String reponse) {
		super();
		this.question = question;
		this.reponse = reponse;
	}

	// Getters / setters

	public String getQuestion() {
		if (question == null) {
			return null;
		}
		return question.replaceAll("  ", " ");
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(question);
		sb.append(" - ");
		sb.append(reponse);
		sb.append(" - ");
		sb.append(version);
		return sb.toString();
	}
}
