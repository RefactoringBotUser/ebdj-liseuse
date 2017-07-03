package fr.qp1c.ebdj.moteur.bean.question;

public class QuestionNPG extends Question {

	// Attributs

	private String difficulte;

	private Long id;

	private String reference;

	private Source source;

	// Getters - setters

	public String getDifficulte() {
		return difficulte;
	}

	public Long getId() {
		return id;
	}

	public String getReference() {
		return reference;
	}

	public Source getSource() {
		return source;
	}

	public void setDifficulte(String difficulte) {
		this.difficulte = difficulte;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(id);
		sb.append(" - ");
		sb.append(difficulte);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(source);

		return sb.toString();
	}

}
