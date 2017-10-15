package fr.qp1c.ebdj.liseuse.commun.bean.question;

import java.util.HashMap;
import java.util.Map;

public class Theme4ALS extends Question {

	// Attributs

	private Long id;

	private String reference;

	private Source source;

	private String theme;

	private String categorie;

	private Long categorieRef;

	private Long groupeCategorieRef;

	private Long difficulte;

	private Map<String, Question> questions = new HashMap<>();

	// Getters - setters

	public String getTheme() {
		return theme;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Long getCategorieRef() {
		return categorieRef;
	}

	public void setCategorieRef(Long categorieRef) {
		this.categorieRef = categorieRef;
	}

	public Long getGroupeCategorieRef() {
		return groupeCategorieRef;
	}

	public void setGroupeCategorieRef(Long groupeCategorieRef) {
		this.groupeCategorieRef = groupeCategorieRef;
	}

	public Long getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(Long difficulte) {
		this.difficulte = difficulte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(groupeCategorieRef);
		sb.append(" - ");
		sb.append(categorieRef);
		sb.append(" - ");
		sb.append(categorie);
		sb.append(" - ");
		sb.append(theme);
		sb.append(" - ");
		sb.append(reference);
		sb.append(" - ");
		sb.append(difficulte);
		return sb.toString();
	}
}
