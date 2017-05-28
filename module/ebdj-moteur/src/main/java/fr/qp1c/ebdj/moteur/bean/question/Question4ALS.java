package fr.qp1c.ebdj.moteur.bean.question;

import java.util.HashMap;
import java.util.Map;

public class Question4ALS extends Question {
	
	private String theme;

	private Map<String,Question> questions=new HashMap<>();
	
	private String reference;
	
	private Source source;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Map<String, Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<String, Question> questions) {
		this.questions = questions;
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
	
	
	
}
