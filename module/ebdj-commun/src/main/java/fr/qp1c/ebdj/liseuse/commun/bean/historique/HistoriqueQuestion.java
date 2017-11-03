package fr.qp1c.ebdj.liseuse.commun.bean.historique;

public abstract class HistoriqueQuestion {

	private int nbQuestion;

	private int nbQuestionReel;

	private boolean nonComptabilise;
	
	// Getters - setters

	public boolean isNonComptabilise() {
		return nonComptabilise;
	}

	public void setNonComptabilise(boolean nonComptabilise) {
		this.nonComptabilise = nonComptabilise;
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
		return sb.toString();
	}

}
