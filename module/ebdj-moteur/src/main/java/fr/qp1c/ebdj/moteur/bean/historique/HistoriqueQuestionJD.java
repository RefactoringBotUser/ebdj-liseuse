package fr.qp1c.ebdj.moteur.bean.historique;

import fr.qp1c.ebdj.moteur.bean.question.QuestionJD;

public class HistoriqueQuestionJD extends HistoriqueQuestion {

	private QuestionJD question;

	// Getters - setters

	public QuestionJD getQuestion() {
		return question;
	}

	public void setQuestion(QuestionJD question) {
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
