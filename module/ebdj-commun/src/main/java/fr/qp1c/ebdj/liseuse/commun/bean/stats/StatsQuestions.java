package fr.qp1c.ebdj.liseuse.commun.bean.stats;

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
		return "nbQuestionsTotal=" + nbQuestionsTotal + ", nbQuestionsJouees=" + nbQuestionsJouees;
	}

	public String afficherQuantites() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getNbQuestionsTotal());
		sb.append(" / ");
		sb.append(getNbQuestionsInedites());
		sb.append(" / ");
		sb.append(getNbQuestionsJouees());
		sb.append("]");
		return sb.toString();
	}

}
