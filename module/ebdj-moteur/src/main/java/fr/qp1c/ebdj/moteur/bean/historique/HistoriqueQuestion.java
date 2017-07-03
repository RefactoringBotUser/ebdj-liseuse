package fr.qp1c.ebdj.moteur.bean.historique;

public abstract class HistoriqueQuestion {

	private int niveau;

	private int nbQuestion;

	private int nbQuestionReel;

	private boolean nonComptabilise;

	public boolean isNonComptabilise() {
		return nonComptabilise;
	}

	public void setNonComptabilise(boolean nonComptabilise) {
		this.nonComptabilise = nonComptabilise;
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
		return sb.toString();
	}

}
