package fr.qp1c.ebdj.liseuse.commun.bean.historique;

import fr.qp1c.ebdj.liseuse.commun.bean.question.QuestionNPG;

public class HistoriqueQuestion9PG extends HistoriqueQuestion {

	private int niveau;

	private QuestionNPG question;

	// Getters - setters

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

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
		sb.append(" - ");
		sb.append(niveau);
		return sb.toString();
	}

}
