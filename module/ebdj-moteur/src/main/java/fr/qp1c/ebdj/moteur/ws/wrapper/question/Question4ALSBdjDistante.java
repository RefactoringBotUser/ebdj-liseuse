package fr.qp1c.ebdj.moteur.ws.wrapper.question;

public class Question4ALSBdjDistante {

	private Integer seq;

	private String question;

	private String reponse;

	/**
	 * @param seq
	 * @param question
	 * @param reponse
	 */
	public Question4ALSBdjDistante(Integer seq, String question, String reponse) {
		super();
		this.seq = seq;
		this.question = question;
		this.reponse = reponse;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

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

}