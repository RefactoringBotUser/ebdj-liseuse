package fr.qp1c.ebdj.moteur.bean.question;

public class QuestionJD extends Question {

	// Attributs

	private String reference;

	private Source source;

	private String theme;

	private Long id;

	// Getters - setters

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(id);
		sb.append(" - ");
		sb.append(theme);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(source);

		return sb.toString();
	}

}
