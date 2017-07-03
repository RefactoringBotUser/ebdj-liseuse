package fr.qp1c.ebdj.moteur.bean.historique;

import fr.qp1c.ebdj.moteur.bean.question.QuestionNPG;

public class HistoriqueQuestion9PG extends HistoriqueQuestion {

	private QuestionNPG question;

	public QuestionNPG getQuestion() {
		return question;
	}

	public void setQuestion(QuestionNPG question) {
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
