package fr.qp1c.ebdj.liseuse.commun.bean.historique;

import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionFAF;

public class HistoriqueQuestionFAF extends HistoriqueQuestion {

	private QuestionFAF question;

	// Getters - setters

	public QuestionFAF getQuestion() {
		return question;
	}

	public void setQuestion(QuestionFAF question) {
		this.question = question;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(question);
		return sb.toString();
	}

}
