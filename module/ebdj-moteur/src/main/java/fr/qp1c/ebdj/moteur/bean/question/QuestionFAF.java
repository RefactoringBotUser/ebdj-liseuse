package fr.qp1c.ebdj.moteur.bean.question;

public class QuestionFAF extends Question {

	private String reference;
	
	private Source source;
	
	private String theme;

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
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(super.toString());
		sb.append(" - ");
		sb.append(theme);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(source);
		
		return sb.toString();
	}

}
