package fr.qp1c.ebdj.moteur.bean.question;

public class QuestionNPG extends Question {

	// Attributs

	private String reference;

	private String difficulte;

	private Source source;

	private int id;

	// Getters - setters

	public String getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(String difficulte) {
		this.difficulte = difficulte;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(difficulte);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(source);

		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
