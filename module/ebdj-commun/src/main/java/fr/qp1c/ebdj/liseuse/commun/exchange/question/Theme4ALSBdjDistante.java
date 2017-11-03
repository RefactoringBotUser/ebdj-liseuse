package fr.qp1c.ebdj.liseuse.commun.exchange.question;

import java.util.Map;

public class Theme4ALSBdjDistante extends QRGeneriqueBdjDistante {

	private String theme;

	private String categorie4ALS;

	private Long categorie4ALSRef;

	private Long groupeCategorie4ALS;

	private Map<Integer, Question4ALSBdjDistante> questions;


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCategorie4ALS() {
		return categorie4ALS;
	}

	public void setCategorie4ALS(String categorie4als) {
		categorie4ALS = categorie4als;
	}

	public Long getCategorie4ALSRef() {
		return categorie4ALSRef;
	}

	public void setCategorie4ALSRef(Long categorie4alsRef) {
		categorie4ALSRef = categorie4alsRef;
	}

	public Long getGroupeCategorie4ALS() {
		return groupeCategorie4ALS;
	}

	public void setGroupeCategorie4ALS(Long groupeCategorie4ALS) {
		this.groupeCategorie4ALS = groupeCategorie4ALS;
	}

	public Map<Integer, Question4ALSBdjDistante> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<Integer, Question4ALSBdjDistante> questions) {
		this.questions = questions;
	}

}
