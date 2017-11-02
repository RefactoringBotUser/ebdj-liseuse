package fr.qp1c.ebdj.liseuse.commun.bean.question;

public class QuestionNPG extends QRUnique {

	// Attributs

	private String difficulte;

	// Getters - setters

	public String getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(String difficulte) {
		this.difficulte = difficulte;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(difficulte);

		return sb.toString();
	}

}
