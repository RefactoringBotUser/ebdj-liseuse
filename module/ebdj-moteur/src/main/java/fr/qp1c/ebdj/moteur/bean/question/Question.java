package fr.qp1c.ebdj.moteur.bean.question;

public class Question {

	// Attributs

	// TODO : #4CAF50 vert #FE2E64 rouge

	private String question;

	private String reponse;

	// Constructeur

	/**
	 * 
	 */
	public Question() {
		super();
	}

	/**
	 * @param question
	 * @param reponse
	 */
	public Question(String question, String reponse) {
		super();
		this.question = question;
		this.reponse = reponse;
	}

	// Getters / setters

	public String getQuestion() {
		return question;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(question);
		sb.append(" - ");
		sb.append(reponse);
		return sb.toString();
	}
}
