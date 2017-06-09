package fr.qp1c.ebdj.moteur.bean.stats;

public class StatsQuestions {

	private int nbQuestionsTotal;

	private int nbQuestionsJouees;

	public int getNbQuestionsTotal() {
		return nbQuestionsTotal;
	}

	public void setNbQuestionsTotal(int nbQuestionsTotal) {
		this.nbQuestionsTotal = nbQuestionsTotal;
	}

	public int getNbQuestionsInedites() {
		return nbQuestionsTotal - nbQuestionsJouees;
	}

	public int getNbQuestionsJouees() {
		return nbQuestionsJouees;
	}

	public void setNbQuestionsJouees(int nbQuestionsJouees) {
		this.nbQuestionsJouees = nbQuestionsJouees;
	}

	@Override
	public String toString() {
		return "StatsQuestions [nbQuestionsTotal=" + nbQuestionsTotal + ", nbQuestionsJouees=" + nbQuestionsJouees
				+ "]";
	}

}
