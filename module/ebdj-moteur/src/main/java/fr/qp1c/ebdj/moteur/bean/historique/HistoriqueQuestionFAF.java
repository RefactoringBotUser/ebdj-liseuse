package fr.qp1c.ebdj.moteur.bean.historique;

import fr.qp1c.ebdj.moteur.bean.question.QuestionFAF;

public class HistoriqueQuestionFAF {

	private int niveau;

	private int nbQuestion;

	private int nbQuestionReel;

	private QuestionFAF question;

	private boolean nonComptabilise;

	public boolean isNonComptabilise() {
		return nonComptabilise;
	}

	public void setNonComptabilise(boolean nonComptabilise) {
		this.nonComptabilise = nonComptabilise;
	}

	public QuestionFAF getQuestion() {
		return question;
	}

	public void setQuestion(QuestionFAF question) {
		this.question = question;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public int getNbQuestion() {
		return nbQuestion;
	}

	public void setNbQuestion(int nbQuestion) {
		this.nbQuestion = nbQuestion;
	}

	public int getNbQuestionReel() {
		return nbQuestionReel;
	}

	public void setNbQuestionReel(int nbQuestionReel) {
		this.nbQuestionReel = nbQuestionReel;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nbQuestion);
		sb.append(" - ");
		sb.append(nbQuestionReel);
		sb.append(" - ");
		sb.append(niveau);
		sb.append(" - ");
		sb.append(question);
		return sb.toString();
	}

}
